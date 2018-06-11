package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.io.File;
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
        try {
            Node questionView = getPluginForCurrentQuestion().getVraagView().getView();
            questionPane.getChildren().add(questionView);
        } catch (ClassNotFoundException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
            AlertError("Could not load question view (ClassNotFoundException)");
        }
    }

    public void loadAnswerView() {
        try {
            Node answerView = getPluginForCurrentQuestion().getAntwoordView().getView(null);
            answerPane.getChildren().add(answerView);
        } catch (ClassNotFoundException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
            AlertError("Could not load question view (ClassNotFoundException)");
        }

        questionPane.getChildren().add(currentPlugin.getVraagView(currentToets.getVragen().get(currentQuestionIndex).getData()).getView());
    }

    public void loadAnswerView() {
        // TODO: De JSON string in de getView() methode onderin is nu fake en moet straks uit de "cache" komen
//        String dummyJSON = "{ \"steps\": [ { \"rows\": [ { \"targetNode\": \"A\", \"distanceToTarget\": 1, \"isDone\": false }, { \"targetNode\": \"B\", \"distanceToTarget\": 2, \"isDone\": false }, { \"targetNode\": \"C\", \"distanceToTarget\": 3, \"isDone\": false }, { \"targetNode\": \"D\", \"distanceToTarget\": 0, \"isDone\": false }, { \"targetNode\": \"E\", \"distanceToTarget\": 0, \"isDone\": false }, { \"targetNode\": \"F\", \"distanceToTarget\": 0, \"isDone\": false } ], \"fromNode\": \"C\", \"totalDistance\": 3, \"isCorrect\": false }, { \"rows\": [ { \"targetNode\": \"F\", \"distanceToTarget\": 1, \"isDone\": false }, { \"targetNode\": \"E\", \"distanceToTarget\": 2, \"isDone\": false }, { \"targetNode\": \"D\", \"distanceToTarget\": 3, \"isDone\": false }, { \"targetNode\": \"C\", \"distanceToTarget\": 0, \"isDone\": false }, { \"targetNode\": \"B\", \"distanceToTarget\": 0, \"isDone\": false }, { \"targetNode\": \"A\", \"distanceToTarget\": 0, \"isDone\": false } ], \"fromNode\": \"F\", \"totalDistance\": 4, \"isCorrect\": false } ] }";
        answerPane.getChildren().add(currentPlugin.getAntwoordView().getView());
>>>>>>> 1b49317e5372af2459b6c9b6c96df5dc7c5117c1
    }

    public Plugin getPluginForCurrentQuestion() throws ClassNotFoundException {
        Vraag currentVraag = currentToets.getVragen().get(currentQuestionIndex);
        return PluginLoader.getPlugin(currentVraag.getVraagType());
    }

    public void btnPreviousQuestionPressed(ActionEvent event) {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex -= 1;
            showExercise();
        } else {
            AlertInfo("Er zijn geen vragen meer om hiervoor in te laden.");
        }
    }

    public void btnNextQuestionPressed(ActionEvent event) {
        if (currentQuestionIndex < (currentToets.getVragen().size() - 1)) {
            currentQuestionIndex += 1;
            showExercise();
        } else {
            AlertInfo("Laatste vraag bereikt.");
        }
    }

    public void btnLoadPressed(ActionEvent event) {
        FileChooser directoryChooser = new FileChooser();
        File selectedDirectory = directoryChooser.showOpenDialog(primaryStage);
        currentToets = gsu.loadTentamen(selectedDirectory.toString());
        showExercise();
        AlertInfo("Test");
    }

    public String getGivenAntwoordFromPlugin() {
        String givenAntwoord = "";

        if (currentPlugin != null)
            givenAntwoord = currentPlugin.getAntwoordView().getGivenAntwoord();

        return givenAntwoord;
    }

}
