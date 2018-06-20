package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VraagDaoSqlite implements VraagDao {

    /**
     * Injected from frontend GuiceModule.java
     */
    @Inject
    public StorageDao storageDao;

    public List<Antwoord> getAntwoordenVoorTentamen(String tentamenId) throws SQLException {
        List<Antwoord> antwoorden = new ArrayList<>();

        String query = "SELECT * FROM MODULE_UITVOEREN_ANTWOORD WHERE tentamenid = '" + tentamenId + "';";

        ResultSet resultSet = storageDao.executeQuery(query);
        while (resultSet.next()) {
            Antwoord antwoord = new Antwoord();
            antwoord.setTentamenId(resultSet.getString("tentamenid"));
            antwoord.setVraagId(resultSet.getString("vraagid"));
            antwoord.setGegevenAntwoord(resultSet.getString("gegevenAntwoord"));
            antwoorden.add(antwoord);
        }

        return antwoorden;
    }

    @Override
    public List<Antwoord> getAntwoorden() throws SQLException {
        List<Antwoord> antwoorden = new ArrayList<>();

        ResultSet resultSet = storageDao.executeQuery("SELECT * FROM MODULE_UITVOEREN_ANTWOORD");
        while (resultSet.next()) {
            Antwoord antwoord = new Antwoord();
            antwoord.setTentamenId(resultSet.getString("tentamenid"));
            antwoord.setVraagId(resultSet.getString("vraagid"));
            antwoord.setGegevenAntwoord(resultSet.getString("gegevenAntwoord"));
            antwoorden.add(antwoord);
        }

        return antwoorden;
    }

    @Override
    public Antwoord getAntwoord(String vraagId, String tentamenId) throws SQLException {
        ResultSet resultSet = storageDao.executeQuery(getSqlString(false, vraagId, tentamenId));

        return new Antwoord(
                resultSet.getString("vraagid"),
                resultSet.getString("tentamenid"),
                resultSet.getString("gegevenAntwoord")
        );
    }

    @Override
    public void setAntwoord(String vraagId, String tentamenId, String gegevenAntwoord) throws SQLException {

        String checkAntwoord = getSqlString(true, vraagId, tentamenId);

        ResultSet res = storageDao.executeQuery(checkAntwoord);
        String query;

        int count = res.getInt("rowcount");

        if (count == 0) {
            query = "INSERT INTO MODULE_UITVOEREN_ANTWOORD (vraagid, tentamenid, gegevenAntwoord) VALUES (" +
                    "'" + vraagId + "', '" + tentamenId + "', '" + gegevenAntwoord + "');";
        } else {
            query = "UPDATE MODULE_UITVOEREN_ANTWOORD SET gegevenAntwoord = '" + gegevenAntwoord + "' WHERE " +
                    "(vraagid = '" + vraagId + "' AND tentamenid = '" + tentamenId + "');";
        }

        storageDao.executeUpdate(query);
    }

    private String getSqlString(boolean getCount, String vraagId, String tentamenId) {
        StringBuilder str = new StringBuilder();
        if (getCount)
            str.append("SELECT COUNT(*) AS rowcount FROM ");
        else
            str.append("SELECT * FROM ");

        str.append("MODULE_UITVOEREN_ANTWOORD WHERE (vraagid = '").append(vraagId).append("' AND tentamenid = '").append(tentamenId).append("');");
        return str.toString();
    }
}
