package nl.han.toetsapplicatie.module.dao;

public interface CacheDao {

    void saveQuestion(int toetsId, int vraagId, String antwoordData);

    String getAntwoordData(int toetsId, int vraagId);
    
}
