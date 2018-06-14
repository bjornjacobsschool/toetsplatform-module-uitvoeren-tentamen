package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.toets;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;

import java.sql.SQLException;
import java.util.List;

public interface ToetsDao {

    List<Tentamen> getLocalTentamens() throws SQLException;

    Tentamen fetchTentamenFromDatabase(String tentamenId);

    String saveTentamen(Tentamen tentamen) throws SQLException;

    void saveAntwoord(Vraag vraag, String tentamenId);
}