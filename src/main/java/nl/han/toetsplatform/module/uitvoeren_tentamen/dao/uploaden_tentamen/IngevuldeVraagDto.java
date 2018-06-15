package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Versie;

import java.util.UUID;

public class IngevuldeVraagDto {
    private UUID id;
    private String gegevenAntwoord;

    private VersieDto versie;
    IngevuldeVraagDto(UUID id, Antwoord antwoord, VersieDto versie){
        this.id = id;
        this.gegevenAntwoord = antwoord.getGegevenAntwoord();
        this.versie = versie;
    }

    IngevuldeVraagDto(UUID id, String antwoord, VersieDto versie){
        this.id = id;
        this.gegevenAntwoord = antwoord;
        this.versie = versie;
    }
}
