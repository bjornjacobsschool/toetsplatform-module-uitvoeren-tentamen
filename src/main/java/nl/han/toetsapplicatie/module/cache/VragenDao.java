package nl.han.toetsapplicatie.module.cache;

import nl.han.toetsapplicatie.module.model.Vraag;

import java.util.List;

public interface VragenDao {

    List<Vraag> getVragen();

    Vraag getVraag(int id);

}
