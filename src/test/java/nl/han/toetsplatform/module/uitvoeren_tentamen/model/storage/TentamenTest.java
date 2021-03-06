package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TentamenTest {

    private Tentamen tentamen;

    @Before
    public void setUp() {
        tentamen = new Tentamen();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void getTentamenId() {
        tentamen.setTentamenId("123456");
        assertEquals(tentamen.getTentamenId(), "123456");
    }

    @Test
    public void setTentamenId() {
        tentamen.setTentamenId("654321");
        assertEquals(tentamen.getTentamenId(), "654321");
    }

    @Test
    public void getStudentNr() {
        tentamen.setStudentNr(555928);
        assertEquals(tentamen.getStudentNr(), 555928);
    }

    @Test
    public void setStudentNr() {
        tentamen.setStudentNr(829555);
        assertEquals(tentamen.getStudentNr(), 829555);
    }

    @Test
    public void getNaam() {
        tentamen.setNaam("John");
        assertEquals(tentamen.getNaam(), "John");
    }

    @Test
    public void setNaam() {
        tentamen.setNaam("Apple");
        assertEquals(tentamen.getNaam(), "Apple");
    }

    @Test
    public void getHash() {
        tentamen.setHash("23567uhg6747i4u3hwg543");
        assertEquals(tentamen.getHash(), "23567uhg6747i4u3hwg543");
    }

    @Test
    public void setHash() {
        tentamen.setHash("56785ijeyhrtwg3h47j6ue");
        assertEquals(tentamen.getHash(), "56785ijeyhrtwg3h47j6ue");
    }

    @Test
    public void getAntwoorden() {
        List<Antwoord> antwoordList = new ArrayList<>();

        Antwoord a1 = new Antwoord();
        a1.setTentamenId("1");
        a1.setVraagId("1");
        a1.setGegevenAntwoord("1");

        Antwoord a2 = new Antwoord();
        a2.setTentamenId("2");
        a2.setVraagId("2");
        a2.setGegevenAntwoord("2");

        antwoordList.add(a1);
        antwoordList.add(a2);

        tentamen.setAntwoorden(antwoordList);

        assertEquals(tentamen.getAntwoorden().size(), 2);
        assertEquals(tentamen.getAntwoorden().get(0), a1);
        assertEquals(tentamen.getAntwoorden().get(1), a2);
    }

    @Test
    public void setAntwoorden() {
        List<Antwoord> antwoordList = new ArrayList<>();

        Antwoord a1 = new Antwoord();
        a1.setTentamenId("11");
        a1.setVraagId("11");
        a1.setGegevenAntwoord("11");

        Antwoord a2 = new Antwoord();
        a2.setTentamenId("22");
        a2.setVraagId("22");
        a2.setGegevenAntwoord("22");

        antwoordList.add(a1);
        antwoordList.add(a2);

        tentamen.setAntwoorden(antwoordList);

        assertEquals(tentamen.getAntwoorden().size(), 2);
        assertEquals(tentamen.getAntwoorden().get(0), a1);
        assertEquals(tentamen.getAntwoorden().get(1), a2);
    }

    @Test
    public void getBeschrijving() {
        tentamen.setBeschrijving("APP");
        assertEquals(tentamen.getBeschrijving(), "APP");
    }

    @Test
    public void setBeschrijving() {
        tentamen.setBeschrijving("SWA");
        assertEquals(tentamen.getBeschrijving(), "SWA");
    }

    @Test
    public void getStartDatum() {
        Date date = new Date();
        tentamen.setStartDatum(date);
        assertEquals(tentamen.getStartDatum(), date);
    }

    @Test
    public void setStartDatum() {
        Date date = new Date();
        tentamen.setStartDatum(date);
        assertEquals(tentamen.getStartDatum(), date);
    }

    @Test
    public void getVersie() {
        Versie versie = new Versie();
        tentamen.setVersie(versie);
        assertEquals(tentamen.getVersie(), versie);
    }

    @Test
    public void setVersie() {
        Versie versie = new Versie();
        tentamen.setVersie(versie);
        assertEquals(tentamen.getVersie(), versie);
    }

    @Test
    public void getStrStartDatum() {
        Date date = new Date();
        date.setTime(1528112714);

        tentamen.setStartDatum(date);
        assertEquals(tentamen.getStrStartDatum(), "18-01-1970 17:28");
    }
}