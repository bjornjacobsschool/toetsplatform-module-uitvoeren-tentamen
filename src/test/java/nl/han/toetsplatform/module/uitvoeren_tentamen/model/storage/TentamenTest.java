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

        tentamen.setVragen(VraagList);

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

        tentamen.setVragen(VraagList);

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
    public void getStartDatum() {
        Date date = new Date();
        tentamen.setStartdatum(date);
        assertEquals(tentamen.getStartdatum(), date);
    }

    @Test
    public void setStartDatum() {
        Date date = new Date();
        tentamen.setStartdatum(date);
        assertEquals(tentamen.getStartdatum(), date);
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
}