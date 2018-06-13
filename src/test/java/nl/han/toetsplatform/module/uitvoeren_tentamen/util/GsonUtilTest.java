package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GsonUtilTest {

    private GsonUtil gsonUtil;
    private JSONObject t1;
    private JSONObject t2;
    private JSONObject vr1;
    private JSONArray jsonArray;

    @Before
    public void setUp() {
        gsonUtil = new GsonUtil();
        t1 = new JSONObject();

        t1.put("beschrijving", "Beschrijving 1");
        t1.put("startdatum", "1375726743000");
        t1.put("vragen", "abcdefghijklmnop");
        t1.put("toegestaneHulpmiddelen", "Geen");
        t1.put("tijdsduur", "90 minuten");
        t1.put("id", "1");
        t1.put("naam", "APP Toets 1");

        JSONObject v1 = new JSONObject();
        v1.put("datum", "1367777943000");
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
        v2.put("datum", "1367777943000");
        v2.put("omschrijving", "Spelfout verbeterd.");
        v2.put("nummer", "1.0.5");

        t2.put("versie", v2);

        jsonArray = new JSONArray();

        vr1 = new JSONObject();
        vr1.put("id", "1");
        vr1.put("naam", "Naampje");
        vr1.put("mogelijkeAntwoorden", "description");
        vr1.put("vraagType", "vraagType");
        vr1.put("plugin", "plugin");
        vr1.put("thema", "thema");
        vr1.put("punten", 1);
        vr1.put("data", "data");
        vr1.put("versie", v1);

        jsonArray.put(vr1);


    }

    @Test
    public void loadTentamen() throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Utils.getFolder(Utils.DOWNLOADED_TENTAMENS).getAbsolutePath() + "/exam_1.json"), StandardCharsets.UTF_8));
        writer.write(t1.toString());
        writer.close();

        File file = new File(Utils.getFolder(Utils.DOWNLOADED_TENTAMENS).getAbsolutePath() + "/exam_1.json");
        assertTrue(file.exists());

        Tentamen t = gsonUtil.loadTentamen(Utils.getFolder(Utils.DOWNLOADED_TENTAMENS).getAbsolutePath() + "/exam_1.json");

        assertEquals(t.getNaam(), "APP Toets 1");
        assertEquals(t.getId(), "1");
        assertEquals(t.getBeschrijving(), "Beschrijving 1");
        assertEquals(t.getStartdatum().toString(), "Sun Jan 17 06:30:00 CET 45565");
        assertEquals(t.getToegestaneHulpmiddelen(), "Geen");
        assertEquals(t.getTijdsduur(), "90 minuten");
        assertEquals(t.getVersie().getOmschrijving(), "Spelfout verbeterd.");
        assertEquals(t.getVersie().getNummer(), "1.0.1");
        assertEquals(t.getVersie().getDatum().toString(), "Mon Feb 27 06:30:00 CET 45313");
        assertTrue(file.delete());
    }

    @Test
    public void tentamenStringToModel() {
        Tentamen t = gsonUtil.tentamenStringToModel(t1.toString());

        assertEquals(t.getNaam(), "APP Toets 1");
        assertEquals(t.getId(), "1");
        assertEquals(t.getBeschrijving(), "Beschrijving 1");
        assertEquals(t.getStartdatum().toString(), "Sun Jan 17 06:30:00 CET 45565");
        assertEquals(t.getToegestaneHulpmiddelen(), "Geen");
        assertEquals(t.getTijdsduur(), "90 minuten");
        assertEquals(t.getVersie().getOmschrijving(), "Spelfout verbeterd.");
        assertEquals(t.getVersie().getNummer(), "1.0.1");
        assertEquals(t.getVersie().getDatum().toString(), "Mon Feb 27 06:30:00 CET 45313");
    }

    @Test
    public void writeTentamen() {
    }

    @Test
    public void vragenJSONToList() {
        List<Vraag> result = gsonUtil.vragenJSONToList(jsonArray.toString());

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getNaam(), "Naampje");
        assertEquals(result.get(0).getId(), "1");
        assertEquals(result.get(0).getMogelijkeAntwoorden(), "description");
        assertEquals(result.get(0).getVraagType(), "vraagType");
        assertEquals(result.get(0).getPlugin(), "plugin");
        assertEquals(result.get(0).getThema(), "thema");
        assertEquals(result.get(0).getPunten(), 1);
        assertEquals(result.get(0).getData(), "data");
        assertEquals(result.get(0).getVersie().getOmschrijving(), "Spelfout verbeterd.");
        assertEquals(result.get(0).getVersie().getNummer(), "1.0.1");
        assertEquals(result.get(0).getVersie().getDatum().toString(), "Mon Feb 27 06:30:00 CET 45313");
    }
}

