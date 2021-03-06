package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub;

import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.cache.CacheDao;

import java.util.HashMap;
import java.util.Map;

public class CacheDaoStub implements CacheDao {

    private Map<Integer, Map<Integer, String>> data = new HashMap<>();

    @Override
    public void saveQuestion(int toetsId, int vraagId, String antwoordData) {
        if (!data.containsKey(toetsId))
            data.put(toetsId, new HashMap<>());

        data.get(toetsId).put(vraagId, antwoordData);
    }

    @Override
    public String getAntwoordData(int toetsId, int vraagId) {
        if (!data.containsKey(toetsId))
            return null;

        if (!data.get(toetsId).containsKey(vraagId))
            return null;

        return data.get(toetsId).get(vraagId);
    }

}
