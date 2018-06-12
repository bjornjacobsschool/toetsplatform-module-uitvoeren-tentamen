package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class StorageSqliteTest {

    private StorageSqlite storageSqlite;

    @Before
    public void setUp() {
        this.storageSqlite = new StorageSqlite();
    }

    @Test
    public void executeQuery() throws SQLException {
        assertNull(this.storageSqlite.executeQuery(""));
    }

    @Test
    public void executeUpdate() throws SQLException {
        assertFalse(this.storageSqlite.executeUpdate(""));
    }

    @Test
    public void getConnection() {
        assertNull(this.storageSqlite.getConnection());
    }
}