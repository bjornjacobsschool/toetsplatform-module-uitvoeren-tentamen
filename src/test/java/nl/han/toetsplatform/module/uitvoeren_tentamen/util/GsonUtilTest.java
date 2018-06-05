package nl.han.toetsplatform.module.uitvoeren_tentamen.util;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Versie;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.*;

public class GsonUtilTest {

    private Tentamen tentamen;
    private String tentamenId;
    private int studentNr;
    private String naam;
    private String hash;
    private List<Antwoord> antwoorden;
    private String beschrijving;
    private Date startDatum;
    // private String strStartDatum;
    private Versie versie;
    private Date date;
    private Antwoord aw1;
    private Antwoord aw2;
    private GsonUtil gsu;
    private String expected;
    private String dir;

    @Before
    public void setupGsonUtilTest() {
        tentamenId = "1";
        studentNr = 496798;
        naam = "Kars";
        hash = "hash";
        antwoorden = new ArrayList<Antwoord>();
        aw1 = new Antwoord("1", "1", "Het antwoord is 5");
        aw2 = new Antwoord("2", "1", "Geen idee");
        antwoorden.add(aw1);
        antwoorden.add(aw2);
        beschrijving = "Dit is een tentamen";
        date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        versie = new Versie();
        versie.setDatum(date);
        versie.setNummer("1");
        versie.setOmschrijving("Versieomschrijving");
        tentamen = new Tentamen();
        tentamen.setTentamenId(tentamenId);
        tentamen.setStudentNr(studentNr);
        tentamen.setNaam(naam);
        tentamen.setHash(hash);
        tentamen.setAntwoorden(antwoorden);
        tentamen.setBeschrijving(beschrijving);
        tentamen.setStartDatum(date);
        tentamen.setVersie(versie);
        gsu = new GsonUtil();
        dir = "C:\\Users\\Kars\\Desktop\\File.json";
        expected = "{\"tentamenId\":\"1\",\"studentNr\":496798,\"naam\":\"Kars\",\"hash\":\"hash\",\"antwoorden\"" +
                ":[{\"vraagId\":\"1\",\"tentamenId\":\"1\",\"gegevenAntwoord\":\"Het antwoord is 5\"},{\"vraagId\":\"2\"" +
                ",\"tentamenId\":\"1\",\"gegevenAntwoord\":\"Geen idee\"}],\"beschrijving\":\"Dit is een tentamen\",\"" +
                "startDatum\":\"Feb 11, 2014 12:00:00 AM\",\"strStartDatum\":\"11-02-2014 00:00\",\"versie\":{\"datum\"" +
                ":\"Feb 11, 2014 12:00:00 AM\",\"nummer\":\"1\",\"omschrijving\":\"Versieomschrijving\"}}";
    }

    @Test
    public void testGsonReadWrite() {
        // gsu.write(tentamen, dir);
        Tentamen result = gsu.loadTentamen(dir);
        Assert.assertEquals(result.getTentamenId(), tentamenId);
        Assert.assertEquals(result.getStudentNr(), studentNr);
        Assert.assertEquals(result.getNaam(), naam);
        Assert.assertEquals(result.getHash(), hash);
        Assert.assertEquals(result.getBeschrijving(), beschrijving);
        Assert.assertEquals(result.getStartDatum(), date);
        Assert.assertEquals(result.getVersie().getDatum(), versie.getDatum());
        Assert.assertEquals(result.getVersie().getNummer(), versie.getNummer());
    }
}

