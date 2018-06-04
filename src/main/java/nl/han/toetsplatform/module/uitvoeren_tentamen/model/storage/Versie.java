package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import java.util.Date;

public class Versie {
    private Date datum;
    private String nummer;
    private String omschrijving;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
