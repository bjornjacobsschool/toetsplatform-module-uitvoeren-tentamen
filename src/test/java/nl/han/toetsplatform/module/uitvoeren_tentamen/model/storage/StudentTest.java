package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StudentTest {

    private Student student;

    @Before
    public void setUp() {
        student = new Student();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getStudentNr() {
        student.setStudentNr("555928");
        assertEquals(student.getStudentNr(), "555928");
    }

    @Test
    public void setStudentNr() {
        student.setStudentNr("345674");
        assertEquals(student.getStudentNr(), "345674");
    }

    @Test
    public void getKlas() {
        student.setKlas("ASD A-145-BN");
        assertEquals(student.getKlas(), "ASD A-145-BN");
    }

    @Test
    public void setKlas() {
        student.setKlas("ASD B-4-RA");
        assertEquals(student.getKlas(), "ASD B-4-RA");
    }
}