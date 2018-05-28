package nl.han.toetsapplicatie.module.cache.mock;

import nl.han.toetsapplicatie.module.cache.VragenDao;
import nl.han.toetsapplicatie.module.model.Vraag;

import java.util.ArrayList;
import java.util.List;

public class VragenDaoMock implements VragenDao {

    private static List<Vraag> vragen;

    public VragenDaoMock() {
        if (vragen != null)
            return;

        vragen = new ArrayList<>();
        Vraag vraag1 = new Vraag();
        vraag1.setPlugin("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag1.setId(1);
        vraag1.setName("Vraag 1");
        vraag1.setData("{\"nodes\":[{\"name\":\"B\",\"connectedNodes\":[{\"nodeInfo\":\"D\",\"distance\":2}]},{\"name\":\"A\",\"connectedNodes\":[{\"nodeInfo\":\"B\",\"distance\":33},{\"nodeInfo\":\"B\",\"distance\":33}]},{\"name\":\"D\",\"connectedNodes\":[]}],\"vraagText\":\"Hallo\"}\n");
        vragen.add(vraag1);


        Vraag vraag2 = new Vraag();
        vraag2.setId(2);
        vraag2.setName("Vraag 2");
        vraag2.setPlugin("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag2.setData("{\"nodes\":[{\"name\":\"B\",\"connectedNodes\":[{\"nodeInfo\":\"D\",\"distance\":2}]},{\"name\":\"A\",\"connectedNodes\":[{\"nodeInfo\":\"B\",\"distance\":33},{\"nodeInfo\":\"B\",\"distance\":33}]},{\"name\":\"D\",\"connectedNodes\":[]}],\"vraagText\":\"Hallo\"}\n");
        vragen.add(vraag2);
    }

    @Override
    public List<Vraag> getVragen() {
        return vragen;
    }

    @Override
    public Vraag getVraag(int id) {
        for (Vraag v : vragen)
            if (v.getId() == id)
                return v;

        return null;
    }
}
