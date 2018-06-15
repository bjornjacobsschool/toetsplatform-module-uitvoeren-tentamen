package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Student;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UitgevoerdTentamenDto {
    private UUID id;
    private String naam;
    private String hash;

    private StudentDto student;
    private VersieDto versie;
    private List<IngevuldeVraagDto> vragen;

    public UitgevoerdTentamenDto(Tentamen tentamen, Student student){
        this.id = java.util.UUID.fromString("d3aa88e2-c754-41e0-8ba6-4198a34aa0a2");//TODO this is a temporary UUID //tentamen.getId());
        this.naam = tentamen.getNaam();
        this.hash = "temporaryhash"; //TODO this is a temporary hash

        this.student = new StudentDto(student);
        this.versie = new VersieDto(tentamen.getVersie());


        this.vragen = new ArrayList<IngevuldeVraagDto>();
        for(int i = 0; i < tentamen.getAntwoorden().size(); i++){
            vragen.add(new IngevuldeVraagDto(id,tentamen.getAntwoorden().get(i),versie));
        }

    }

}
