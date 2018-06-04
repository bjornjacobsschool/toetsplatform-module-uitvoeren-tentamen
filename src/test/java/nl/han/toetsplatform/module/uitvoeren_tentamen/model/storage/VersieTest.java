package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class VersieTest {

    private Versie versie;

    @Before
    public void setUp() {
        versie = new Versie();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getDatum() {
        Date date = new Date();
        versie.setDatum(date);
        assertEquals(versie.getDatum(), date);
    }

    @Test
    public void setDatum() {
        Date date = new Date();
        versie.setDatum(date);
        assertEquals(versie.getDatum(), date);
    }

    @Test
    public void getNummer() {
        versie.setNummer("125367876543");
        assertEquals(versie.getNummer(), "125367876543");
    }

    @Test
    public void setNummer() {
        versie.setNummer("56789876");
        assertEquals(versie.getNummer(), "56789876");
    }

    @Test
    public void getOmschrijving() {
        versie.setOmschrijving("Changed something");
        assertEquals(versie.getOmschrijving(), "Changed something");
    }

    @Test
    public void setOmschrijving() {
        versie.setOmschrijving("Changed nothing");
        assertEquals(versie.getOmschrijving(), "Changed nothing");
    }
}