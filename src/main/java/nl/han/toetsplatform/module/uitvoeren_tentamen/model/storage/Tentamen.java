package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import nl.han.toetsplatform.module.uitvoeren_tentamen.util.AESCipher;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Tentamen {

    private GsonUtil gsonUtil;

    private String id;
    private String naam;
    private String vragen;
    private List<Antwoord> antwoorden;
    private List<Vraag> vraagList;
    private String beschrijving;
    private String toegestaneHulpmiddelen;
    private String tijdsduur;
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

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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

        if (this.strStartdatum == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            this.strStartdatum = sdf.format(this.startdatum);
        }
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
        return strStartdatum;
    }

    public void setStrStartdatum(String strStartdatum) {
        this.strStartdatum = strStartdatum;
    }

    public String getToegestaneHulpmiddelen() {
        return toegestaneHulpmiddelen;
    }

    public void setToegestaneHulpmiddelen(String toegestaneHulpmiddelen) {
        this.toegestaneHulpmiddelen = toegestaneHulpmiddelen;
    }

    public String getTijdsduur() {
        return tijdsduur;
    }

    public void setTijdsduur(String tijdsduur) {
        this.tijdsduur = tijdsduur;
    }

    public void setVragen(String vragen) {
        this.vragen = vragen;
    }
}
