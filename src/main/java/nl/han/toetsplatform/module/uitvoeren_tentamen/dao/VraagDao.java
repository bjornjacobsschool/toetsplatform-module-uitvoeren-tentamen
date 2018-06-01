package nl.han.toetsplatform.module.uitvoeren_tentamen.dao;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;

import java.sql.SQLException;
import java.util.List;

public interface VraagDao {

    List<Antwoord> getAntwoorden() throws SQLException;

    Antwoord getAntwoord(String vraagId) throws SQLException;

}