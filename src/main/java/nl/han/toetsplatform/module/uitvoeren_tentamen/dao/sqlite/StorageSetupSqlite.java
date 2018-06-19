package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;

import java.sql.SQLException;

public class StorageSetupSqlite implements StorageSetupDao {

    private static String TABLE_MODULE_UITVOEREN_ANTWOORD = "MODULE_UITVOEREN_ANTWOORD";
    private static String TABLE_MODULE_UITVOEREN_TENTAMEN = "MODULE_UITVOEREN_TENTAMEN";

    @Inject
    public StorageDao storageDao;

    @Override
    public void setup() throws SQLException {
        storageDao.setup(getSQLFromFile(), new String[]{
                TABLE_MODULE_UITVOEREN_ANTWOORD,
                TABLE_MODULE_UITVOEREN_TENTAMEN
        });
    }

    public String getSQLFromFile() {
        return "CREATE TABLE IF NOT EXISTS MODULE_UITVOEREN_TENTAMEN (\n" +
                "  tentamenid      char(36)      NOT NULL,\n" +
                "  versieNummer    varchar(10)   NOT NULL,\n" +
                "  hash            char(24)      NULL,\n" +
                "  CONSTRAINT pk_tentamen PRIMARY KEY (tentamenid)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS MODULE_UITVOEREN_ANTWOORD (\n" +
                "  vraagid         char(36)      NOT NULL,\n" +
                "  tentamenid      char(36)      NOT NULL,\n" +
                "  gegevenAntwoord varchar(1024) NULL,\n" +
                "  CONSTRAINT pk_vraag PRIMARY KEY (vraagid, tentamenid),\n" +
                "  CONSTRAINT fk_vraag_van_tentamen FOREIGN KEY (tentamenid) REFERENCES TENTAMEN (tentamenid)\n" +
                ");";
    }

}
