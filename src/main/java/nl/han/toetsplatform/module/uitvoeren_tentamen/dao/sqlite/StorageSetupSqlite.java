package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.StorageSetupDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class StorageSetupSqlite implements StorageSetupDao {

    private static String DDL_SCRIPT_FILENAME = "/sql/ddl_script_uitvoeren_tentamen.sql";

    private static String TABLE_MODULE_UITVOEREN_ANTWOORD = "MODULE_UITVOEREN_ANTWOORD";
    private static String TABLE_MODULE_UITVOEREN_STUDENT = "MODULE_UITVOEREN_STUDENT";
    private static String TABLE_MODULE_UITVOEREN_TENTAMEN = "MODULE_UITVOEREN_TENTAMEN";

    @Inject
    public StorageDao storageDao;

    @Override
    public void setup() throws SQLException, IOException {
        storageDao.setup(getSQLFromFile(), new String[]{
                TABLE_MODULE_UITVOEREN_ANTWOORD,
                TABLE_MODULE_UITVOEREN_STUDENT,
                TABLE_MODULE_UITVOEREN_TENTAMEN
        });
    }

    public String getSQLFromFile() throws IOException {
        /*
        FileReader fr = new FileReader(new File(DDL_SCRIPT_FILENAME));
        BufferedReader in = new BufferedReader(fr);

        String str;
        StringBuffer sb = new StringBuffer();

        while ((str = in.readLine()) != null) {
            sb.append(str);
        }

        in.close();

        return sb.toString();
        */

        return "DROP TABLE IF EXISTS MODULE_UITVOEREN_ANTWOORD;\n" +
                "DROP TABLE IF EXISTS MODULE_UITVOEREN_TENTAMEN;\n" +
                "DROP TABLE IF EXISTS MODULE_UITVOEREN_STUDENT;\n" +
                "\n" +
                "CREATE TABLE MODULE_UITVOEREN_STUDENT (\n" +
                "  studentnr       int           NOT NULL,\n" +
                "  klas            varchar(10)   NOT NULL,\n" +
                "  CONSTRAINT pk_student PRIMARY KEY (studentnr)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE MODULE_UITVOEREN_TENTAMEN (\n" +
                "  tentamenid      char(36)      NOT NULL,\n" +
                "  studentnr       int           NOT NULL,\n" +
                "  versieNummer    varchar(10)   NOT NULL,\n" +
                "  naam            varchar(50)   NOT NULL,\n" +
                "  hash            char(24)      NULL,\n" +
                "  CONSTRAINT pk_tentamen PRIMARY KEY (tentamenid),\n" +
                "  CONSTRAINT fk_Student_maakt_tentamen FOREIGN KEY (studentnr) REFERENCES STUDENT (studentnr)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE MODULE_UITVOEREN_ANTWOORD (\n" +
                "  vraagid         char(36)      NOT NULL,\n" +
                "  tentamenid      char(36)      NOT NULL,\n" +
                "  gegevenAntwoord varchar(1024) NULL,\n" +
                "  CONSTRAINT pk_vraag PRIMARY KEY (vraagid, tentamenid),\n" +
                "  CONSTRAINT fk_vraag_van_tentamen FOREIGN KEY (tentamenid) REFERENCES TENTAMEN (tentamenid)\n" +
                ");";
    }

}
