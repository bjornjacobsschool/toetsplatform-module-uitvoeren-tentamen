package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class DownloadenUitvoerenTabPaneController extends Controller {

    @FXML
    public Tab downloadTentamenTab;
    public DownloadenTentamenController downloadenTentamenController;

    @FXML
    public Tab gedownloadeTentamensTab;
    public GedownloadeTentamensController gedownloadeTentamensController;

    @FXML
    private Tab inteleverenTenamensTab;
    @FXML
    private InteleverenTentamenController inteleverenTentamenController;

    private Stage primarayStage;

    @FXML
    public TabPane tabs;

    public void initialize() {
        tabs.getSelectionModel().selectedItemProperty().addListener(
                (ov, currentTab, newTab) -> {
                    if (newTab.getId().equals("gedownloadeTentamensTab")) {
                        gedownloadeTentamensController.reloadView(primarayStage);
                    } else if (newTab.getId().equals("downloadTentamenTab")) {
                        downloadenTentamenController.reloadView(primarayStage);
                    } else {
                        inteleverenTentamenController.reloadView(primarayStage);
                    }
                }
        );
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primarayStage = primaryStage;
    }
}
