package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.text.SimpleDateFormat;
import java.util.List;

class Controller {

    void AlertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    void AlertInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.show();
    }

    static void loadTable(List<Tentamen> tentamens, TableView tblViewTentamens) {
        if (tentamens != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            for (Tentamen tentamen : tentamens) {
                if (tentamen.getStrStartdatum() == null) {
                    if (tentamen.getStartdatum() != null) {
                        tentamen.setStrStartdatum(sdf.format(tentamen.getStartdatum()));
                    }
                }
                tblViewTentamens.getItems().add(tentamen);
            }
        }
    }
}
