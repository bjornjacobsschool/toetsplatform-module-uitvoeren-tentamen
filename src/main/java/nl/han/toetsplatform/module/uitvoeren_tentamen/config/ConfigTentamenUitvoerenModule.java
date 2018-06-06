package nl.han.toetsplatform.module.uitvoeren_tentamen.config;

import com.google.inject.AbstractModule;

import java.net.URL;

public class ConfigTentamenUitvoerenModule {

    public static URL getFXMLTentamenUitvoeren() {
        return ConfigTentamenUitvoerenModule.class.getResource("/fxml/tentamenUitvoeren.fxml");
    }


    public static URL getFXMLDownloadenTentamen() {
        return ConfigTentamenUitvoerenModule.class.getResource("/fxml/downloadenTentamen.fxml");
    }

    public static URL getFXMLDownloadenUitvoerenTabPaneController() {
        return ConfigTentamenUitvoerenModule.class.getResource("/fxml/downloadenUitvoerenTabPaneController.fxml");
    }

    public static AbstractModule getAbstractModule() {
        return new GuiceModule();
    }

}