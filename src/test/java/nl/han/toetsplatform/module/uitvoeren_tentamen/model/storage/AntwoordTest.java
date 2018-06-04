package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AntwoordTest {

    private Antwoord antwoord;

    @Before
    public void setUp() {
        antwoord = new Antwoord();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void constructorTest() {
        Antwoord a = new Antwoord("123", "134", "A");
        assertEquals(a.getVraagId(), "123");
        assertEquals(a.getTentamenId(), "134");
        assertEquals(a.getGegevenAntwoord(), "A");
    }

    @Test
    public void getVraagId() {
        antwoord.setVraagId("123456");
        assertEquals(antwoord.getVraagId(), "123456");
    }

    @Test
    public void setVraagId() {
        antwoord.setVraagId("123456");
        assertEquals(antwoord.getVraagId(), "123456");
    }

    @Test
    public void getTentamenId() {
        antwoord.setTentamenId("654321");
        assertEquals(antwoord.getTentamenId(), "654321");
    }

    @Test
    public void setTentamenId() {
        antwoord.setTentamenId("654321");
        assertEquals(antwoord.getTentamenId(), "654321");
    }

    @Test
    public void getGegevenAntwoord() {
        antwoord.setGegevenAntwoord("My answer is A.");
        assertEquals(antwoord.getGegevenAntwoord(), "My answer is A.");
    }

    @Test
    public void setGegevenAntwoord() {
        antwoord.setGegevenAntwoord("My answer is A.");
        assertEquals(antwoord.getGegevenAntwoord(), "My answer is A.");
    }
}