package nl.han.toetsplatform.module.uitvoeren_tentamen.dao;

public interface CacheDao {

    void saveQuestion(int toetsId, int vraagId, String antwoordData);

    String getAntwoordData(int toetsId, int vraagId);

}
