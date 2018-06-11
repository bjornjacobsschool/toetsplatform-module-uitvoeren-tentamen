package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

public class Vraag {

    private String id;
    private String naam;
    private String description;
    private String vraagType;
    private String plugin;
    private String thema;
    private int punten;
    private String data;
    private Versie versie;

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public int getPunten() {
        return punten;
    }

    public void setPunten(int punten) {
        this.punten = punten;
    }

    public Versie getVersie() {
        return versie;
    }

    public void setVersie(Versie versie) {
        this.versie = versie;
    }
}
