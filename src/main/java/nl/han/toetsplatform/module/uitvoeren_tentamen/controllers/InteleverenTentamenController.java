package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.StorageSetupSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.tentamen.TentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen.UitgevoerdTentamenDto;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen.UploadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Student;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;
import org.json.JSONException;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class InteleverenTentamenController extends Controller {

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

    @Inject
    private TentamenDAO dManager;
    @Inject
    private StorageSetupSqlite storageSetupSqlite;
    private List<Tentamen> tentamens = null;
    private Stage primaryStage;

    public void initialize() {
        try {
            storageSetupSqlite.setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.loadView();
    }

    public void loadView() {
        tblViewTentamens.setVisible(false);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Tentamen, String>("naam"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tentamen, String>("beschrijving"));
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

                tentamens = dManager.getLocalTentamens();
            } catch (SQLException | JSONException e) {
                Utils.logger.log(Level.SEVERE, e.getMessage());
                AlertError("Er is iets fout gegaan, probeer opnieuw.");
            }

            Controller.loadTable(tentamens, tblViewTentamens);

            tblViewTentamens.setVisible(true);
            loadingIndicator.setVisible(false);
        }).start();
    }

    @FXML
    public void uploadPressed() {
        int tentamenIndex = tblViewTentamens.getSelectionModel().getSelectedIndex();
        if (tentamenIndex == -1 || tentamenIndex > tentamens.size() - 1) {
            AlertError("Selecteer eerst de tentamen die je wilt downloaden.");
            return;
        }

        if (!Utils.checkInternetConnection()) {
            AlertError("U heeft geen internet connectie. Maak verbinding met het internet en probeer opnieuw te uploaden.");
            return;
        }

        String result = null;

        UploadenTentamenDAO uploadenTentamenDAO = new UploadenTentamenDAO();
        for (Tentamen tentamen :
                tentamens) {
            uploadenTentamenDAO.superUploadTentamen(tentamen, new Student());
            //TODO zet ingeleverd

        }


        // Tentamen inteleverenTentamen = result.get(tentamenIndex);

       /* try {
            //TODO zorg dat het uploaden van een tentamen wordt aangeroepen.
        } catch ( SQLException | JSONException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        }*/

/*        if (!result.isEmpty()) {
            AlertInfo("Het tentamen is succesvol lokaal opgeslagen.");
        } else {
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        }*/

    }


    @FXML
    public void btnExitPressed() {
        Platform.runLater(() -> {
            loadingIndicator.setVisible(false);
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        });
    }
}
