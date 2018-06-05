package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class DownloadenUitvoerenTabPaneController extends Controller {

    @FXML
    public Tab downloadTentamenTab;
    public DownloadenTentamenController downloadenTentamenController;

    @FXML
    public Tab gedownloadeTentamensTab;
    public GedownloadeTentamensController gedownloadeTentamensController;

    @FXML
    public TabPane tabs;

    public void initialize() {
        tabs.getSelectionModel().selectedItemProperty().addListener(
                (ov, currentTab, newTab) -> {
                    if (newTab.getId().equals("gedownloadeTentamensTab")) {
                        gedownloadeTentamensController.reloadView();
                    } else {
                        downloadenTentamenController.reloadView();
                    }
                }
        );
    }
}