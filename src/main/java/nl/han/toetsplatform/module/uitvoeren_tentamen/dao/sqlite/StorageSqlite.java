package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import nl.han.toetsplatform.module.shared.storage.StorageDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageSqlite implements StorageDao {

    @Override
    public void setup(String s, String[] strings) throws SQLException {

    }

    @Override
    public ResultSet executeQuery(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean executeUpdate(String s) throws SQLException {
        return false;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

}
