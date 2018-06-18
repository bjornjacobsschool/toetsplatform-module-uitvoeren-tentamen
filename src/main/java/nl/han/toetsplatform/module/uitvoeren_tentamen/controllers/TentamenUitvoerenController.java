package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.TentamenDaoSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class TentamenUitvoerenController extends Controller {
    @FXML
    private Pane questionPane;

    @FXML
    private Pane answerPane;

    @FXML
    private Label lblCurrentQuestionNo;

    @Inject
    private StorageSetupDao storageSetupDao;

    @Inject
    private TentamenDaoSqlite toetsDao;

    @Inject
    private VraagDao vraagDao;

    private Tentamen currentToets;
    private Plugin currentPlugin;

    private int currentQuestionIndex = 0;
    private GsonUtil gsu;
    private Stage primaryStage;

    public void setUp(Stage primaryStage, Tentamen tentamen) {
        // Setup SQLite
        try {
            storageSetupDao.setup();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }

        this.primaryStage = primaryStage;
        gsu = new GsonUtil();

        // Assign loadedtoets to currentToets
        currentToets = tentamen;
        currentToets.setStudentNr(500000); //TODO dynamisch maken

        // Create antwoord objects for every vraag where no antwoord exists and save in local database
        try {
            createAntwoordObjectsForVragenWhereNoneExist();
        } catch (SQLException e) {
            AlertInfo(e.getMessage());
        }
        saveTentamen();

        // Show exercise
        showExercise();
    }

    public void setPrimaryStage(Stage ps) {
        this.primaryStage = ps;
    }

    public void showExercise() {
        // Clear question & answer panes
        questionPane.getChildren().clear();
        answerPane.getChildren().clear();

        lblCurrentQuestionNo.setText((currentQuestionIndex + 1) + "/" + currentToets.getVragen().size());

        // Get current plugin and store it in local variable
        try {
            currentPlugin = getPluginForCurrentQuestion();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadQuestionView();
        loadAnswerView();
    }

    public void loadQuestionView() {
        questionPane.getChildren().add(currentPlugin.getVraagView(getCurrentVraag().getData()).getView());
    }

    public void loadAnswerView() {
        String antwoord = getCurrentVraag().getAntwoord().getGegevenAntwoord();
        if (antwoord.equals("")) {
            answerPane.getChildren().add(currentPlugin.getAntwoordView(getCurrentVraag().getData()).getView());
        } else {
            answerPane.getChildren().add(currentPlugin.getAntwoordView(getCurrentVraag().getData(), antwoord).getView());
        }
    }

    private Vraag getCurrentVraag() {
        return currentToets.getVragen().get(currentQuestionIndex);
    }


    public Plugin getPluginForCurrentQuestion() throws ClassNotFoundException {
        Vraag currentVraag = getCurrentVraag();
        return PluginLoader.getPlugin(currentVraag.getVraagtype());
    }

    public void btnPreviousQuestionPressed(ActionEvent event) {
        saveQuestion();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex -= 1;
            showExercise();
        } else {
            AlertInfo("Er zijn geen vragen meer om hiervoor in te laden.");
        }
    }

    public void btnNextQuestionPressed(ActionEvent event) {
        saveQuestion();
        if (currentQuestionIndex < (currentToets.getVragen().size() - 1)) {
            currentQuestionIndex += 1;
            showExercise();
        } else {
            AlertInfo("Laatste vraag bereikt.");
        }
    }

    public void btnLoadPressed(ActionEvent event) {
        saveTentamen();
    }

    public String getGivenAntwoordFromPlugin() {
        String givenAntwoord = "";

        if (currentPlugin != null)
            givenAntwoord = currentPlugin.getAntwoordView("").getGivenAntwoord();
        else {
            try {
                currentPlugin = getPluginForCurrentQuestion();
            } catch (ClassNotFoundException e) {
                AlertError(e.getMessage());
            }
            givenAntwoord = currentPlugin.getAntwoordView("").getGivenAntwoord();
        }

        return givenAntwoord;
    }

    private void saveQuestion() {
        updateAntwoordForCurrentVraag();
        String tentamenId = currentToets.getId();
        toetsDao.saveAntwoord(getCurrentVraag(), tentamenId);
    }

    private void saveTentamen() {
        updateAntwoordForCurrentVraag();
        try {
            toetsDao.saveTentamen(currentToets);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAntwoordForCurrentVraag() {
        Antwoord currentAntwoord = getCurrentVraag().getAntwoord();
        if (currentAntwoord == null) {
            getCurrentVraag().setAntwoord(new Antwoord());
        } else {
            currentAntwoord.setGegevenAntwoord(getGivenAntwoordFromPlugin());
        }
    }

    /**
     * Maak antwoord objecten aan met een lege string als antwoord voor vragen waar nog geen antwoord object
     * voor bestaat.
     */
    private void createAntwoordObjectsForVragenWhereNoneExist() throws SQLException {
        List<Vraag> vragen = currentToets.getVragen();
        for (Vraag currentVraag : vragen) {
            Antwoord currentAntwoord = currentVraag.getAntwoord();
            if (currentAntwoord == null) {

                Antwoord newAntwoord = vraagDao.getAntwoord(currentVraag.getId());
                if (newAntwoord == null) {
                    newAntwoord = new Antwoord(currentVraag.getId(), currentToets.getId(), "");
                }
                currentVraag.setAntwoord(newAntwoord);
            }
        }
    }

}
