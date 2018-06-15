package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Versie;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

public class VersieDto {
    private long datum;
    private int nummer;
    private String omschrijving;

    VersieDto(Versie versie){
        this.datum = versie.getDatum().getTime();
        //TODO
        //this.nummer = Integer.parseInt(versie.getNummer().replaceAll("[^\\d.]", ""));
        this.omschrijving = versie.getOmschrijving();
    }

    VersieDto(Long datum, int nummer, String omschrijving){
        this.datum = datum;
        this.nummer = nummer;
        this.omschrijving = omschrijving;
    }

}
