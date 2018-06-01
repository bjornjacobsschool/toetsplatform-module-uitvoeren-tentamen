package nl.han.toetsplatform.module.uitvoeren_tentamen.dao;

import java.io.IOException;
import java.sql.SQLException;

public interface StorageSetupDao {

    void setup() throws SQLException, ClassNotFoundException, IOException;

}