package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.VraagDaoSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
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
    private GsonUtil gsu;
    private Stage primaryStage;

    public void setUp(Stage primaryStage, Tentamen tentamen) {
        // Lock min window to 640x480
        primaryStage.setMinWidth(640);
        primaryStage.setMinHeight(480);

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

        // Update question index
        lvQuestionIndexList.getSelectionModel().select(currentQuestionIndex);

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

    public String getGivenAntwoordFromPlugin() {
        String givenAntwoord = "";

        if (currentPlugin != null)
            givenAntwoord = currentPlugin.getAntwoordView("").getGivenAntwoord();

        return givenAntwoord;
    }

}
