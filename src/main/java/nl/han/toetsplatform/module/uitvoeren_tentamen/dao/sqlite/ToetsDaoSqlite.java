package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.toets.ToetsDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToetsDaoSqlite implements ToetsDao {

    /**
     * Injected from frontend GuiceModule.java
     */
    @Inject
    public StorageDao storageDao;

    @Override
    public List<Tentamen> getLocalTentamens() throws SQLException {
        List<Tentamen> tentamens = new ArrayList<>();

        ResultSet resultSet = storageDao.executeQuery("SELECT * FROM MODULE_UITVOEREN_TENTAMEN");
        while (resultSet.next()) {
            if (!resultSet.getBoolean("ingeleverd")) {
                Tentamen tentamen = new Tentamen();
                tentamen.setId(resultSet.getString("tentamenid"));
//            tentamen.setStudentNr(resultSet.getInt("studentnr"));
//            tentamen.setVersieNummer(resultSet.getString("versieNummer"));
                tentamen.setNaam(resultSet.getString("naam"));
//            tentamen.setHash(resultSet.getString("hash"));
                tentamens.add(tentamen);
            }
        }

        return tentamens;
    }
}
