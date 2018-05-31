package nl.han.toetsapplicatie.module.config;

import java.net.URL;

public class ConfigTentamenUitvoerenModule {

    public static URL getFXMLTentamenUitvoeren() {
        return ConfigTentamenUitvoerenModule.class.getResource("/fxml/tentamenUitvoeren.fxml");
    }

}