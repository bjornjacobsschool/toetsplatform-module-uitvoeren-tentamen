package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.downloaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IDownloadenTentamenDAO {
    public boolean downloadTentamen(int tentamenId);
    public List<Tentamen> getKlaargezetteTentamens() throws IOException, ParseException;
}
