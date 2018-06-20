package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen.DownloadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen.IDownloadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.JSONReader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;

public class DownloadenTentamenController extends Controller {


    @FXML
    public VBox loadingIndicator;
    @FXML
    public TableView tblViewTentamens;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn descriptionColumn;
    @FXML
    public TableColumn dateColumn;

    private IDownloadenTentamenDAO dManager;
    private List<Tentamen> tentamens = null;
    private Stage primaryStage;

    public void initialize() {
        dManager = new DownloadenTentamenDAO(new JSONReader(), new GsonUtil());

        this.loadView();
    }

    public void loadView() {
        if (!Utils.checkInternetConnection()) {
            loadingIndicator.setVisible(false);
            AlertError("U heeft geen internet connectie. Maak verbinding met het internet en start de applicatie opnieuw op.");
            return;
        }

        tblViewTentamens.setVisible(false);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Tentamen,String>("naam"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tentamen,String>("beschrijving"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Tentamen, String>("strStartdatum"));

        this.reloadView(primaryStage);
    }

    public void reloadView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if (!Utils.checkInternetConnection()) {
            loadingIndicator.setVisible(false);
            AlertError("U heeft geen internet connectie. Maak verbinding met het internet en start de applicatie opnieuw op.");
            return;
        }

        tblViewTentamens.setVisible(false);
        loadingIndicator.setVisible(true);

        tblViewTentamens.getItems().clear();


        new Thread(() -> {
            try {
                tentamens = dManager.getKlaargezetteTentamens();
            } catch (IOException | ParseException | JSONException e) {
                Utils.logger.log(Level.SEVERE, e.getMessage());
                AlertError("Er is iets fout gegaan, probeer opnieuw.");
            }

            Controller.loadTable(tentamens, tblViewTentamens);

            tblViewTentamens.setVisible(true);
            loadingIndicator.setVisible(false);
        }).start();
    }

    @FXML
    public void downloadPressed() {
        int tentamenIndex = tblViewTentamens.getSelectionModel().getSelectedIndex();
        if (tentamenIndex == -1 || tentamenIndex > tentamens.size()-1) {
            AlertError("Selecteer eerst de tentamen die je wilt downloaden.");
            return;
        }

        if (!Utils.checkInternetConnection()) {
            AlertError("U heeft geen internet connectie. Maak verbinding met het internet en probeer opnieuw te downloaden.");
            return;
        }

        boolean result = false;
        try {
            result = dManager.downloadTentamen(tentamens.get(tentamenIndex).getId());
        } catch (IOException | JSONException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        }

        if (result) {
            AlertInfo("Het tentamen is succesvol lokaal opgeslagen.");
        } else {
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        }

    }


    @FXML
    public void btnExitPressed() {
        Platform.runLater(() -> {
            loadingIndicator.setVisible(false);
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        });
    }
}
