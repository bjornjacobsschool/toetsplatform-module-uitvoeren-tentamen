package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen;

import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Student;

public class StudentDto {
    private int studentNummer;
    private String klas;

    public StudentDto(Student student) {
        this.studentNummer = Integer.parseInt(student.getStudentNr());
        this.klas = student.getKlas();
    }

    public StudentDto(int studentNummer, String klas) {
        this.studentNummer = studentNummer;
        this.klas = klas;
    }
}
