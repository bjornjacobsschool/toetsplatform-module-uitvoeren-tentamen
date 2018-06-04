package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.JSONReader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DownloadenTentamenDAO implements IDownloadenTentamenDAO {

    @Override
    public boolean downloadTentamen(String tentamenId) throws IOException, JSONException {

        // TODO: Replace with real URL
        JSONObject jTentamen = JSONReader.JSONObjectFromURL("https://www.focusws.nl/exam1.json");

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("exam1.json"), StandardCharsets.UTF_8));
        System.out.println(jTentamen.toString());
        writer.write(jTentamen.toString());
        writer.close();

        return true;
    }

    @Override
    public List<Tentamen> getKlaargezetteTentamens() throws IOException, ParseException, JSONException {
        List<Tentamen> tentamens = new ArrayList<>();

        // TODO: Replace with real URL
        JSONArray jTentamens = JSONReader.JSONArrayFromURL("https://www.focusws.nl/exam.json");

        for (int i = 0; i < jTentamens.length(); i++) {
            JSONObject o = jTentamens.getJSONObject(i);
            Tentamen t = new Tentamen();

            t.setTentamenId(o.getString("id"));
            t.setNaam(o.getString("naam"));
            t.setBeschrijving(o.getString("beschrijving"));

            String dateStr = o.getString("startdatum");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            t.setStartDatum(sdf.parse(dateStr));

            tentamens.add(t);
        }

        return tentamens;
    }
}
