package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import java.util.List;

public class Tentamen {

    private String tentamenId;
    private int studentNr;
    private String versieNummer;
    private String naam;
    private String hash;
    private List<Antwoord> antwoorden;

    public Tentamen() {

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

    public String getVersieNummer() {
        return versieNummer;
    }

    public void setVersieNummer(String versieNummer) {
        this.versieNummer = versieNummer;
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
}
