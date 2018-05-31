package nl.han.toetsplatform.module.uitvoeren_tentamen.dao;

import nl.han.toetsplatform.module.shared.model.Vraag;

import java.util.List;

public interface VraagDao {

    List<Vraag> getVragen();

    Vraag getVraag(int id);

}
