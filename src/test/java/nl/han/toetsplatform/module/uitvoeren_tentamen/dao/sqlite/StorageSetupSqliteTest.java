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
        assertEquals(this.storageSetupSqlite.getSQLFromFile(), "CREATE TABLE IF NOT EXISTS MODULE_UITVOEREN_TENTAMEN (\n" +
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
                ");");
    }
}