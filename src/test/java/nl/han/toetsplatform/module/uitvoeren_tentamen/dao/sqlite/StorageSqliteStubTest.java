package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub.StorageSqliteStub;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class StorageSqliteStubTest {

    private StorageSqliteStub storageSqliteStub;

    @Before
    public void setUp() {
        this.storageSqliteStub = new StorageSqliteStub();
    }

    @Test
    public void executeQuery() throws SQLException {
        assertNull(this.storageSqliteStub.executeQuery(""));
    }

    @Test
    public void executeUpdate() throws SQLException {
        assertFalse(this.storageSqliteStub.executeUpdate(""));
    }

    @Test
    public void getConnection() {
        assertNull(this.storageSqliteStub.getConnection());
    }
}