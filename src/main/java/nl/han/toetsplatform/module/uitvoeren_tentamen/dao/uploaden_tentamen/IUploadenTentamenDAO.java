package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

public interface IUploadenTentamenDAO {
    String uploadTentamen(UitgevoerdTentamenDto tentamen);
}