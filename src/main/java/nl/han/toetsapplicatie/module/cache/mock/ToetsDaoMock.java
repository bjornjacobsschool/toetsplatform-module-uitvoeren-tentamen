package nl.han.toetsapplicatie.module.cache.mock;

import nl.han.toetsapplicatie.module.cache.ToetsDao;
import nl.han.toetsapplicatie.module.cache.VragenDao;
import nl.han.toetsapplicatie.module.model.Toets;

import java.util.ArrayList;
import java.util.List;

public class ToetsDaoMock implements ToetsDao {

    private VragenDao vragenDao = new nl.han.toetsapplicatie.module.cache.mock.VragenDaoMock();

    @Override
    public List<Toets> getLocalToetsen() {
        List<Toets> toetsen = new ArrayList<>();
        Toets toets1 = new Toets();
        toets1.setVragen(vragenDao.getVragen());
        toets1.setNaam("Toets 1");
        toets1.setId(1);

        Toets toets2 = new Toets();
        toets2.setNaam("Toets 2");
        toets2.setVragen(vragenDao.getVragen());
        toets2.setId(2);

        toetsen.add(toets1);
        toetsen.add(toets2);

        return toetsen;
    }
}
