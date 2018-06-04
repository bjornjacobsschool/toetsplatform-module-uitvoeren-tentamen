package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IDownloadenTentamenDAO {
    public boolean downloadTentamen(String tentamenId) throws IOException, JSONException;
    public List<Tentamen> getKlaargezetteTentamens() throws IOException, ParseException, JSONException;
}
