package nl.han.toetsplatform.module.uitvoeren_tentamen.dao;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.Toets;

import java.util.List;

public interface ToetsDao {

    List<Toets> getLocalToetsen();

}
