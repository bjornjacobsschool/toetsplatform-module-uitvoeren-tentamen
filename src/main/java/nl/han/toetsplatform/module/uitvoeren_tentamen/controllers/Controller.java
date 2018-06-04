package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

class Controller {

    void AlertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    void AlertInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.show();
    }
}
