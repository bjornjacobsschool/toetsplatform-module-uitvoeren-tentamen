package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.JSONReader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DownloadenTentamenDAOTest {

    private DownloadenTentamenDAO dt;
    private JSONReader JSONReaderMock;
    private JSONObject t1;
    private JSONObject t2;
    private JSONArray jsonArray;

    @Before
    public void setUp() {
        JSONReaderMock = mock(JSONReader.class);
        dt = new DownloadenTentamenDAO(JSONReaderMock, new GsonUtil());

        t1 = new JSONObject();
        t1.put("beschrijving", "Beschrijving 1");
        t1.put("startdatum", "1375726743000");
        t1.put("vragen", "abcdefghijklmnop");
        t1.put("toegestaneHulpmiddelen", "Geen");
        t1.put("tijdsduur", "90 minuten");
        t1.put("id", "1");
        t1.put("naam", "APP Toets 1");

        JSONObject v1 = new JSONObject();
        v1.put("datum", "1375726743000");
        v1.put("omschrijving", "Spelfout verbeterd.");
        v1.put("nummer", "1.0.1");

        t1.put("versie", v1);

        t2 = new JSONObject();
        t2.put("beschrijving", "Beschrijving 2");
        t2.put("startdatum", "1375726743000");
        t2.put("vragen", "abcdefghijklmnop");
        t2.put("toegestaneHulpmiddelen", "Geen");
        t2.put("tijdsduur", "90 minuten");
        t2.put("id", "2");
        t2.put("naam", "SWA Toets 1");

        JSONObject v2 = new JSONObject();
        v2.put("datum", "1375726743000");
        v2.put("omschrijving", "Spelfout verbeterd.");
        v2.put("nummer", "1.0.5");

        t2.put("versie", v2);

        jsonArray = new JSONArray();
        jsonArray.put(t1);
        jsonArray.put(t2);
    }

    @Test
    public void downloadTentamen() throws Exception {
        when(JSONReaderMock.getJSONObjectFromURL(any())).thenReturn(t1);

        boolean res = dt.downloadTentamen("asd123");
        assertTrue(res);

        File file = new File(Utils.getFolder(Utils.DOWNLOADED_TENTAMENS).getAbsolutePath() + "/exam_asd123.json");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
        assertEquals(content, "{\"beschrijving\":\"Beschrijving 1\",\"startdatum\":\"1375726743000\",\"vragen\":\"abcdefghijklmnop\",\"tijdsduur\":\"90 minuten\",\"toegestaneHulpmiddelen\":\"Geen\",\"id\":\"1\",\"naam\":\"APP Toets 1\",\"versie\":{\"datum\":\"1375726743000\",\"omschrijving\":\"Spelfout verbeterd.\",\"nummer\":\"1.0.1\"}}");

        assertTrue(file.delete());
    }

    @Test
    public void getKlaargezetteTentamens() throws IOException {
        when(JSONReaderMock.getJSONArrayFromURL(any())).thenReturn(jsonArray);

        List<Tentamen> result = dt.getKlaargezetteTentamens();

        assertEquals(result.size(), 2);

        assertEquals(result.get(0).getNaam(), "APP Toets 1");
        assertEquals(result.get(0).getId(), "1");
        assertEquals(result.get(0).getBeschrijving(), "Beschrijving 1");
        assertEquals(result.get(0).getStartdatum().toString(), "Sun Jan 17 06:30:00 CET 45565");
        assertEquals(result.get(0).getToegestaneHulpmiddelen(), "Geen");
        assertEquals(result.get(0).getTijdsduur(), "90 minuten");
        assertEquals(result.get(0).getVersie().getOmschrijving(), "Spelfout verbeterd.");
        assertEquals(result.get(0).getVersie().getNummer(), "1.0.1");
        assertEquals(result.get(0).getVersie().getDatum().toString(), "Sun Jan 17 06:30:00 CET 45565");

        assertEquals(result.get(1).getNaam(), "SWA Toets 1");
        assertEquals(result.get(1).getId(), "2");
        assertEquals(result.get(1).getBeschrijving(), "Beschrijving 2");
        assertEquals(result.get(1).getStartdatum().toString(), "Sun Jan 17 06:30:00 CET 45565");
        assertEquals(result.get(1).getToegestaneHulpmiddelen(), "Geen");
        assertEquals(result.get(1).getTijdsduur(), "90 minuten");
        assertEquals(result.get(1).getVersie().getOmschrijving(), "Spelfout verbeterd.");
        assertEquals(result.get(1).getVersie().getNummer(), "1.0.5");
        assertEquals(result.get(1).getVersie().getDatum().toString(), "Sun Jan 17 06:30:00 CET 45565");
    }

    @Test
    public void getDownloadedTentamens() throws IOException {
        when(JSONReaderMock.getJSONArrayFromFolder(any())).thenReturn(jsonArray);

        List<Tentamen> result = dt.getDownloadedTentamens();
        assertEquals(result.size(), 2);

        assertEquals(result.get(0).getNaam(), "APP Toets 1");
        assertEquals(result.get(0).getId(), "1");
        assertEquals(result.get(0).getBeschrijving(), "Beschrijving 1");
        assertEquals(result.get(0).getStartdatum().toString(), "Sun Jan 17 06:30:00 CET 45565");
        assertEquals(result.get(0).getToegestaneHulpmiddelen(), "Geen");
        assertEquals(result.get(0).getTijdsduur(), "90 minuten");
        assertEquals(result.get(0).getVersie().getOmschrijving(), "Spelfout verbeterd.");
        assertEquals(result.get(0).getVersie().getNummer(), "1.0.1");
        assertEquals(result.get(0).getVersie().getDatum().toString(), "Sun Jan 17 06:30:00 CET 45565");

        assertEquals(result.get(1).getNaam(), "SWA Toets 1");
        assertEquals(result.get(1).getId(), "2");
        assertEquals(result.get(1).getBeschrijving(), "Beschrijving 2");
        assertEquals(result.get(1).getStartdatum().toString(), "Sun Jan 17 06:30:00 CET 45565");
        assertEquals(result.get(1).getToegestaneHulpmiddelen(), "Geen");
        assertEquals(result.get(1).getTijdsduur(), "90 minuten");
        assertEquals(result.get(1).getVersie().getOmschrijving(), "Spelfout verbeterd.");
        assertEquals(result.get(1).getVersie().getNummer(), "1.0.5");
        assertEquals(result.get(1).getVersie().getDatum().toString(), "Sun Jan 17 06:30:00 CET 45565");
    }
}