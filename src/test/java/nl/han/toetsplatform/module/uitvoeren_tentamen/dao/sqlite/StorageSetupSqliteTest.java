package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StorageSetupSqliteTest {

    private StorageSetupSqlite storageSetupSqlite;

    @Before
    public void setUp() {
        this.storageSetupSqlite = new StorageSetupSqlite();
    }

    @Test
    public void getSQLFromFile() {
        assertEquals(this.storageSetupSqlite.getSQLFromFile(), "DROP TABLE IF EXISTS MODULE_UITVOEREN_ANTWOORD;\n" +
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
                ");");
    }
}