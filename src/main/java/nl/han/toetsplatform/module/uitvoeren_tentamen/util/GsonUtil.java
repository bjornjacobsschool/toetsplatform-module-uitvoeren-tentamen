package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class GsonUtil {
    private static Gson gson;

    public GsonUtil() {
        init();
    }

    private static void init() {
        JsonSerializer<Date> serializer = (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.getTime());
        JsonDeserializer<Date> deserializer = (json, typeOfT, context) -> json == null ? null : new Date(json.getAsLong() * 1000);

        gson = new GsonBuilder().registerTypeAdapter(Date.class, serializer).registerTypeAdapter(Date.class, deserializer).create();
    }

    /**
     * Laad een tentamen in uit JSON bestand van de schijf
     *
     * @param dir file path + name
     * @return Tentamen object aangemaakt uit het bestand
     */
    public Tentamen loadTentamen(String dir) {
        Tentamen t = new Tentamen();
        try {
            t = gson.fromJson(readFileToString(dir), Tentamen.class);
        } catch (Exception e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }
        return t;
    }

    public Tentamen tentamenStringToModel(String jsonString) {
        Tentamen t = new Tentamen();
        try {
            t = gson.fromJson(jsonString, Tentamen.class);
        } catch (Exception e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }
        return t;
    }

    /**
     * Schrijf een Tentamen object weg naar schijf in de vorm van JSON bestand
     *
     * @param obj Het Tentamen object
     * @param dir de directory + file name
     */
    public void writeTentamen(Tentamen obj, String dir) throws IOException {
        try (FileWriter fileWriter = new FileWriter(dir);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            String write = gson.toJson(obj);
            try {
                bufferedWriter.write(write);
                bufferedWriter.close();
            } catch (IOException e) {
                Utils.getLogger().log(Level.SEVERE, e.getMessage());
            }
        } catch (IOException e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Converts the content of a file to a string
     *
     * @param filePath the direct location of the file
     * @return String representation of content of file
     */
    public String readFileToString(String filePath) throws IOException {
        String returnString = "";
        StringBuilder contentBuilder = new StringBuilder();

        try (FileReader fileReader = new FileReader(filePath);
         BufferedReader bufferedReader = new BufferedReader(fileReader)){

            String sCurrentLine;

            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }

            returnString = contentBuilder.toString();
        } catch (FileNotFoundException e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }

        return returnString;
    }

    public List<Vraag> vragenJSONToList(String json) {
        List<Vraag> vragen = new ArrayList<>();
        try {
            vragen = gson.fromJson(json, new TypeToken<List<Vraag>>() {
            }.getType());
        } catch (Exception e) {
            Utils.getLogger().log(Level.SEVERE, e.getMessage());
        }
        return vragen;
    }
}
