package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UploadenTentamenDaoTest {

    @Test
    public void uploadTentamen() {
        //Arrange
        long date = 13531531;
        VersieDto versieDto = new VersieDto(date,1, "testomschijvingversie");
        StudentDto studentDto = new StudentDto(573612, "ASD17/18 S2");
        UUID id = java.util.UUID.fromString("d3aa88e2-c754-41e0-8ba6-4198a34aa0a2");
        IngevuldeVraagDto ingevuldeVraagDto = new IngevuldeVraagDto(id, "Dit is een test antwoord", versieDto);
        List<IngevuldeVraagDto> antwoorden = new ArrayList<IngevuldeVraagDto>();
        antwoorden.add(ingevuldeVraagDto);
        UitgevoerdTentamenDto uitgevoerdTentamenDto = new UitgevoerdTentamenDto(id, "toetsnaam", "testhash", studentDto, versieDto, antwoorden);

        UploadenTentamenDAO uploadenTentamenDAO = new UploadenTentamenDAO();

        //Act
        String uploadResult = uploadenTentamenDAO.uploadTentamen(uitgevoerdTentamenDto);

        //Assert

        assertEquals("Uploading tentamen resultaat: http error: 500", uploadResult);
    }
}
