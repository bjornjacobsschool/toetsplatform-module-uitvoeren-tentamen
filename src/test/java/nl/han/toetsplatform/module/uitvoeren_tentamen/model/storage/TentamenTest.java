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
    public void getId() {
        tentamen.setId("123456");
        assertEquals(tentamen.getId(), "123456");
    }

    @Test
    public void setId() {
        tentamen.setId("654321");
        assertEquals(tentamen.getId(), "654321");
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
    public void testGetVragen() {
        List<Vraag> VraagList = new ArrayList<>();

        Vraag a1 = new Vraag();
        a1.setId("1");
        a1.setNaam("1");
        a1.setDescription("1");

        Vraag a2 = new Vraag();
        a2.setId("2");
        a2.setNaam("2");
        a2.setDescription("2");

        VraagList.add(a1);
        VraagList.add(a2);

        tentamen.setVraagList(VraagList);

        assertEquals(tentamen.getVragen().size(), 2);
        assertEquals(tentamen.getVragen().get(0), a1);
        assertEquals(tentamen.getVragen().get(1), a2);
    }

    @Test
    public void testSetVragen() {
        List<Vraag> VraagList = new ArrayList<>();

        Vraag a1 = new Vraag();
        a1.setId("11");
        a1.setNaam("11");
        a1.setDescription("11");

        Vraag a2 = new Vraag();
        a2.setId("22");
        a2.setNaam("22");
        a2.setDescription("22");

        VraagList.add(a1);
        VraagList.add(a2);

        tentamen.setVraagList(VraagList);

        assertEquals(tentamen.getVragen().size(), 2);
        assertEquals(tentamen.getVragen().get(0), a1);
        assertEquals(tentamen.getVragen().get(1), a2);
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
    public void setStrtartdatum() {
        tentamen.setStrStartdatum("10-10-2010 10:10");
        assertEquals(tentamen.getStrStartdatum(), "10-10-2010 10:10");
    }

    @Test
    public void getStartdatum() {
        Date date = new Date();
        tentamen.setStartdatum(date);
        assertEquals(tentamen.getStartdatum(), date);
    }

    @Test
    public void setStartdatum() {
        Date date = new Date();
        tentamen.setStartdatum(date);
        assertEquals(tentamen.getStartdatum(), date);
    }

    private void setDecryptGetVragen() throws Exception {
        tentamen.setVragen("C58VCdZmEGITxud92+VDr6gj4t7ux1B3l3UHUuMthult8JOyxrDZy+C2Hjrd8fsWHXTaTGjH8nzPy1JSNljpCERHcsq4ERG+d7HGXQRaqjZZYLIZivLTBYshPkI/h8SHrFCUxZfHpfHzvIIfuSly4Em1sdWXRphSPfK43CbwKZWZS2zd8aFmz2YkzKK2GK3tEfk41vqv2HZEnyA5bsGM3ntwEttQsOPf9SOzWu+/gdxAHi2e5G8b46/CnVzInzVLsY1aJpXVIKTfmgdUOIDWAWfyDLvgo+Bpasjg3UVGOXbndxTsKjOR+XJerKGIrccbpIg6P7mySL/0/+uXNnnCJIUfPpQ734y3Gpcanugcqhw=");
        tentamen.decryptVragen("hello world hand");

        assertEquals(tentamen.getVragen().get(0).getId(), "123e4567-e89b-12d3-a456-426655441111");
        assertEquals(tentamen.getVragen().get(0).getThema(), "GRAAF");
        assertEquals(tentamen.getVragen().get(0).getPunten(), 15);
        assertEquals(tentamen.getVragen().get(0).getNaam(), "Hoe verbind je twee nodes binnen een graaf?");
        assertEquals(tentamen.getVragen().get(0).getVersie().getDatum().toString(), "Mon Feb 27 06:30:00 CET 45313");
        assertEquals(tentamen.getVragen().get(0).getVersie().getOmschrijving(), "Eerste versie");
        assertEquals(tentamen.getVragen().get(0).getVersie().getNummer(), "1");
    }

    @Test
    public void setVragen() throws Exception {
        setDecryptGetVragen();
    }

    @Test
    public void decryptVragen() throws Exception {
        setDecryptGetVragen();
    }

    @Test
    public void getVragen() throws Exception {
        setDecryptGetVragen();
    }

    @Test
    public void getStrStartdatum() {

        Date date = new Date();
        date.setTime(345678765);
        tentamen.setStartdatum(date);
        assertEquals(tentamen.getStrStartdatum(), "05-01-1970 01:01");
    }

    @Test
    public void getToegestaneHulpmiddelen() {
        String toegestaneHulpmiddelen = "Geen";
        tentamen.setToegestaneHulpmiddelen(toegestaneHulpmiddelen);
        assertEquals(tentamen.getToegestaneHulpmiddelen(), toegestaneHulpmiddelen);
    }

    @Test
    public void setToegestaneHulpmiddelen() {
        String toegestaneHulpmiddelen = "Geen";
        tentamen.setToegestaneHulpmiddelen(toegestaneHulpmiddelen);
        assertEquals(tentamen.getToegestaneHulpmiddelen(), toegestaneHulpmiddelen);
    }

    @Test
    public void getTijdsduur() {
        tentamen.setTijdsduur("90 minuten");
        assertEquals(tentamen.getTijdsduur(), "90 minuten");
    }

    @Test
    public void setTijdsduur() {
        tentamen.setTijdsduur("90 minuten");
        assertEquals(tentamen.getTijdsduur(), "90 minuten");
    }
}