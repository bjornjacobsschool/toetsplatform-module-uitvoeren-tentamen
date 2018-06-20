package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.tentamen.TentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TentamenDAOStub implements TentamenDAO {

    @Inject
    private VraagDao vraagDao;

    @Override
    public List<Tentamen> getLocalTentamens() throws SQLException {
        List<Tentamen> tentamens = new ArrayList<>();

        // Dummy tentamen
        Tentamen tentamen = new Tentamen();
        tentamen.setId("ID-VAN-TENTAMEN");
//        tentamen.setStudentNr(123456);
        tentamen.setNaam("Toets APP 1 (Grafen en Paden)");
//        tentamen.setVersieNummer("1.0");
//        tentamen.setHash("HASH");

        tentamen.setAntwoorden(vraagDao.getAntwoorden());

        // Add all exams to the list
        tentamens.add(tentamen);

        return tentamens;
    }

    @Override
    public void addTentamen(String tentamenId, String versieNummer) {

    }
}
