package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.ToetsDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.Toets;

import java.util.ArrayList;
import java.util.List;

public class ToetsDaoStub implements ToetsDao {

    @Inject
    private VraagDao vraagDao;

    @Override
    public List<Toets> getLocalToetsen() {
        List<Toets> toetsen = new ArrayList<>();

        // Toets 1
        Toets toets1 = new Toets();
        toets1.setId(1);
        toets1.setNaam("Toets APP 1 (Grafen en Paden)");
        toets1.setVragen(vraagDao.getVragen());

        // Add all exams to the list
        toetsen.add(toets1);

        return toetsen;
    }
}
