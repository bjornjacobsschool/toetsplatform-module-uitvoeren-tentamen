package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.toets.ToetsDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;

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
        tentamen.setId("ID-VAN-TENTAMEN");
        tentamen.setNaam("Toets APP 1 (Grafen en Paden)");


        // Add all exams to the list
        tentamens.add(tentamen);

        return tentamens;
    }

    @Override
    public Tentamen fetchTentamenFromDatabase(String tentamenId) {
        return null;
    }

    @Override
    public String saveTentamen(Tentamen tentamen) throws SQLException {
        return "";
    }

    @Override
    public void saveAntwoord(Vraag vraag, String tentamenId) {

    }
}
