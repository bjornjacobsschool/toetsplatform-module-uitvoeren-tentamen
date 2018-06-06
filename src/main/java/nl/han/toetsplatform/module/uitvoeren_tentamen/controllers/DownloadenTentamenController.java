package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.uitvoeren_tentamen.config.ConfigTentamenUitvoerenModule;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.JSONReader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen.DownloadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen.IDownloadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;

public class DownloadenTentamenController extends Controller {

    @FXML
    public VBox loadingIndicator;
    public TableView tblViewTentamens;
    public TableColumn nameColumn;
    public TableColumn descriptionColumn;
    public TableColumn dateColumn;
    public Button btnDownload;

    @Inject
    private GuiceFXMLLoader fxmlLoader;


    private Stage primaryStage;

    private IDownloadenTentamenDAO dManager;
    private List<Tentamen> tentamens = null;

    public void initialize() {
        dManager = new DownloadenTentamenDAO(new JSONReader());

        this.loadView();
    }

    public void loadView() {
        tblViewTentamens.setVisible(false);
        btnDownload.setVisible(false);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Tentamen,String>("naam"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tentamen,String>("beschrijving"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Tentamen,String>("strStartDatum"));

         new Thread(() -> {
             try {
                 tentamens = dManager.getKlaargezetteTentamens();
             } catch (IOException | ParseException | JSONException e) {
                 e.printStackTrace();
                 AlertError("Er is iets fout gegaan, probeer opnieuw.");
             }

             if (tentamens != null) {
                 for (Tentamen tentamen : tentamens) {
                    tblViewTentamens.getItems().add(tentamen);
                }
             }

             tblViewTentamens.setVisible(true);
             btnDownload.setVisible(true);
             loadingIndicator.setVisible(false);
        }).start();
    }

    @FXML
    public void downloadPressed() {
        int tentamenIndex = tblViewTentamens.getSelectionModel().getSelectedIndex();
        if (tentamenIndex == -1 || tentamenIndex > tentamens.size()-1) {
            return;
        }

        boolean result = false;
        try {
            result = dManager.downloadTentamen(tentamens.get(tentamenIndex).getTentamenId());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
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

    public void btnStartPressed(ActionEvent actionEvent) {
        try {
            GuiceFXMLLoader.Result result = fxmlLoader.load(ConfigTentamenUitvoerenModule.getFXMLTentamenUitvoeren(), null);

            TentamenUitvoerenController controller = result.getController();
            controller.setUp(primaryStage);

            loadingIndicator.setVisible(true);
            primaryStage.getScene().setRoot(result.getRoot());
            loadingIndicator.setVisible(false);
        } catch (IOException e) {
            //
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
