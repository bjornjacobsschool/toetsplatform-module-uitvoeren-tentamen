package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Versie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;

import java.util.*;

public class JsonUtilTest {

    private String vraagId;
    private String tentamenId;
    private String gegevenAntwoord;
    private Antwoord antwoord;
    private String correct;
    private Antwoord antwoord1;
    private Antwoord antwoord2;
    private List<Antwoord> al;
    private Versie versie;
    private Tentamen tentamen;

    @Before
    public void setupJsonConversionTest() {
        vraagId = "1";
        tentamenId = "1";
        gegevenAntwoord = "Het antwoord is 5";
        antwoord = new Antwoord(vraagId,tentamenId,gegevenAntwoord);
        correct = "{\"vraagId\":\"1\",\"tentamenId\":\"1\",\"gegevenAntwoord\":\"Het antwoord is 5\"}";
    }

    @Before
    public void setupJsonWriteTest() {
        antwoord1 = new Antwoord("1","1","Het antwoord is 5");
        antwoord2 = new Antwoord("2","1","geen idee");
        al = Arrays.asList(antwoord1, antwoord2);
        tentamen = new Tentamen();
        tentamen.setTentamenId("1");
        tentamen.setStudentNr(496798);
        tentamen.setNaam("Kars");
        tentamen.setHash("hash");
        tentamen.setAntwoorden(al);
        Versie versie = new Versie();
        Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        versie.setDatum(date);
        versie.setNummer("1");
        versie.setOmschrijving("Versieomschrijving");
        tentamen.setVersie(versie);
    }

    @Test
    public void testConvertToJson() {
        String result = JsonUtil.convertJavaToJson(antwoord);
        Assert.assertEquals(result,correct);
    }

    @Test
    public void testConvertToJava() {
        Antwoord result = JsonUtil.convertJsonToJava(correct,Antwoord.class);
        Assert.assertEquals(result.getVraagId(),vraagId);
        Assert.assertEquals(result.getTentamenId(),tentamenId);
        Assert.assertEquals(result.getGegevenAntwoord(),gegevenAntwoord);
    }

    @Test
    public void testJsonReadWrite() {
        JsonUtil.writeTentamen(tentamen);
        Tentamen test = JsonUtil.readTentamen("496798.JSON");
        Assert.assertEquals(test.getTentamenId(), tentamenId);
        Assert.assertEquals(test.getStudentNr(), 496798);
        Assert.assertEquals(test.getNaam(), "Kars");
        Assert.assertEquals(test.getHash(), "hash");
    }
}
