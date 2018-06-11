package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import nl.han.toetsplatform.module.uitvoeren_tentamen.util.AESCipher;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Tentamen {

    private GsonUtil gsonUtil;

    private String id;
    private int studentNr;
    private String naam;
    private String vragen;
    private String hash;
    private List<Antwoord> antwoorden;
    private List<Vraag> vraagList;
    private String beschrijving;
    private Date startdatum;
    private String strStartdatum;
    private Versie versie;

    public Tentamen() {
        this.gsonUtil = new GsonUtil();
    }

    public List<Vraag> getVragen() {
        return vraagList;
    }

    public String getId() {
        return id;
    }

    public void setId(String tentamenId) {
        this.id = tentamenId;
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

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startDatum) {
        this.startdatum = startDatum;
    }

    public Versie getVersie() {
        return versie;
    }

    public void setVersie(Versie versie) {
        this.versie = versie;
    }

    public void decryptVragen(String token) throws Exception {
        String decrypted = AESCipher.decrypt(token, this.vragen);
        this.vraagList = this.gsonUtil.vragenJSONToList(decrypted);
    }

    public String getStrStartdatum() {

        if (this.strStartdatum == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            this.strStartdatum = sdf.format(this.startdatum);
        }

        return strStartdatum;
    }
}
