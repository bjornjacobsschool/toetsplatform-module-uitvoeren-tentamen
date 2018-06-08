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
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void setUp(Stage primaryStage) {
        // Setup SQLite
        try {
            storageSetupDao.setup();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage = primaryStage;
        gsu = new GsonUtil();

        // Build dummy toets met vragen
        Vraag vraag1 = new Vraag();
        vraag1.setVraagType("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag1.setId(1);
        vraag1.setName("Vraag 1");
        vraag1.setData("{ \"nodes\": [ { \"name\": \"A\", \"connectedNodes\": [ { \"nodeInfo\": \"B\", \"distance\": 1 }, { \"nodeInfo\": \"C\", \"distance\": 7 } ] }, { \"name\": \"B\", \"connectedNodes\": [ { \"nodeInfo\": \"E\", \"distance\": 4 }, { \"nodeInfo\": \"D\", \"distance\": 2 } ] }, { \"name\": \"C\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 3 }, { \"nodeInfo\": \"D\", \"distance\": 3 } ] }, { \"name\": \"D\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 1 } ] }, { \"name\": \"E\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 1 } ] }, { \"name\": \"F\", \"connectedNodes\": [] } ], \"startNode\": \"A\", \"vraagText\": \"Laat stap voor stap in de onderstaande graaf zien hoe de kortste weg van node A naar alle andere\\nnodes wordt gevonden. Teken voor iedere stap de graaf en geef de lengte van het gevonden pad\\nnaar de vertices aan bij de desbetreffende vertices. Maak daarvoor gebruik van het algoritme van Dijkstra.\" }");

        Vraag vraag2 = new Vraag();
        vraag2.setId(2);
        vraag2.setName("Vraag 2");
        vraag2.setVraagType("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag2.setData("{ \"nodes\": [ { \"name\": \"1\", \"connectedNodes\": [ { \"nodeInfo\": \"2\" }, { \"nodeInfo\": \"5\" } ] }, { \"name\": \"2\", \"connectedNodes\": [ { \"nodeInfo\": \"3\" }, { \"nodeInfo\": \"5\" } ] }, { \"name\": \"5\", \"connectedNodes\": [] }, { \"name\": \"3\", \"connectedNodes\": [ { \"nodeInfo\": \"4\" } ] }, { \"name\": \"4\", \"connectedNodes\": [ { \"nodeInfo\": \"5\" }, { \"nodeInfo\": \"6\" } ] }, { \"name\": \"6\", \"connectedNodes\": [] } ], \"startNode\": \"1\", \"vraagText\": \"Geef de kortste pad vanuit 1 naar alle andere nodes.\" }");

        List<Vraag> vragen = new ArrayList<>();
        vragen.add(vraag1);
        vragen.add(vraag2);

        Tentamen toets = new Tentamen();
        toets.setTentamenId("1");
        toets.setNaam("Toets 1");
        toets.setVragen(vragen);

        // Assign dummy toets to currentToets
        currentToets = toets;

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

    public void loadAnswerView() {
        // TODO: De JSON string in de getView() methode onderin is nu fake en moet straks uit de "cache" komen
//        String dummyJSON = "{ \"steps\": [ { \"rows\": [ { \"targetNode\": \"A\", \"distanceToTarget\": 1, \"isDone\": false }, { \"targetNode\": \"B\", \"distanceToTarget\": 2, \"isDone\": false }, { \"targetNode\": \"C\", \"distanceToTarget\": 3, \"isDone\": false }, { \"targetNode\": \"D\", \"distanceToTarget\": 0, \"isDone\": false }, { \"targetNode\": \"E\", \"distanceToTarget\": 0, \"isDone\": false }, { \"targetNode\": \"F\", \"distanceToTarget\": 0, \"isDone\": false } ], \"fromNode\": \"C\", \"totalDistance\": 3, \"isCorrect\": false }, { \"rows\": [ { \"targetNode\": \"F\", \"distanceToTarget\": 1, \"isDone\": false }, { \"targetNode\": \"E\", \"distanceToTarget\": 2, \"isDone\": false }, { \"targetNode\": \"D\", \"distanceToTarget\": 3, \"isDone\": false }, { \"targetNode\": \"C\", \"distanceToTarget\": 0, \"isDone\": false }, { \"targetNode\": \"B\", \"distanceToTarget\": 0, \"isDone\": false }, { \"targetNode\": \"A\", \"distanceToTarget\": 0, \"isDone\": false } ], \"fromNode\": \"F\", \"totalDistance\": 4, \"isCorrect\": false } ] }";
        answerPane.getChildren().add(currentPlugin.getAntwoordView().getView());
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
