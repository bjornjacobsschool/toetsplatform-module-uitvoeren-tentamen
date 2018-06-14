package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

//import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

public class TentamenDaoSqliteTest {

    private int studentnr = 0;
    private String tentamenid = "testTentamen";
    private String versieNummer = "1.0";
    private String datum = "20180101";
    private String omschrijving = "dit is een tentamen";
    private String naam = "TestTentamen";
    private String hash = "123456789012345678901234";
    private String vraagid = "0";
    private String vraagNaam = "Vraagnaam";
    private String vraagOmschrijving = "Dit is een vraag";
    private String thema = "thema";
    private int punten = 5;
    private String vraagtype = "Graph";
    private String vraagdata = "Wat is..?";
    private String gegevenAntwoord = "Het antwoord is 9";

    @Mock
    private StorageDao storageDao;

    @Mock
    private TentamenDaoSqlite tentamenDaoSqlite;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        // this.tentamenDaoSqlite = new TentamenDaoSqlite();
        String insertQuery = "INSERT INTO MODULE_UITVOEREN_STUDENT (studentnr, klas) VALUES (" + studentnr + ", 'ASD-B') " +
                "INSERT INTO MODULE_UITVOEREN_TENTAMEN (tentamenid, studentnr, versieNummer, datum, omschrijving, naam, hash) VALUES ('" +
                tentamenid + "', " + studentnr + ", '" + versieNummer + "', '" + datum + "', '" + omschrijving + "', '" + naam + "', '" + hash + "') " +
                "INSERT INTO MODULE_UITVOEREN_VRAAG (vraagid, tentamenid, naam, omschrijving, thema, punten, vraagtype, vraagdata) VALUES ('" +
                vraagid + "', '" + tentamenid + "', '" + vraagNaam + "', '" + vraagOmschrijving + "', '" + thema + "', " + punten + ", '" +
                vraagtype + "', '" + vraagdata + "') " +
                "INSERT INTO MODULE_UITVOEREN_ANTWOORD (vraagid, tentamenid, gegevenAntwoord) VALUES ('" +
                vraagid + "', '" + tentamenid + "', '" + gegevenAntwoord + "')";
        storageDao.executeUpdate(insertQuery);
    }

    //@Test
    public void fetchTentamenFromDatabase() throws SQLException {
        Tentamen t = tentamenDaoSqlite.fetchTentamenFromDatabase(tentamenid);
        Assert.assertEquals(t.getStudentNr(), studentnr);
        Assert.assertEquals(t.getVersie().getNummer(), versieNummer);
        Assert.assertEquals(t.getVersie().getDatum(), datum);
        Assert.assertEquals(t.getVersie().getOmschrijving(), omschrijving);
        Assert.assertEquals(t.getNaam(), naam);
        Assert.assertEquals(t.getHash(), hash);
    }
}
