package nl.han.toetsapplicatie.module.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nl.han.toetsapplicatie.module.cache.CacheDao;
import nl.han.toetsapplicatie.module.cache.mock.CacheDaoMock;
import nl.han.toetsapplicatie.module.model.Toets;
import nl.han.toetsapplicatie.module.model.Vraag;
import nl.han.toetsapplicatie.module.plugin.Plugin;
import nl.han.toetsapplicatie.module.plugin.PluginLoader;

import static nl.han.toetsapplicatie.module.util.RunnableUtil.runIfNotNull;

public class TentamenUitvoerenController {

    public VBox loadingIndicator;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrev;

    @FXML
    private Label currentQuestionLbl;

    @FXML
    private AnchorPane questionsContainer;
    public AnchorPane answersContainer;

    private Toets toets;
    private Plugin plugin;
    int currentVraagIndex = 0;
    private Runnable onExit;
    private CacheDao cacheDao;

    public Vraag getCurrentVraag() {
        return toets.getVragen().get(currentVraagIndex);
    }

    public void setToets(Toets toets) {
        this.toets = toets;
        cacheDao = new CacheDaoMock();
        loadCurrentQuestion(0);
    }

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    public void loadCurrentQuestion(int change) {
        if (plugin != null) {
            String data = plugin.getAntwoordView().getGivenAntwoord();
            cacheDao.saveQuestion(toets.id, getCurrentVraag().getId(), data);
        }

        questionsContainer.getChildren().clear();
        answersContainer.getChildren().clear();
        currentVraagIndex += change;
        loadingIndicator.setVisible(true);
        currentQuestionLbl.setText((currentVraagIndex + 1) + "/" + toets.getVragen().size());
        new Thread(this::loadView).start();
    }

    public void loadView() {
        try {
            String vraagData = cacheDao.getAntwoordData(toets.id, getCurrentVraag().getId());

            plugin = PluginLoader.getPlugin(getCurrentVraag());
            Node view = plugin.getVraagView().getView();
            Node answerView = plugin.getAntwoordView().getView(vraagData);
            Platform.runLater(() -> {
                questionsContainer.getChildren().add(view);
                answersContainer.getChildren().add(answerView);
                loadingIndicator.setVisible(false);
            });
        } catch (ClassNotFoundException e) {
            Platform.runLater(() -> {
                loadingIndicator.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load Plugin!", ButtonType.OK);
                alert.show();
            });
        }
    }

    @FXML
    public void btnPrevPressed(ActionEvent event) {
        String d = plugin.getAntwoordView().getGivenAntwoord();
        if (currentVraagIndex > 0)
            loadCurrentQuestion(-1);
    }

    @FXML
    public void btnNextPressed(ActionEvent event) {
        if (currentVraagIndex < toets.getVragen().size() - 1)
            loadCurrentQuestion(1);
    }

    @FXML
    public void btnExitPressed(ActionEvent event) {
        runIfNotNull(onExit);
    }
}
