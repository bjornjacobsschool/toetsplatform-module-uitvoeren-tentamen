package nl.han.toetsapplicatie.module.cache;

import nl.han.toetsapplicatie.module.model.Toets;

import java.util.List;

public interface ToetsDao {

    List<Toets> getLocalToetsen();

}
