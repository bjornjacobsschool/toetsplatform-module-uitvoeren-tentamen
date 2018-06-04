package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;

/**
 * Class that converts Java objects to JSON and vice versa
 * and is able to read and write Tentamen objects to JSON
 * @author Kars
 */
public class JsonUtil {
    private static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
    }

    // Dit moet aangepast worden...
    private static final String DIRECTORY = "C:\\Users\\Kars\\Dropbox\\ASD\\Project\\Repo\\toetsplatform-module-uitvoeren-tentamen\\json";

    /**
     * Sla een tentamen op als JSON bestand
     * @param tentamen De JSON string van het Tentamen object
     */
    public static void writeTentamen(Tentamen tentamen) {
        int studentNummer = tentamen.getStudentNr();
        String filename = DIRECTORY + Integer.toString(studentNummer) + ".JSON";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            String writeString = convertJavaToJson(tentamen);
            bw.write(writeString);
            bw.close();
            //System.out.println(writeString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Maak Tentamen object van JSON bestand op schijf
     * @param filename bestandsnaam (eindigt op .JSON)
     * @return Tentamen object opgesteld uit de data uit het bestand
     */
    public static Tentamen readTentamen(String filename) {
        String file = DIRECTORY + filename;
        System.out.println("Trying to read from: "+ file);
        Tentamen t  = convertJsonToJava(readFileToString(file), Tentamen.class);
        return t;
    }

    /**
     * Converts a Java object to JSON string
     * @param obj The object to be converted
     * @return A string representation of a JSON object
     */
    public static String convertJavaToJson(Object obj) {
        String jsonResult = "";
        try {
            jsonResult = mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e){
            System.out.println("JsonGenerationException");
            //e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("JsonMappingException");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            //e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * Converts a JSON string into a Java object
     * @param jsonString A JSON object represented as a string
     * @param cls The type of class the JSON string will be converted into
     * @param <T> Generic representation of a class
     * @return A java object (cls) with the properties derived from the JSON string (if possible)
     */
    public static <T> T convertJsonToJava(String jsonString, Class<T> cls) {
        T result = null;
        try {
            result = mapper.readValue(jsonString, cls);
        } catch (JsonGenerationException e){
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Converts the content of a file to a string
     * @param filePath the direct location of the file
     * @return String representation of content of file
     */
    private static String readFileToString(String filePath)
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
