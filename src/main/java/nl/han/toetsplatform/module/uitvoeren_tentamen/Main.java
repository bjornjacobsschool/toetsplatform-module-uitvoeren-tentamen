package nl.han.toetsplatform.module.uitvoeren_tentamen;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Inject;
import com.google.inject.Module;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.uitvoeren_tentamen.config.ConfigTentamenUitvoerenModule;
import nl.han.toetsplatform.module.uitvoeren_tentamen.controllers.DownloadenTentamenController;
import nl.han.toetsplatform.module.uitvoeren_tentamen.controllers.TentamenUitvoerenController;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends GuiceApplication {

    private static Logger logger = Logger.getLogger("toetsplatform-module-uitvoeren-tentamen");

    @Inject
    private GuiceFXMLLoader fxmlLoader;

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(List<Module> list) {
        list.add(ConfigTentamenUitvoerenModule.getAbstractModule());
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene scene = new Scene(new Label("If you see this something is wrong"), 640, 480);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Standalone Module - Uitvoeren Tentamen");
        primaryStage.show();

//        // Tentamen uitvoeren scherm weergeven
//        tentamenUitvoeren();
        downloadTentamen();
    }

    private void tentamenUitvoeren() {
        try {
            GuiceFXMLLoader.Result result = fxmlLoader.load(ConfigTentamenUitvoerenModule.getFXMLTentamenUitvoeren(), null);

            TentamenUitvoerenController controller = result.getController();
            controller.setUp(primaryStage);

            primaryStage.getScene().setRoot(result.getRoot());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void downloadTentamen() {
        try {
            GuiceFXMLLoader.Result result = fxmlLoader.load(ConfigTentamenUitvoerenModule.getFXMLDownloadenTentamen(), null);

            DownloadenTentamenController controller = result.getController();

            primaryStage.getScene().setRoot(result.getRoot());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }

}
