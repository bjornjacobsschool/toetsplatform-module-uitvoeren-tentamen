package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.uitvoeren_tentamen.config.ConfigTentamenUitvoerenModule;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen.DownloadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen.IDownloadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.JSONReader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class GedownloadeTentamensController extends Controller {

    @FXML
    public VBox loadingIndicator;
    public TableView tblViewTentamens;
    public TableColumn nameColumn;
    public TableColumn descriptionColumn;
    public TableColumn dateColumn;

    @Inject
    private GuiceFXMLLoader fxmlLoader;


    private Stage primaryStage;
    private GsonUtil gsu;

    private IDownloadenTentamenDAO dManager;
    private List<Tentamen> tentamens = null;

    public GedownloadeTentamensController() {
        gsu = new GsonUtil();
    }

    public void initialize() {
        dManager = new DownloadenTentamenDAO(new JSONReader(), new GsonUtil());

        this.loadView();
    }

    public void loadView() {
        loadingIndicator.setVisible(false);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Tentamen, String>("naam"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tentamen, String>("beschrijving"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Tentamen, String>("strStartdatum"));

        this.reloadView(primaryStage);
    }

    public void reloadView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        tblViewTentamens.getItems().clear();

        try {
            tentamens = dManager.getDownloadedTentamens();
        } catch (IOException | ParseException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        }

        for (Tentamen tentamen : tentamens) {
            tblViewTentamens.getItems().add(tentamen);
        }
    }

    @FXML
    public void btnExitPressed() {
        Platform.runLater(() -> {
            loadingIndicator.setVisible(false);
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        });
    }

    @FXML
    public void startTentamen(ActionEvent actionEvent) {
        int tentamenIndex = tblViewTentamens.getSelectionModel().getSelectedIndex();
        if (tentamenIndex == -1 || tentamenIndex > tentamens.size() - 1) {
            AlertError("Selecteer eerst de tentamen die je wilt starten.");
            return;
        }
        String tentamenId = tentamens.get(tentamenIndex).getId();
        Tentamen tentamen = gsu.loadTentamen(Utils.getFolder(Utils.DOWNLOADED_TENTAMENS) + "/exam_" + tentamenId + ".json");

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Sleutel invoeren");
        dialog.setHeaderText("Voer de sleutel in die je van de surveillant hebt gekregen.");
        dialog.setContentText("");


        String decryptionToken = null;
        Optional<String> dResult = dialog.showAndWait();
        if (dResult.isPresent()) {
            decryptionToken = dResult.get();
        }

        if (decryptionToken == null || decryptionToken.equals("")) {
            AlertError("Voer de sleutel in die je van de surveillant hebt gekregen.");
            return;
        }

        boolean tentamenVragenDecrypted = true;

        try {
            tentamen.decryptVragen(decryptionToken);
        } catch (Exception e) {
            tentamenVragenDecrypted = false;
            Utils.logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }

        if (!tentamenVragenDecrypted) {
            AlertError("De sleutel die je hebt ingevoerd is onjuist.");
            return;
        }

        try {
            GuiceFXMLLoader.Result result = fxmlLoader.load(ConfigTentamenUitvoerenModule.getFXMLTentamenUitvoeren(), null);

            TentamenUitvoerenController controller = result.getController();
            controller.setUp(primaryStage, tentamen);
            primaryStage.getScene().setRoot(result.getRoot());

        } catch (IOException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
