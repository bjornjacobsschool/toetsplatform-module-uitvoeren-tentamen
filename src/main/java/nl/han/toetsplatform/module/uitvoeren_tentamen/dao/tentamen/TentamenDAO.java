package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.sql.SQLException;
import java.util.List;

public interface TentamenDAO {

    List<Tentamen> getLocalTentamens() throws SQLException;

    void addTentamen(String tentamenId, String versieNummer) throws SQLException;

}