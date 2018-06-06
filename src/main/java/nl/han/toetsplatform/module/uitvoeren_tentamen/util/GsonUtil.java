package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import com.google.gson.Gson;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.io.*;
import java.util.logging.Level;

public class GsonUtil {
    private static Gson gson;

    public GsonUtil() {
        gson = new Gson();
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

    /**
     * Schrijf een Tentamen object weg naar schijf in de vorm van JSON bestand
     *
     * @param obj Het Tentamen object
     * @param dir de directory + file name
     */
    public void writeTentamen(Tentamen obj, String dir) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dir);
        } catch (IOException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (fileWriter != null) {
                BufferedWriter bw = new BufferedWriter(fileWriter);
                String write = gson.toJson(obj);
                try {
                    bw.write(write);
                    bw.close();
                } catch (IOException e) {
                    Utils.logger.log(Level.SEVERE, e.getMessage());
                }
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
        StringBuilder contentBuilder = new StringBuilder();
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (fileReader != null) {
                BufferedReader br = new BufferedReader(fileReader);
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    contentBuilder.append(sCurrentLine).append("\n");
                }

                return contentBuilder.toString();
            }
        }

        return "";
    }
}
