package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class GsonUtil {
    private static Gson gson;

    public GsonUtil() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }
        return t;
    }

    public Tentamen tentamenStringToModel(String jsonString) {
        Tentamen t = new Tentamen();
        try {
            t = gson.fromJson(jsonString, Tentamen.class);
        } catch (Exception e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
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
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(dir);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (fileWriter != null && bufferedWriter != null) {

                String write = gson.toJson(obj);
                try {
                    bufferedWriter.write(write);
                    bufferedWriter.close();
                } catch (IOException e) {
                    Utils.logger.log(Level.SEVERE, e.getMessage());
                }
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (fileWriter != null) {
                fileWriter.close();
            }
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
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (fileReader != null && bufferedReader != null) {
                String sCurrentLine;

                while ((sCurrentLine = bufferedReader.readLine()) != null) {
                    contentBuilder.append(sCurrentLine).append("\n");
                }

                returnString = contentBuilder.toString();
            }

            if (fileReader != null) {
                fileReader.close();
            }

            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return returnString;
    }

    public List<Vraag> vragenJSONToList(String json) {
        List<Vraag> vragen = new ArrayList<>();
        try {
            vragen = gson.fromJson(json, new TypeToken<List<Vraag>>() {
            }.getType());
        } catch (Exception e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }
        return vragen;
    }

    public String toJsonTentamen(Tentamen tentamen) {
        return gson.toJson(tentamen);
    }
}
