package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen.DownloadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen.IDownloadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DownloadenTentamenController extends Controller {

    @FXML
    public VBox loadingIndicator;
    public TableView tblViewTentamens;
    public TableColumn nameColumn;
    public TableColumn descriptionColumn;
    public TableColumn dateColumn;

    private IDownloadenTentamenDAO dManager;
    private List<Tentamen> tentamens = null;

    public void initialize() {
        dManager = new DownloadenTentamenDAO();

        this.loadView();
    }

    public void loadView() {
        tblViewTentamens.setVisible(false);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Tentamen,String>("naam"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tentamen,String>("beschrijving"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Tentamen,String>("strStartDatum"));

         new Thread(() -> {
             try {
                 tentamens = dManager.getKlaargezetteTentamens();
             } catch (IOException | ParseException e) {
                 e.printStackTrace();
                 AlertError("Er is iets fout gegaan, probeer opnieuw.");
             }

             if (tentamens != null) {
                 for (Tentamen tentamen : tentamens) {
                    tblViewTentamens.getItems().add(tentamen);
                }
             }

             tblViewTentamens.setVisible(true);
             loadingIndicator.setVisible(false);
        }).start();
    }

    @FXML
    public void downloadPressed() {
        int tentamenIndex = tblViewTentamens.getSelectionModel().getSelectedIndex();
        System.out.println(tentamens.get(tentamenIndex));
    }


    @FXML
    public void btnExitPressed() {
        Platform.runLater(() -> {
            loadingIndicator.setVisible(false);
            AlertError("Er is iets fout gegaan, probeer opnieuw.");
        });
    }
}
