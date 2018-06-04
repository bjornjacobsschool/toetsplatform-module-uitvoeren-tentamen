package nl.han.toetsplatform.module.uitvoeren_tentamen.dao;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JSONReaderTest {

    private JSONReader JSONReader;
    private String responseArray;
    private String responseObject;

    @Before
    public void setUp() {
        JSONReader = new JSONReader();
        responseArray = "[{\"beschrijving\":\"ASD\",\"startdatum\":\"24-06-2018 12:15:00\",\"id\":\"t1-asd123\",\"naam\":\"t1\"}]";
        responseObject = "{\"beschrijving\":\"ASD\",\"startdatum\":\"24-06-2018 12:15:00\",\"id\":\"t1-asd123\",\"naam\":\"t1\"}";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void JSONArrayFromURL() throws IOException {
        final HttpURLConnection mockCon = mock(HttpURLConnection. class);
        InputStream inputStream = new ByteArrayInputStream(responseArray.getBytes(StandardCharsets.UTF_8));
        when(mockCon.getLastModified()).thenReturn(10L, 11L);
        when(mockCon.getInputStream()).thenReturn(inputStream);

        //mocking HttpConnection by URLStreamHandler since we can not mock URL class.
        URLStreamHandler URLStreamHandler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return mockCon;
            }
        };


        JSONArray result = JSONReader.JSONArrayFromURL(new URL(null,"http://get.some.json.from.somewhere", URLStreamHandler));

        assertEquals(result.length(), 1);
        assertEquals(result.getJSONObject(0).getString("beschrijving"), "ASD");
        assertEquals(result.getJSONObject(0).getString("startdatum"), "24-06-2018 12:15:00");
        assertEquals(result.getJSONObject(0).getString("id"), "t1-asd123");
        assertEquals(result.getJSONObject(0).getString("naam"), "t1");
    }

    @Test
    public void JSONObjectFromURL() throws IOException {
        final HttpURLConnection mockCon = mock(HttpURLConnection. class);
        InputStream inputStream = new ByteArrayInputStream(responseObject.getBytes(StandardCharsets.UTF_8));
        when(mockCon.getLastModified()).thenReturn(10L, 11L);
        when(mockCon.getInputStream()).thenReturn(inputStream);

        //mocking HttpConnection by URLStreamHandler since we can not mock URL class.
        URLStreamHandler URLStreamHandler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return mockCon;
            }
        };


        JSONObject result = JSONReader.JSONObjectFromURL(new URL(null,"http://get.some.json.from.somewhere", URLStreamHandler));

        assertEquals(result.length(), 4);
        assertEquals(result.getString("beschrijving"), "ASD");
        assertEquals(result.getString("startdatum"), "24-06-2018 12:15:00");
        assertEquals(result.getString("id"), "t1-asd123");
        assertEquals(result.getString("naam"), "t1");
    }
}