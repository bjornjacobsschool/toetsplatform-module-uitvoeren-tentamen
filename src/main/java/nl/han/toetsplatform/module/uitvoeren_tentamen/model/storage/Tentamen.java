package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Tentamen {

    private String tentamenId;
    private int studentNr;
    private String naam;
    private String hash;
    private List<Antwoord> antwoorden;
    private List<Vraag> vragen;
    private String beschrijving;
    private Date startDatum;
    private String strStartDatum;
    private Versie versie;

    public List<Vraag> getVragen() {
        return vragen;
    }

    public void setVragen(List<Vraag> vragen) {
        this.vragen = vragen;
    }

    public String getTentamenId() {
        return tentamenId;
    }

    public void setTentamenId(String tentamenId) {
        this.tentamenId = tentamenId;
    }

    public int getStudentNr() {
        return studentNr;
    }

    public void setStudentNr(int studentNr) {
        this.studentNr = studentNr;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<Antwoord> getAntwoorden() {
        return antwoorden;
    }

    public void setAntwoorden(List<Antwoord> antwoorden) {
        this.antwoorden = antwoorden;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Date getStartDatum() {
        return startDatum;
    }

    public void setStartDatum(Date startDatum) {
        this.startDatum = startDatum;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        this.strStartDatum = sdf.format(startDatum);
    }

    public Versie getVersie() {
        return versie;
    }

    public void setVersie(Versie versie) {
        this.versie = versie;
    }

    public String getStrStartDatum() {
        return strStartDatum;
    }
}
