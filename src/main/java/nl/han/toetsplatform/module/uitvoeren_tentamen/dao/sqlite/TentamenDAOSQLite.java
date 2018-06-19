package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.tentamen.TentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TentamenDAOSQLite implements TentamenDAO {

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
            Tentamen tentamen = new Tentamen();
            tentamen.setId(resultSet.getString("tentamenid"));
//            tentamen.setStudentNr(resultSet.getInt("studentnr"));
//            tentamen.setVersieNummer(resultSet.getString("versieNummer"));
            tentamen.setNaam(resultSet.getString("naam"));
//            tentamen.setHash(resultSet.getString("hash"));

            tentamens.add(tentamen);
        }

        return tentamens;
    }

    @Override
    public void addTentamen(String tentamenId, String versieNummer) throws SQLException {
//        String createAntwoordQuery = "INSERT INTO MODULE_UITVOEREN_TENTAMEN(tentamenid, versieNummer) VALUES (" +
//                "'" + tentamenId + "', '" + versieNummer + "') " +
//                "WHERE NOT EXISTS(SELECT 1 FROM MODULE_UITVOEREN_TENTAMEN WHERE tentamenid = '" + tentamenId + "');";

        String createAntwoordQuery = "INSERT OR IGNORE INTO MODULE_UITVOEREN_TENTAMEN(tentamenid, versieNummer) VALUES (" +
                "'" + tentamenId + "', '" + versieNummer + "');";

        System.out.println(createAntwoordQuery);
        storageDao.executeUpdate(createAntwoordQuery);
    }
}
