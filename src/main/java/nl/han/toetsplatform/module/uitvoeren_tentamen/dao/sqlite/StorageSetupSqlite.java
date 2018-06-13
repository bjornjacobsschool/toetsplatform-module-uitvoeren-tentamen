package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;

import java.sql.SQLException;

public class StorageSetupSqlite implements StorageSetupDao {

    private static String TABLE_MODULE_UITVOEREN_ANTWOORD = "MODULE_UITVOEREN_ANTWOORD";
    private static String TABLE_MODULE_UITVOEREN_STUDENT = "MODULE_UITVOEREN_STUDENT";
    private static String TABLE_MODULE_UITVOEREN_TENTAMEN = "MODULE_UITVOEREN_TENTAMEN";

    @Inject
    public StorageDao storageDao;

    @Override
    public void setup() throws SQLException {
        storageDao.setup(getSQLFromFile(), new String[]{
                TABLE_MODULE_UITVOEREN_ANTWOORD,
                TABLE_MODULE_UITVOEREN_STUDENT,
                TABLE_MODULE_UITVOEREN_TENTAMEN
        });
    }

    public String getSQLFromFile() {
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
                "  versieNummer    varchar(10)   NOT NULL,\n" +     // versie
                "  datum           varchar(10)   NOT NULL,\n" +     // versie
                "  omschrijving    varchar(10)   NOT NULL,\n" +     // versie
                "  naam            varchar(50)   NOT NULL,\n" +
                "  hash            char(24)      NULL,\n" +
                "  CONSTRAINT pk_tentamen PRIMARY KEY (tentamenid),\n" +
                "  CONSTRAINT fk_Student_maakt_tentamen FOREIGN KEY (studentnr) REFERENCES MODULE_UITVOEREN_STUDENT (studentnr)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE MODULE_UITVOEREN_VRAAG (\n" +
                "  vraagid         varchar(36)   NOT NULL,\n" +
                "  tentamenid      varchar(36)   NOT NULL,\n" +
                "  naam            varchar(255)  NOT NULL,\n" +
                "  omschrijving    varchar(1024) NOT NULL,\n" +
                "  thema           varchar(255)  NOT NULL,\n" +
                "  punten          int           NOT NULL,\n" +
                "  vraagtype       varchar(255)  NOT NULL,\n" +
                "  vraagdata       varchar(1024) NOT NULL,\n" +
                "  CONSTRAINT pk_vraag PRIMARY KEY (vraagid, tentamenid),\n" +
                "  CONSTRAINT fk_vraag_van_tentamen FOREIGN KEY (tentamenid) REFERENCES MODULE_UITVOEREN_TENTAMEN (tentamenid)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE MODULE_UITVOEREN_ANTWOORD (\n" +
                "  vraagid         varchar(36)   NOT NULL,\n" +
                "  tentamenid      varchar(36)   NOT NULL,\n" +
                "  gegevenAntwoord varchar(1024) NULL,\n" +
                "  CONSTRAINT pk_antwoord PRIMARY KEY (vraagid, tentamenid),\n" +
                "  CONSTRAINT fk_antwoord_van_vraag FOREIGN KEY (vraagid, tentamenid) REFERENCES MODULE_UITVOEREN_VRAAG (vraagid, tentamenid)\n" +
                ");";
    }

}
