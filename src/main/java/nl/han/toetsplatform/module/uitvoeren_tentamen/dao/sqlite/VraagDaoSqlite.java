package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;

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

    @Override
    public List<Antwoord> getAntwoorden() throws SQLException {
        List<Antwoord> antwoorden = new ArrayList<>();

        ResultSet resultSet = storageDao.executeQuery("SELECT * FROM MODULE_UITVOEREN_ANTWOORD");
        while (resultSet.next()) {
            Antwoord antwoord = new Antwoord();
            antwoord.setTentamenId(resultSet.getString("tentamenid"));
            antwoord.setTentamenId(resultSet.getString("vraagid"));
            antwoord.setGegevenAntwoord(resultSet.getString("gegevenAntwoord"));
            antwoorden.add(antwoord);
        }

        return antwoorden;
    }

    @Override
    public Antwoord getAntwoord(String vraagId) throws SQLException {
        ResultSet resultSet = storageDao.executeQuery("SELECT * FROM MODULE_UITVOEREN_ANTWOORD WHERE vraagid = " + vraagId + ";");
        return new Antwoord(
                resultSet.getString("vraagid"),
                resultSet.getString("tentamenid"),
                resultSet.getString("gegevenAntwoord")
        );
    }
}
