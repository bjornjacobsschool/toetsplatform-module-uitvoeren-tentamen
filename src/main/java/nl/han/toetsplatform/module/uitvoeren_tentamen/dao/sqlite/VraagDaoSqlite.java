package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VraagDaoSqlite implements VraagDao {
    private static Logger LOGGER = Logger.getLogger(VraagDaoSqlite.class.getName());
    /**
     * Injected from frontend GuiceModule.java
     */
    public StorageDao storageDao;

    @Inject
    public VraagDaoSqlite(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public List<Antwoord> getAntwoorden(String tentamenid) throws SQLException {
        if (storageDao == null) {
            LOGGER.log(Level.SEVERE, "storagedao is null..");
        }

        List<Antwoord> antwoorden = new ArrayList<>();

        ResultSet resultSet = storageDao.executeQuery("SELECT * FROM MODULE_UITVOEREN_ANTWOORD WHERE tentamenid ='" + tentamenid + "';");
        while (resultSet.next()) {
            Antwoord antwoord = new Antwoord();
            antwoord.setTentamenId(resultSet.getString("tentamenid"));
            antwoord.setVraagId(resultSet.getString("vraagid"));
            antwoord.setGegevenAntwoord(resultSet.getString("gegevenAntwoord"));
            antwoorden.add(antwoord);
        }

        resultSet.close();

        return antwoorden;
    }

    @Override
    public Antwoord getAntwoord(String vraagId, String tentamenId) throws SQLException {
        ResultSet resultSet = storageDao.executeQuery("SELECT * FROM MODULE_UITVOEREN_ANTWOORD WHERE (vraagid = '" + vraagId + "' AND tentamenid = '" + tentamenId + "');");


        Antwoord antwoord = new Antwoord(
                resultSet.getString("vraagid"),
                resultSet.getString("tentamenid"),
                resultSet.getString("gegevenAntwoord")
        );

        resultSet.close();

        return antwoord;
    }

    @Override
    public void setAntwoord(String vraagId, String tentamenId, String gegevenAntwoord) throws SQLException {

        String checkAntwoord = "SELECT COUNT(*) AS rowcount FROM MODULE_UITVOEREN_ANTWOORD WHERE (vraagid = '" + vraagId + "' AND tentamenid = '" + tentamenId + "')";

        ResultSet res = storageDao.executeQuery(checkAntwoord);
        String query;

        int count = res.getInt("rowcount");

        res.close();

        if (count == 0) {
            query = "INSERT INTO MODULE_UITVOEREN_ANTWOORD (vraagid, tentamenid, gegevenAntwoord) VALUES (" +
                    "'" + vraagId + "', '" + tentamenId + "', '" + gegevenAntwoord + "');";
        } else {
            query = "UPDATE MODULE_UITVOEREN_ANTWOORD SET gegevenAntwoord = '" + gegevenAntwoord + "' WHERE " +
                    "(vraagid = '" + vraagId + "' AND tentamenid = '" + tentamenId + "');";
        }

        storageDao.executeUpdate(query);
    }
}
