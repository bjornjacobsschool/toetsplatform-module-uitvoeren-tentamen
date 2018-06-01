package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

public class Antwoord {

    private String vraagId;
    private String tentamenId;
    private String gegevenAntwoord;

    public Antwoord() {

    }

    public Antwoord(String vraagId, String tentamenId, String gegevenAntwoord) {
        this.vraagId = vraagId;
        this.tentamenId = tentamenId;
        this.gegevenAntwoord = gegevenAntwoord;
    }

    public String getVraagId() {
        return vraagId;
    }

    public void setVraagId(String vraagId) {
        this.vraagId = vraagId;
    }

    public String getTentamenId() {
        return tentamenId;
    }

    public void setTentamenId(String tentamenId) {
        this.tentamenId = tentamenId;
    }

    public String getGegevenAntwoord() {
        return gegevenAntwoord;
    }

    public void setGegevenAntwoord(String gegevenAntwoord) {
        this.gegevenAntwoord = gegevenAntwoord;
    }

}
