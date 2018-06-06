package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

public class Vraag {

    private int id;
    private String name;
    private String description;
    private String vraagType;
    private String plugin;
    private String data;

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVraagType() {
        return vraagType;
    }

    public void setVraagType(String vraagType) {
        this.vraagType = vraagType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
