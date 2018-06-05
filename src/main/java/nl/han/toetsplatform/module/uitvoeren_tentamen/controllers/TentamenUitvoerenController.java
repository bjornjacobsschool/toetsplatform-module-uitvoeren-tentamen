package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import nl.han.toetsplatform.module.shared.model.Toets;
import nl.han.toetsplatform.module.shared.model.Vraag;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
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
    private int currentQuestionIndex = 0;
    private GsonUtil gsu;

    public void setUp() {
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

        gsu = new GsonUtil();

        // Build dummy toets met vragen
        Vraag vraag1 = new Vraag();
        vraag1.setPlugin("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag1.setId(1);
        vraag1.setName("Vraag 1");
        vraag1.setData("{ \"nodes\": [ { \"name\": \"A\", \"connectedNodes\": [ { \"nodeInfo\": \"B\", \"distance\": 1 }, { \"nodeInfo\": \"C\", \"distance\": 7 } ] }, { \"name\": \"B\", \"connectedNodes\": [ { \"nodeInfo\": \"E\", \"distance\": 4 }, { \"nodeInfo\": \"D\", \"distance\": 2 } ] }, { \"name\": \"C\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 3 }, { \"nodeInfo\": \"D\", \"distance\": 3 } ] }, { \"name\": \"D\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 1 } ] }, { \"name\": \"E\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 1 } ] }, { \"name\": \"F\", \"connectedNodes\": [] } ], \"vraagText\": \"Laat stap voor stap in de onderstaande graaf zien hoe de kortste weg van node A naar alle andere\\nnodes wordt gevonden. Teken voor iedere stap de graaf en geef de lengte van het gevonden pad\\nnaar de vertices aan bij de desbetreffende vertices. Maak daarvoor gebruik van het algoritme van Dijkstra.\" }");

        Vraag vraag2 = new Vraag();
        vraag2.setId(2);
        vraag2.setName("Vraag 2");
        vraag2.setPlugin("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag2.setData("{ \"nodes\": [ { \"name\": \"1\", \"connectedNodes\": [ { \"nodeInfo\": \"2\" }, { \"nodeInfo\": \"5\" } ] }, { \"name\": \"2\", \"connectedNodes\": [ { \"nodeInfo\": \"3\" }, { \"nodeInfo\": \"5\" } ] }, { \"name\": \"5\", \"connectedNodes\": [] }, { \"name\": \"3\", \"connectedNodes\": [ { \"nodeInfo\": \"4\" } ] }, { \"name\": \"4\", \"connectedNodes\": [ { \"nodeInfo\": \"5\" }, { \"nodeInfo\": \"6\" } ] }, { \"name\": \"6\", \"connectedNodes\": [] } ], \"vraagText\": \"Geef de kortste pad vanuit 1 naar alle andere nodes.\" }");

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

    public void showExercise() {
        // Clear question & answer panes
        questionPane.getChildren().clear();
        answerPane.getChildren().clear();

        lblCurrentQuestionNo.setText((currentQuestionIndex + 1) + "/" + currentToets.getVragen().size());

        loadQuestionView();
        loadAnswerView();
    }

    public void loadQuestionView() {
        try {
            Node questionView = getPluginForCurrentQuestion().getVraagView().getView();
            questionPane.getChildren().add(questionView);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            AlertError("Could not load question view (ClassNotFoundException)");
        }
    }

    public void loadAnswerView() {
        try {
            Node answerView = getPluginForCurrentQuestion().getAntwoordView().getView(null);
            answerPane.getChildren().add(answerView);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            AlertError("Could not load question view (ClassNotFoundException)");
        }
    }

    public Plugin getPluginForCurrentQuestion() throws ClassNotFoundException {
        return PluginLoader.getPlugin(currentToets.getVragen().get(currentQuestionIndex));
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
        //DirectoryChooser directoryChooser = new DirectoryChooser();
        //File selectedDirectory = directoryChooser.showDialog();
        //currentToets = gsu.loadTentamen("%appdata%\\Toetsapplicatie\\TestTentamen.json");
        //showExercise();
        AlertInfo("Test");
    }

}
