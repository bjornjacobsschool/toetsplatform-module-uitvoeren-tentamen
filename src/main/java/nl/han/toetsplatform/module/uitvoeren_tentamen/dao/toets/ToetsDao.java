package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.toets;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.sql.SQLException;
import java.util.List;

public interface ToetsDao {

    List<Tentamen> getLocalTentamens() throws SQLException;

}