package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.JSONReader;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DownloadenTentamenDAO implements IDownloadenTentamenDAO {

    private JSONReader reader;
    private GsonUtil gsonUtil;

    public DownloadenTentamenDAO(JSONReader reader, GsonUtil gsonUtil) {
        this.reader = reader;
        this.gsonUtil = gsonUtil;
    }

    @Override
    public boolean downloadTentamen(String tentamenId) throws IOException, JSONException {

        JSONObject jTentamen = this.reader.JSONObjectFromURL(new URL("http://94.124.143.127/tentamens/klaargezet/" + tentamenId));

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Utils.getFolder(Utils.DOWNLOADED_TENTAMENS).getAbsolutePath() + "/exam_" + tentamenId + ".json"), StandardCharsets.UTF_8));
        writer.write(jTentamen.toString());
        writer.close();

        return true;
    }

    @Override
    public List<Tentamen> getKlaargezetteTentamens() throws IOException, JSONException {
        List<Tentamen> tentamens = new ArrayList<>();

        JSONArray jTentamens = this.reader.JSONArrayFromURL(new URL("http://94.124.143.127/tentamens/klaargezet"));
        parseTentamenModel(tentamens, jTentamens);

        return tentamens;
    }

    @Override
    public List<Tentamen> getDownloadedTentamens() throws IOException, JSONException {
        List<Tentamen> tentamens = new ArrayList<>();

        JSONArray jTentamens = this.reader.JSONArrayFromFolder(Utils.getFolder(Utils.DOWNLOADED_TENTAMENS));
        parseTentamenModel(tentamens, jTentamens);

        return tentamens;
    }

    private void parseTentamenModel(List<Tentamen> tentamens, JSONArray jTentamens) {
        for (int i = 0; i < jTentamens.length(); i++) {
            JSONObject o = jTentamens.getJSONObject(i);
            tentamens.add(gsonUtil.tentamenStringToModel(o.toString()));
        }
    }
}
