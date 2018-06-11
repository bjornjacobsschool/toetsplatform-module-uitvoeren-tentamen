package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Versie;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

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

//    @Before
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
        dir = System.getProperty("java.io.tmpdir") + "File.json";
        expected = "{\"tentamenId\":\"1\",\"studentNr\":496798,\"naam\":\"Kars\",\"hash\":\"hash\",\"antwoorden\"" +
                ":[{\"vraagId\":\"1\",\"tentamenId\":\"1\",\"gegevenAntwoord\":\"Het antwoord is 5\"},{\"vraagId\":\"2\"" +
                ",\"tentamenId\":\"1\",\"gegevenAntwoord\":\"Geen idee\"}],\"beschrijving\":\"Dit is een tentamen\",\"" +
                "startDatum\":\"Feb 11, 2014 12:00:00 AM\",\"strStartDatum\":\"11-02-2014 00:00\",\"versie\":{\"datum\"" +
                ":\"Feb 11, 2014 12:00:00 AM\",\"nummer\":\"1\",\"omschrijving\":\"Versieomschrijving\"}}";
    }

<<<<<<< HEAD
    @Test
    public void testGsonWrite() throws IOException {
=======
//    @Test
    public void testGsonWrite() {
>>>>>>> 1b49317e5372af2459b6c9b6c96df5dc7c5117c1
        gsu.writeTentamen(tentamen, dir);
        Tentamen result = gsu.loadTentamen(dir);
        assertEquals(result.getTentamenId(), tentamenId);
        assertEquals(result.getStudentNr(), studentNr);
        assertEquals(result.getNaam(), naam);
        assertEquals(result.getHash(), hash);
        assertEquals(result.getBeschrijving(), beschrijving);
        assertEquals(result.getStartDatum(), date);
        assertEquals(result.getVersie().getDatum(), versie.getDatum());
        assertEquals(result.getVersie().getNummer(), versie.getNummer());
    }

//    @Test
    public void testGsonLoad() {
        String resourceDir = "src/test/resources/Test.json";
        Tentamen result = gsu.loadTentamen(resourceDir);
        assertEquals(result.getTentamenId(), tentamenId);
        assertEquals(result.getStudentNr(), studentNr);
        assertEquals(result.getNaam(), naam);
        assertEquals(result.getHash(), hash);
        assertEquals(result.getBeschrijving(), beschrijving);
        assertEquals(result.getStartDatum(), date);
        assertEquals(result.getVersie().getDatum(), versie.getDatum());
        assertEquals(result.getVersie().getNummer(), versie.getNummer());
    }
}

