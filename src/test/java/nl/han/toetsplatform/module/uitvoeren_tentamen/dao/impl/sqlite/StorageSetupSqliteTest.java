package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.impl.sqlite;

import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.StorageSetupSqlite;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class StorageSetupSqliteTest {

    private StorageSetupSqlite storageSetupSqlite;

    private StorageDao storageDao;

    @Before
    public void setup() {
        // Mock Storage Dao
        storageDao = mock(StorageDao.class);

        storageSetupSqlite = new StorageSetupSqlite();
        storageSetupSqlite.storageDao = storageDao;
    }

    @Test
    public void getSQLFromFileFileNotFound() throws IOException {
        storageSetupSqlite.getSQLFromFile();
    }
}