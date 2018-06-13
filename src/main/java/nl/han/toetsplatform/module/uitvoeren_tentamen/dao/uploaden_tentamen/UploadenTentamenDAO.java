package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import com.google.gson.Gson;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import org.json.JSONWriter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadenTentamenDAO implements IUploadenTentamenDAO {
    @Override
    public Boolean uploadTentamen(Tentamen tentamen) {
        try {

            URL url = new URL("http://94.124.143.127/tentamens/uitgevoerd");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            String input = new GsonUtil().toJsonTentamen(tentamen);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED){
                throw new RuntimeException();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            while ((output = bufferedReader.readLine()) != null){
                System.out.println(output);
            }


            connection.disconnect();

            return true;
        } catch (Exception e){
            return false;
        }
    }
}
