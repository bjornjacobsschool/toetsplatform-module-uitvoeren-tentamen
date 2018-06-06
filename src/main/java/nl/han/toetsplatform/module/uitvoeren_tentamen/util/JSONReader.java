package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class JSONReader {

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONArray JSONArrayFromURL(URL url) throws IOException, JSONException {
        try (InputStream is = url.openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = this.readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    public JSONArray JSONArrayFromFolder(String pathname) throws IOException, JSONException {

        File folder = new File(pathname);
        File[] listOfFiles = folder.listFiles();

        JSONArray files = new JSONArray();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                try (InputStream is = new FileInputStream(file)) {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String jsonText = this.readAll(rd);

                    files.put(new JSONObject(jsonText));
                }
            }
        }

        return files;
    }

    public JSONObject JSONObjectFromURL(URL url) throws IOException, JSONException {
        try (InputStream is = url.openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = this.readAll(rd);
            return new JSONObject(jsonText);
        }
    }
}