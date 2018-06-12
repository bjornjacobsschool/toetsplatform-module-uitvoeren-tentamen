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
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.io.IOException;
import java.sql.SQLException;
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
        questionPane.getChildren().add(currentPlugin.getVraagView(currentToets.getVragen().get(currentQuestionIndex).getData()).getView());
    }

    public Plugin getPluginForCurrentQuestion() throws ClassNotFoundException {
        Vraag currentVraag = currentToets.getVragen().get(currentQuestionIndex);
        return PluginLoader.getPlugin(currentVraag.getVraagType());
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
//        FileChooser directoryChooser = new FileChooser();
//        File selectedDirectory = directoryChooser.showOpenDialog(primaryStage);
//        currentToets = gsu.loadTentamen(selectedDirectory.toString());
//        showExercise();
//        AlertInfo("Test");
    }

    public String getGivenAntwoordFromPlugin() {
        String givenAntwoord = "";

        if (currentPlugin != null)
            givenAntwoord = currentPlugin.getAntwoordView("").getGivenAntwoord();

        return givenAntwoord;
    }

    public void loadAnswerView() {
        answerPane.getChildren().add(currentPlugin.getAntwoordView(currentToets.getVragen().get(currentQuestionIndex).getData()).getView());
    }

    private void saveQuestion() {
        String tentamenId = currentToets.getId();
        Vraag vraag = currentToets.getVragen().get(currentQuestionIndex);
        Antwoord gegevenAntwoord = new Antwoord(vraag.getId(), tentamenId, getGivenAntwoordFromPlugin());
        vraag.setAntwoord(gegevenAntwoord);
        toetsDao.saveAntwoord(vraag, tentamenId);
    }

    private void saveTentamen() {
        try {
            toetsDao.saveTentamen(currentToets);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
