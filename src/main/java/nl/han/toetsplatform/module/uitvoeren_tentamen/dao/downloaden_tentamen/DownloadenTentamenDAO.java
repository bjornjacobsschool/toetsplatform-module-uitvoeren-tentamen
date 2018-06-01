package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.JSONReader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Versie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DownloadenTentamenDAO implements IDownloadenTentamenDAO {
    @Override
    public boolean downloadTentamen(int tentamenId) {
        return false;
    }

    @Override
    public List<Tentamen> getKlaargezetteTentamens() throws IOException, ParseException {
        List<Tentamen> tentamens = new ArrayList<>();

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
