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
    private GsonUtil gsonUtilMock;
    private JSONObject t1;
    private JSONObject t2;
    private JSONArray jsonArray;

    @Before
    public void setUp() {
        JSONReaderMock = mock(JSONReader.class);
        gsonUtilMock = mock(GsonUtil.class);
        dt = new DownloadenTentamenDAO(JSONReaderMock, gsonUtilMock);

        t1 = new JSONObject();
        t1.put("naam", "t1");
        t1.put("id", "t1-asd123");
        t1.put("beschrijving", "ASD");
        t1.put("startdatum", "24-06-2018 12:15:00");

        t2 = new JSONObject();
        t2.put("naam", "t2");
        t2.put("id", "t2-abc456");
        t2.put("beschrijving", "APP");
        t2.put("startdatum", "26-08-2090 12:45:00");

        jsonArray = new JSONArray();
        jsonArray.put(t1);
        jsonArray.put(t2);
    }

    @Test
    public void downloadTentamen() throws Exception {
        when(JSONReaderMock.JSONObjectFromURL(any())).thenReturn(t1);

        boolean res = dt.downloadTentamen("asd123");
        assertTrue(res);

        File file = new File(Utils.getFolder(Utils.DOWNLOADED_TENTAMENS).getAbsolutePath() + "/exam_asd123.json");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
        assertEquals(content, "{\"beschrijving\":\"ASD\",\"startdatum\":\"24-06-2018 12:15:00\",\"id\":\"t1-asd123\",\"naam\":\"t1\"}");

        assertTrue(file.delete());
    }

    @Test
    public void getKlaargezetteTentamens() throws IOException {
        when(JSONReaderMock.JSONArrayFromURL(any())).thenReturn(jsonArray);

        List<Tentamen> result = dt.getKlaargezetteTentamens();

        assertEquals(result.size(), 2);

        assertEquals(result.get(0).getNaam(), "t1");
        assertEquals(result.get(0).getId(), "t1-asd123");
        assertEquals(result.get(0).getBeschrijving(), "ASD");
//        assertEquals(result.get(0).getStrStartDatum(), "24-06-2018 12:15");

        assertEquals(result.get(1).getNaam(), "t2");
        assertEquals(result.get(1).getId(), "t2-abc456");
        assertEquals(result.get(1).getBeschrijving(), "APP");
//        assertEquals(result.get(1).getStrStartDatum(), "26-08-2090 12:45");
    }

    @Test
    public void getDownloadedTentamens() throws IOException {
        when(JSONReaderMock.JSONArrayFromFolder(any())).thenReturn(jsonArray);

        List<Tentamen> result = dt.getDownloadedTentamens();
        assertEquals(result.size(), 2);

        assertEquals(result.get(0).getNaam(), "t1");
        assertEquals(result.get(0).getId(), "t1-asd123");
        assertEquals(result.get(0).getBeschrijving(), "ASD");
//        assertEquals(result.get(0).getStrStartDatum(), "24-06-2018 12:15");

        assertEquals(result.get(1).getNaam(), "t2");
        assertEquals(result.get(1).getId(), "t2-abc456");
        assertEquals(result.get(1).getBeschrijving(), "APP");
//        assertEquals(result.get(1).getStrStartDatum(), "26-08-2090 12:45");
    }
}