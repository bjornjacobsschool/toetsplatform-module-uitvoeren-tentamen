package nl.han.toetsplatform.module.uitvoeren_tentamen.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.han.toetsplatform.module.shared.plugin.PluginLoader;

import java.util.List;


public class HomeController extends Controller {

    @FXML
    private Label lblNetworkStatus;

    @FXML
    private ListView lvLoadedPlugins;

    public void initialize() {
        loadPlugins();
    }

    public void loadPlugins() {
        List<Class> plugins = PluginLoader.getPlugins();
        ObservableList list = FXCollections.observableArrayList();
        for (Class p : plugins) {
            int index = p.getName().lastIndexOf('.');
            String pluginType = p.getName().substring(index + 1);

            list.add(pluginType);
        }

        lvLoadedPlugins.setItems(list);
    }

}
