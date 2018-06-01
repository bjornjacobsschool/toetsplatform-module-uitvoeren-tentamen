package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.ToetsDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToetsDaoStub implements ToetsDao {

    @Inject
    private VraagDao vraagDao;

    @Override
    public List<Tentamen> getLocalTentamens() throws SQLException {
        List<Tentamen> tentamens = new ArrayList<>();

        // Dummy tentamen
        Tentamen tentamen = new Tentamen();
        tentamen.setTentamenId("ID-VAN-TENTAMEN");
        tentamen.setStudentNr(123456);
        tentamen.setNaam("Toets APP 1 (Grafen en Paden)");
        tentamen.setVersieNummer("1.0");
        tentamen.setHash("HASH");

        tentamen.setAntwoorden(vraagDao.getAntwoorden());

        // Add all exams to the list
        tentamens.add(tentamen);

        return tentamens;
    }
}
