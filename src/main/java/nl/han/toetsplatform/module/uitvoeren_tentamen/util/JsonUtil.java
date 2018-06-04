package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    private static final String DIRECTORY = "C:\\Users\\Kars\\Dropbox\\ASD\\Project\\Code\\JsonUtilTest\\";

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

    //TODO
    public static Tentamen readTentamen() {
        Tentamen readTentamen = new Tentamen();
        return readTentamen;
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
    public static <T> T ConvertJsonToJava(String jsonString, Class<T> cls) {
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
}
