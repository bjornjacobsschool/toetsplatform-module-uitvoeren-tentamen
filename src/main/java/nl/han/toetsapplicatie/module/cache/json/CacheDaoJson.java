package nl.han.toetsapplicatie.module.cache.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.han.toetsapplicatie.module.cache.CacheDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheDaoJson implements CacheDao {

    final String filePath = "./cache.json";

    @Override
    public void saveQuestion(int toetsId, int vraagId, String antwoordData) {
        Map<Integer, Map<Integer, String>> data = getData();
        if (!data.containsKey(toetsId)) {
            data.put(toetsId, new HashMap<>());
        }

        data.get(toetsId).put(vraagId, antwoordData);

        try {
            PrintWriter writer = new PrintWriter(filePath);
            String json = new Gson().toJson(data);
            writer.write(json);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Map<Integer, String>> getData() {
        File f = new File(filePath);
        if (!f.exists() || f.isDirectory()) {
            return new HashMap<>();
        }

        try {
            String json = Arrays.toString(Files.readAllLines(Paths.get(filePath)).toArray());
            Type hashMapType = new TypeToken<Map<Integer, Map<Integer, String>>>() {
            }.getType();
            Map<Integer, Map<Integer, String>> map = new Gson().fromJson(json, hashMapType);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    @Override
    public String getAntwoordData(int toetsId, int vraagId) {
        Map<Integer, Map<Integer, String>> map = getData();
        if (!map.containsKey(toetsId))
            return null;

        if (!map.get(toetsId).containsKey(vraagId))
            return null;

        return map.get(toetsId).get(vraagId);
    }
}
