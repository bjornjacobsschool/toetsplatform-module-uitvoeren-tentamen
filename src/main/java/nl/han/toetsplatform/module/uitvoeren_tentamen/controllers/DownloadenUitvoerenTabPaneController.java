package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public class DownloadenUitvoerenTabPaneController extends Controller {

    @FXML
    public Tab downloadTentamenTab;
    public DownloadenTentamenController downloadenTentamenController;

    @FXML
    public Tab gedownloadeTentamensTab;
    public GedownloadeTentamensController gedownloadeTentamensController;

    private Stage primarayStage;

    @FXML
    public TabPane tabs;

    @Inject
    private StorageSetupDao storageSetupDao;

    public void initialize() {

        // Setup SQLite
        try {
            storageSetupDao.setup();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }

        tabs.getSelectionModel().selectedItemProperty().addListener(
                (ov, currentTab, newTab) -> {
                    if (newTab.getId().equals("gedownloadeTentamensTab")) {
                        gedownloadeTentamensController.reloadView(primarayStage);
                    } else {
                        downloadenTentamenController.reloadView(primarayStage);
                    }
                }
        );
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primarayStage = primaryStage;
    }
}
