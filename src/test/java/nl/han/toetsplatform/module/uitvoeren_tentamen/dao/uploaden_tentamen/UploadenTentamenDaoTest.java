package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import nl.han.toetsapplicatie.apimodels.dto.IngevuldeVraagDto;
import nl.han.toetsapplicatie.apimodels.dto.StudentDto;
import nl.han.toetsapplicatie.apimodels.dto.UitgevoerdTentamenDto;
import nl.han.toetsapplicatie.apimodels.dto.VersieDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UploadenTentamenDaoTest {

//    @Test
//    public void uploadTentamen() {
//        //Arrange
//        long date = 13531531;
//        VersieDto versieDto = new VersieDto(date,1, "testomschijvingversie");
//        StudentDto studentDto = new StudentDto(573612, "ASD17/18 S2");
//        UUID id = java.util.UUID.fromString("123e4567-e89b-12d3-a456-426655440000");
//        UUID idtentamen = java.util.UUID.fromString("123e4567-e89b-12d3-a456-426655441111");
//        IngevuldeVraagDto ingevuldeVraagDto = new IngevuldeVraagDto(id, "Dit is een test antwoord", versieDto);
//        List<IngevuldeVraagDto> antwoorden = new ArrayList<IngevuldeVraagDto>();
//        antwoorden.add(ingevuldeVraagDto);
//        UitgevoerdTentamenDto uitgevoerdTentamenDto = new UitgevoerdTentamenDto(id, "toetsnaam", "testhash", studentDto, versieDto, antwoorden);
//
//        UploadenTentamenDAO uploadenTentamenDAO = new UploadenTentamenDAO();
//
//        //Act
//        String uploadResult = uploadenTentamenDAO.uploadTentamen(uitgevoerdTentamenDto);
//
//        //Assert
//        System.out.println(uploadResult);
//        //      assertEquals("Uploading tentamen resultaat: http error: 500", uploadResult);
//        //TODO verander de expected naar een success bericht wannneer de backend klaar is voor upload.
//    }
}
