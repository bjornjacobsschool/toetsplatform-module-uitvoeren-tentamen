package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.google.inject.Inject;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.han.toetsapplicatie.apimodels.dto.IngevuldeVraagDto;
import nl.han.toetsapplicatie.apimodels.dto.StudentDto;
import nl.han.toetsapplicatie.apimodels.dto.UitgevoerdTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.VraagDaoSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.exceptions.GatewayCommunicationException;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Versie;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import nl.han.toetsplatform.module.uitvoeren_tentamen.serviceagent.GatewayServiceAgent;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class TentamenUitvoerenController extends Controller {

    @FXML
    private ListView lvQuestionIndexList;

    @FXML
    private Pane questionPane;

    @FXML
    private Pane answerPane;

    @FXML
    private Label lblCurrentQuestionNo;

    @Inject
    private StorageSetupDao storageSetupDao;

    @Inject
    private VraagDaoSqlite vraagDaoSqlite;

    private Tentamen currentTentamen;
    private Plugin currentPlugin;

    private int currentQuestionIndex = 0;

    public void setUp(Stage primaryStage, Tentamen tentamen) {
        // Lock min window to 640x480
        primaryStage.setMinWidth(640);
        primaryStage.setMinHeight(480);

        // Setup SQLite
        try {
            storageSetupDao.setup();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }

        // Assign loadedtoets to currentTentamen
        currentTentamen = tentamen;

        // Update question index listview
        buildQuestionMenu();

        // Show exercise
        showExercise();
        intializeAutoSaver();
    }

    public void buildQuestionMenu() {
        for (int i = 1; i < currentTentamen.getVragen().size() + 1; i++) {
            lvQuestionIndexList.getItems().add(i);
        }
        lvQuestionIndexList.getSelectionModel().select(currentQuestionIndex);
        lvQuestionIndexList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                saveAnswerToLocal();
                currentQuestionIndex = Integer.parseInt(lvQuestionIndexList.getSelectionModel().getSelectedItem().toString()) - 1;
                showExercise();
            }
        });
    }

    private void intializeAutoSaver() {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Utils.getLogger().log(Level.INFO, "Saving answer to local db automatically");
                saveAnswerToLocal();
            }
        }, 30000, 30000);
    }

    public void showExercise() {
        // Clear question & answer panes
        questionPane.getChildren().clear();
        answerPane.getChildren().clear();

        lblCurrentQuestionNo.setText((currentQuestionIndex + 1) + "/" + currentTentamen.getVragen().size());

        // Get current plugin and store it in local variable
        try {
            currentPlugin = getPluginForCurrentQuestion();
        } catch (Exception e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }

        // Update question index
        lvQuestionIndexList.getSelectionModel().select(currentQuestionIndex);

        loadQuestionView();
        loadAnswerView();
    }

    public void loadQuestionView() {
        questionPane.getChildren().add(currentPlugin.getVraagView(currentTentamen.getVragen().get(currentQuestionIndex).getMogelijkeAntwoorden()).getView());
    }

    public void loadAnswerView() {
        String localAnswer = loadLocalAnswer();
        if (localAnswer != null) {
            answerPane.getChildren().add(currentPlugin.getAntwoordView(currentTentamen.getVragen().get(currentQuestionIndex).getMogelijkeAntwoorden(), localAnswer).getView());
        } else {
            answerPane.getChildren().add(currentPlugin.getAntwoordView(currentTentamen.getVragen().get(currentQuestionIndex).getMogelijkeAntwoorden()).getView());
        }
    }

    public Plugin getPluginForCurrentQuestion() throws ClassNotFoundException {
        Vraag currentVraag = currentTentamen.getVragen().get(currentQuestionIndex);
        return PluginLoader.getPlugin(currentVraag.getVraagtype());
    }

    public void btnPreviousQuestionPressed() {
        saveAnswerToLocal();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex -= 1;
            showExercise();
        } else {
            alertInfo("Er zijn geen vragen meer om hiervoor in te laden.");
        }
    }

    public void btnNextQuestionPressed() {
        saveAnswerToLocal();
        if (currentQuestionIndex < (currentTentamen.getVragen().size() - 1)) {
            currentQuestionIndex += 1;
            showExercise();
        } else {
            uploadTentamen();
        }
    }

    public void uploadTentamen() {
        try {
            List<Antwoord> antwoorden = vraagDaoSqlite.getAntwoordenVoorTentamen(currentTentamen.getId());
            List<IngevuldeVraagDto> ingevuldeVragen = new ArrayList<>();

            for (int i = 0; i < antwoorden.size(); i++) {
                Versie versie = currentTentamen.getVersie();

                VersieDto versieDto = new VersieDto();
                versieDto.setDatum(versie.getDatum().getTime() / 1000);
                versieDto.setNummer(Integer.parseInt(versie.getNummer()));
                versieDto.setOmschrijving(versie.getOmschrijving());

                ingevuldeVragen.add(new IngevuldeVraagDto(
                        UUID.fromString(antwoorden.get(i).getVraagId()),
                        antwoorden.get(i).getGegevenAntwoord(),
                        versieDto
                ));
            }

            VersieDto tentamenVersie = new VersieDto();
            tentamenVersie.setDatum(currentTentamen.getVersie().getDatum().getTime() / 1000);
            tentamenVersie.setNummer(Integer.parseInt(currentTentamen.getVersie().getNummer()));
            tentamenVersie.setOmschrijving(currentTentamen.getVersie().getOmschrijving());

            StudentDto studentDto = new StudentDto();
            studentDto.setKlas("ASD");
            studentDto.setStudentNummer(559616);

            UitgevoerdTentamenDto tentamenDto = new UitgevoerdTentamenDto();
            tentamenDto.setId(UUID.fromString(currentTentamen.getId()));
            tentamenDto.setNaam(currentTentamen.getNaam());
            tentamenDto.setVersie(tentamenVersie);
            tentamenDto.setVragen(ingevuldeVragen);
            tentamenDto.setHash("");
            tentamenDto.setStudent(studentDto);

            // Stuur tentamenDto naar backend
            GatewayServiceAgent gatewayServiceAgent = new GatewayServiceAgent();
            gatewayServiceAgent.post("/tentamens/uitgevoerd", tentamenDto);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (GatewayCommunicationException e) {
            e.printStackTrace();
        }
    }

    private void saveAnswerToLocal() {


        boolean saved = false;
        try {
            vraagDaoSqlite.setAntwoord(currentTentamen.getVragen().get(currentQuestionIndex).getId(), currentTentamen.getId(), getGivenAntwoordFromPlugin());
            saved = true;
        } catch (SQLException e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }

        if (!saved) {
            alertError("Er is wat misgegaan met het (automatisch) opslaan van je vraag, probeer opnieuw.");
        }
    }

    private String loadLocalAnswer() {
        Antwoord antwoord = null;
        try {
            antwoord = vraagDaoSqlite.getAntwoord(currentTentamen.getVragen().get(currentQuestionIndex).getId(), currentTentamen.getId());
        } catch (SQLException e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }

        if (antwoord != null) {
            return antwoord.getGegevenAntwoord();
        }

        return null;
    }

    public String getGivenAntwoordFromPlugin() {
        String givenAntwoord = "";

        if (currentPlugin != null)
            givenAntwoord = currentPlugin.getAntwoordView("").getGivenAntwoord();

        return givenAntwoord;
    }

}
