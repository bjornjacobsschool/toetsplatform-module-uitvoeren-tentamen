package nl.han.toetsplatform.module.uitvoeren_tentamen.util;
import com.google.gson.Gson;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;

import java.io.*;

public class GsonUtil {
    private static Gson gson;

    public GsonUtil() {
        gson = new Gson();
    }

    public Tentamen loadTentamen(String dir) {
        Tentamen t = new Tentamen();
        try {
            t = gson.fromJson(readFileToString(dir), Tentamen.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public void writeTentamen(Tentamen obj, String dir) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(dir));
            String write = gson.toJson(obj);
            bw.write(write);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonInString = gson.toJson(obj);
    }

    /**
     * Converts the content of a file to a string
     * @param filePath the direct location of the file
     * @return String representation of content of file
     */
    public String readFileToString(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
