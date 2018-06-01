DROP TABLE IF EXISTS MODULE_UITVOEREN_ANTWOORD;
DROP TABLE IF EXISTS MODULE_UITVOEREN_TENTAMEN;
DROP TABLE IF EXISTS MODULE_UITVOEREN_STUDENT;

CREATE TABLE MODULE_UITVOEREN_STUDENT (
  studentnr       int           NOT NULL,
  klas            varchar(10)   NOT NULL,
  CONSTRAINT pk_student PRIMARY KEY (studentnr)
);

CREATE TABLE MODULE_UITVOEREN_TENTAMEN (
  tentamenid      char(36)      NOT NULL,
  studentnr       int           NOT NULL,
  versieNummer    varchar(10)   NOT NULL,
  naam            varchar(50)   NOT NULL,
  hash            char(24)      NULL,
  CONSTRAINT pk_tentamen PRIMARY KEY (tentamenid),
  CONSTRAINT fk_Student_maakt_tentamen FOREIGN KEY (studentnr) REFERENCES STUDENT (studentnr)
);

CREATE TABLE MODULE_UITVOEREN_ANTWOORD (
  vraagid         char(36)      NOT NULL,
  tentamenid      char(36)      NOT NULL,
  gegevenAntwoord varchar(1024) NULL,
  CONSTRAINT pk_vraag PRIMARY KEY (vraagid, tentamenid),
  CONSTRAINT fk_vraag_van_tentamen FOREIGN KEY (tentamenid) REFERENCES TENTAMEN (tentamenid)
);