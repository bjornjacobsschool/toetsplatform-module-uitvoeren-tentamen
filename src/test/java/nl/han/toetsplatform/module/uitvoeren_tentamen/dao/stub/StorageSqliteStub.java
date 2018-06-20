package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub;

import nl.han.toetsplatform.module.shared.storage.StorageDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageSqliteStub implements StorageDao {

    @Override
    public void setup(String ddlScript, String[] tables) throws SQLException {

    }

    @Override
    public ResultSet executeQuery(String sqlStatement) throws SQLException {
        return null;
    }

    @Override
    public boolean executeUpdate(String sqlStatement) throws SQLException {
        return false;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void closeConnection() {

    }

}
