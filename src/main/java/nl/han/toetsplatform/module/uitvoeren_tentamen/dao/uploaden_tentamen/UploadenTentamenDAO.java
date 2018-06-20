package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import com.google.inject.Inject;
import nl.han.toetsapplicatie.apimodels.dto.IngevuldeVraagDto;
import nl.han.toetsapplicatie.apimodels.dto.StudentDto;
import nl.han.toetsapplicatie.apimodels.dto.UitgevoerdTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Student;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.GsonUtil;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.HashingUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class UploadenTentamenDAO implements IUploadenTentamenDAO {
    private static Logger LOGGER = Logger.getLogger(UploadenTentamenDAO.class.getName());

    public VraagDao vraagDao;

    @Inject
    public UploadenTentamenDAO(VraagDao vraagDao) {
        this.vraagDao = vraagDao;
    }

    public String superUploadTentamen(Tentamen currentTentamen, Student studentDieDezeTentamenUitvoerd) {

        studentDieDezeTentamenUitvoerd.setKlas("ASD B-N");
        studentDieDezeTentamenUitvoerd.setStudentNr("424242");

        VersieDto versieDto = new VersieDto();
        StudentDto studentDto = new StudentDto();

        studentDto.setStudentNummer(Integer.valueOf(studentDieDezeTentamenUitvoerd.getStudentNr()));
        studentDto.setKlas(studentDieDezeTentamenUitvoerd.getKlas());

        UitgevoerdTentamenDto uitgevoerdTentamenDto = new UitgevoerdTentamenDto();
        uitgevoerdTentamenDto.setId(UUID.fromString(currentTentamen.getId()));
        uitgevoerdTentamenDto.setStudent(studentDto);
        uitgevoerdTentamenDto.setVersie(versieDto);

        List<Antwoord> antwoords = null;
        try {
            antwoords = vraagDao.getAntwoorden(currentTentamen.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<IngevuldeVraagDto> ingevuldeVraagDtos = new ArrayList<IngevuldeVraagDto>();
        for (Antwoord antwoord : antwoords) {
            IngevuldeVraagDto ingevuldeVraagDto = new IngevuldeVraagDto();
            ingevuldeVraagDto.setId(UUID.fromString(antwoord.getVraagId()));
            ingevuldeVraagDto.setGegevenAntwoord(antwoord.getGegevenAntwoord());
            ingevuldeVraagDto.setVersie(versieDto);
            ingevuldeVraagDtos.add(ingevuldeVraagDto);
        }

        uitgevoerdTentamenDto.setVragen(ingevuldeVraagDtos);

        String result = "";
        try {
            uitgevoerdTentamenDto.setHash(HashingUtil.generateHash(new GsonUtil().toJsonTentamen(uitgevoerdTentamenDto)));
            result = uploadTentamen(uitgevoerdTentamenDto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }



    @Override
    public String uploadTentamen(UitgevoerdTentamenDto tentamen) {
        String postResultString = "Uploading tentamen resultaat: "; //"the return variable for the result of the post request for uploading the test was not set.";

        try {

            URL url = new URL("http://94.124.143.127/tentamens/uitgevoerd");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            String input = new GsonUtil().toJsonTentamen(tentamen);

            System.out.println(input);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("http error: " + connection.getResponseCode());
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            while ((output = bufferedReader.readLine()) != null) {
                System.out.println(output);
            }
            postResultString += output;

            connection.disconnect();
        } catch (Exception e) {
            postResultString += e.getMessage();
        } finally {
            return postResultString;
        }
    }
}
