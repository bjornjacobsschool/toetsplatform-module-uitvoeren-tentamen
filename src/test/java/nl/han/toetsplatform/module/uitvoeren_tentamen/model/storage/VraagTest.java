package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class VraagTest {

    private Vraag vraag;

    @Before
    public void setUp() {
        vraag = new Vraag();
    }

    @Test
    public void getPlugin() {
        vraag.setPlugin("han.plugin.graph");
        assertEquals(vraag.getPlugin(), "han.plugin.graph");
    }

    @Test
    public void setPlugin() {
        vraag.setPlugin("han.plugin.vrije.tekst");
        assertEquals(vraag.getPlugin(), "han.plugin.vrije.tekst");
    }

    @Test
    public void getMogelijkeAntwoorden() {
        vraag.setMogelijkeAntwoorden("Description 1");
        assertEquals(vraag.getMogelijkeAntwoorden(), "Description 1");
    }

    @Test
    public void setDescription() {
        vraag.setMogelijkeAntwoorden("Description 2");
        assertEquals(vraag.getMogelijkeAntwoorden(), "Description 2");
    }

    @Test
    public void getVraagType() {
        vraag.setVraagtype("Vraagtype 1");
        assertEquals(vraag.getVraagtype(), "Vraagtype 1");
    }

    @Test
    public void setVraagType() {
        vraag.setVraagtype("Vraagtype 2");
        assertEquals(vraag.getVraagtype(), "Vraagtype 2");
    }

    @Test
    public void getData() {
        vraag.setData("Data 1");
        assertEquals(vraag.getData(), "Data 1");
    }

    @Test
    public void setData() {
        vraag.setData("Data 2");
        assertEquals(vraag.getData(), "Data 2");
    }

    @Test
    public void getId() {
        vraag.setId("1");
        assertEquals(vraag.getId(), "1");
    }

    @Test
    public void setId() {
        vraag.setId("1");
        assertEquals(vraag.getId(), "1");
    }

    @Test
    public void getNaam() {
        vraag.setNaam("Question 1");
        assertEquals(vraag.getNaam(), "Question 1");
    }

    @Test
    public void setNaam() {
        vraag.setNaam("Question 1");
        assertEquals(vraag.getNaam(), "Question 1");
    }

    @Test
    public void getThema() {
        vraag.setThema("Graven");
        assertEquals(vraag.getThema(), "Graven");
    }

    @Test
    public void setThema() {
        vraag.setThema("Graven");
        assertEquals(vraag.getThema(), "Graven");
    }

    @Test
    public void getPunten() {
        vraag.setPunten(5);
        assertEquals(vraag.getPunten(), 5);
    }

    @Test
    public void setPunten() {
        vraag.setPunten(5);
        assertEquals(vraag.getPunten(), 5);
    }

    @Test
    public void getVersie() {
        getSetVersie();
    }

    @Test
    public void setVersie() {
        getSetVersie();
    }

    private void getSetVersie() {
        Versie v = new Versie();
        v.setOmschrijving("abc");
        v.setNummer("1");

        Date d = new Date();
        d.setTime(34567654);
        v.setDatum(d);
        vraag.setVersie(v);

        assertEquals(v.getNummer(), "1");
        assertEquals(v.getOmschrijving(), "abc");
        assertEquals(v.getDatum().toString(), "Thu Jan 01 10:36:07 CET 1970");
    }
}