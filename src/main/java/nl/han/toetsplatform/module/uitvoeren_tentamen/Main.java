package nl.han.toetsplatform.module.uitvoeren_tentamen;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Inject;
import com.google.inject.Module;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.han.toetsplatform.module.uitvoeren_tentamen.config.ConfigTentamenUitvoerenModule;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.Utils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class Main extends GuiceApplication {

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

        initializePage();
    }

    private void initializePage() {
        try {
            GuiceFXMLLoader.Result result = fxmlLoader.load(ConfigTentamenUitvoerenModule.getFXMLDownloadenUitvoerenTabPaneController(), null);

            primaryStage.getScene().setRoot(result.getRoot());
        } catch (IOException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }
    }

}
