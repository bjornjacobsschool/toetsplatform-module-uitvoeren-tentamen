package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nl.han.toetsplatform.module.shared.model.Vraag;
import nl.han.toetsplatform.module.shared.plugin.Plugin;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.CacheDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.Toets;

public class TentamenUitvoerenController {

    @FXML
    private Label currentQuestionLbl;

    @FXML
    private AnchorPane questionsContainer;
    public AnchorPane answersContainer;

    public VBox loadingIndicator;

    @Inject
    private CacheDao cacheDao;

    private Toets toets;
    private Plugin plugin;
    private int currentVraagIndex = 0;

    public void setToets(Toets toets) {
        this.toets = toets;
        loadCurrentQuestion(0);
    }

    public Vraag getCurrentVraag() {
        return toets.getVragen().get(currentVraagIndex);
    }

    public void loadCurrentQuestion(int change) {
        if (plugin != null) {
            String data = plugin.getAntwoordView().getGivenAntwoord();
            cacheDao.saveQuestion(toets.getId(), getCurrentVraag().getId(), data);
        }

        questionsContainer.getChildren().clear();
        answersContainer.getChildren().clear();
        currentVraagIndex += change;
        loadingIndicator.setVisible(true);
        currentQuestionLbl.setText((currentVraagIndex + 1) + "/" + toets.getVragen().size());

        // Load view in new thread
        new Thread(this::loadView).start();
    }

    public void loadView() {
        try {
            plugin = PluginLoader.getPlugin(getCurrentVraag());

            String cachedAntwoordData = cacheDao.getAntwoordData(toets.getId(), getCurrentVraag().getId());

            Node questionView = plugin.getVraagView().getView();
            Node answerView = plugin.getAntwoordView().getView(cachedAntwoordData);

            Platform.runLater(() -> {
                questionsContainer.getChildren().add(questionView);
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
        Platform.runLater(() -> {
            loadingIndicator.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Exit pressed, code something here!", ButtonType.OK);
            alert.show();
        });
    }
}
