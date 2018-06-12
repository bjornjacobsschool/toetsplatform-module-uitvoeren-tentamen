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
        tentamen.setVragen("BvIT91GMIi//sY4Ygubkd3gkk0ogak3lH2/6Zr6uJZDdrIf9oZmn18Hyc5JOoaR4tun25Sd8XDNZgNMrY9h/1Y0a4jSJLXoJwCpMXqPKm6Pm5JA0+QdLFzsHxzezGDBjA7XJHcsVZZHufSAfzJP3SRloyg78Qnuqo1uiilldWXsGyGsY2guIx3aeb3YIfJ+tjwuOC0PzRKB0LB07A1Y5RJIHEQeKyo46PU6aVm9ylK6VFB5cCuI40XSO/laH/rToRFwSxPhSlFlD1BfaEnrXCSGZ63iayNLx/KD1gLrD9vNBQfgDTgo45V15rtNQsfZRexoKqAl5CkZ8kZOpbRRXVqjcH3SXOY06hd9H45y8WKDGuO0MpFQwxEYqXOhJWd5v60XU7oTW5Gbpz2nomJTKRiEFQRphdLTuDe4FqFC0EJw=");
        tentamen.decryptVragen("hello world hand");

        assertEquals(tentamen.getVragen().get(0).getId(), "d290f1ee-6c54-4b01-90e6-d701748f0851");
        assertEquals(tentamen.getVragen().get(0).getThema(), "Graven");
        assertEquals(tentamen.getVragen().get(0).getPunten(), 5);
        assertEquals(tentamen.getVragen().get(0).getNaam(), "Wat is het korste pad startend van A naar E");
        assertEquals(tentamen.getVragen().get(0).getVersie().getDatum().toString(), "Thu May 24 00:00:00 CEST 2018");
        assertEquals(tentamen.getVragen().get(0).getVersie().getOmschrijving(), "Spelfout verbeterd.");
        assertEquals(tentamen.getVragen().get(0).getVersie().getNummer(), "1.0.1");
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