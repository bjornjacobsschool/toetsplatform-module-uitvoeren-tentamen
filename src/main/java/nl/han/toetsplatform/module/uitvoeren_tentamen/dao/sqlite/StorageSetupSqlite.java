package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.StorageSetupDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class StorageSetupSqlite implements StorageSetupDao {

    private static String DDL_SCRIPT_FILENAME = "sql/ddl_script_uitvoeren_tentamen.sql";

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
        BufferedReader in = new BufferedReader(new FileReader(getClass().getResource("/" + DDL_SCRIPT_FILENAME).getFile()));
        String str;
        StringBuffer sb = new StringBuffer();

        while ((str = in.readLine()) != null) {
            sb.append(str + "\n");
        }

        in.close();

        return sb.toString();
    }

}
