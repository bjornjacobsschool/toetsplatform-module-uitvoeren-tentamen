package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.VraagDaoSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen.IUploadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Student;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
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
    private VraagDaoSqlite vraagDaoSqlite;

    private Tentamen currentTentamen;
    @Inject
    private IUploadenTentamenDAO uploadenTentamenDAO;

    private Plugin currentPlugin;

    private int currentQuestionIndex = 0;
    private GsonUtil gsu;
    private Stage primaryStage;

    private Student studentDieDezeTentamenUitvoerd = new Student();

    public void setUp(Stage primaryStage, Tentamen tentamen) {
        // Setup SQLite
        try {
            storageSetupDao.setup();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }

        this.primaryStage = primaryStage;
        gsu = new GsonUtil();

        // Assign loadedtoets to currentTentamen
        currentTentamen = tentamen;

        // Show exercise
        showExercise();
        intializeAutoSaver();
    }

    private void intializeAutoSaver() {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Saving answer to local db automatically");
                saveAnswerToLocal();
            }
        }, 30000, 30000);
    }

    public void setPrimaryStage(Stage ps) {
        this.primaryStage = ps;
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
            e.printStackTrace();
        }

        loadQuestionView();
        loadAnswerView();
    }

    public void loadQuestionView() {
        questionPane.getChildren().add(currentPlugin.getVraagView(currentTentamen.getVragen().get(currentQuestionIndex).getData()).getView());
    }

    public void loadAnswerView() {
        String localAnswer = loadLocalAnswer();
        if (localAnswer != null) {
            answerPane.getChildren().add(currentPlugin.getAntwoordView(currentTentamen.getVragen().get(currentQuestionIndex).getData(), localAnswer).getView());
        } else {
            answerPane.getChildren().add(currentPlugin.getAntwoordView(currentTentamen.getVragen().get(currentQuestionIndex).getData()).getView());
        }
    }

    public Plugin getPluginForCurrentQuestion() throws ClassNotFoundException {
        Vraag currentVraag = currentTentamen.getVragen().get(currentQuestionIndex);
        return PluginLoader.getPlugin(currentVraag.getVraagtype());
    }

    public void btnPreviousQuestionPressed(ActionEvent event) {

        saveAnswerToLocal();

        if (currentQuestionIndex > 0) {
            currentQuestionIndex -= 1;
            showExercise();
        } else {
            AlertInfo("Er zijn geen vragen meer om hiervoor in te laden.");
        }
    }

    public void btnNextQuestionPressed(ActionEvent event) {

        saveAnswerToLocal();

        if (currentQuestionIndex < (currentTentamen.getVragen().size() - 1)) {
            currentQuestionIndex += 1;
            showExercise();
        } else {
            AlertInfo("Laatste vraag bereikt.");
        }
    }

    public void btnLoadPressed(ActionEvent event) {
        FileChooser directoryChooser = new FileChooser();
        File selectedDirectory = directoryChooser.showOpenDialog(primaryStage);
        currentTentamen = gsu.loadTentamen(selectedDirectory.toString());
        showExercise();
        AlertInfo("Test");
    }

    private void saveAnswerToLocal() {

        boolean saved = false;
        try {
            vraagDaoSqlite.setAntwoord(currentTentamen.getVragen().get(currentQuestionIndex).getId(), currentTentamen.getId(), getGivenAntwoordFromPlugin());
            saved = true;
        } catch (SQLException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }

        if (!saved) {
            AlertError("Er is wat misgegaan met het (automatisch) opslaan van je vraag, probeer opnieuw.");
        }
    }

    private String loadLocalAnswer() {
        Antwoord antwoord = null;
        try {
            antwoord = vraagDaoSqlite.getAntwoord(currentTentamen.getVragen().get(currentQuestionIndex).getId(), currentTentamen.getId());
        } catch (SQLException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }

        if (antwoord != null) {
            return antwoord.getGegevenAntwoord();
        }

        return null;
    }

    public void btnInleverenPressed(ActionEvent event) {
        if (currentTentamen.getAntwoorden() == null) {
            AlertInfo("Je hebt geen vragen beantwoord om in te kunnen leveren.");
        } else {
            if (Utils.checkInternetConnection()) {
                AlertInfo(uploadenTentamenDAO.superUploadTentamen(currentTentamen, studentDieDezeTentamenUitvoerd));
            }
        }
    }

    public String getGivenAntwoordFromPlugin() {
        String givenAntwoord = "";

        if (currentPlugin != null)
            givenAntwoord = currentPlugin.getAntwoordView("").getGivenAntwoord();

        return givenAntwoord;
    }

}
