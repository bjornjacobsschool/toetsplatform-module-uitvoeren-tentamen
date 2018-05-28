package nl.han.toetsapplicatie.module;

import java.net.URL;

public class ModuleTentamenUitvoeren {

    private static ModuleTentamenUitvoeren instance;

    private ModuleTentamenUitvoeren() {

    }

    public static ModuleTentamenUitvoeren getInstance() {
        if (instance == null)
            instance = new ModuleTentamenUitvoeren();

        return instance;
    }

    public URL getFXML_URL() {
        return getClass().getResource("/fxml/tentamenUitvoeren.fxml");
    }
}
