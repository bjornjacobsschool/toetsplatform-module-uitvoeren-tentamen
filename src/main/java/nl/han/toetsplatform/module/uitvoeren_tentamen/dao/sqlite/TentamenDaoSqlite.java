package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite;

import com.google.inject.Inject;
import nl.han.toetsplatform.module.shared.storage.StorageDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.toets.ToetsDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Tentamen;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Versie;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Vraag;
import nl.han.toetsplatform.module.uitvoeren_tentamen.util.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TentamenDaoSqlite implements ToetsDao {

    /**
     * Injected from frontend GuiceModule.java
     */
    @Inject
    public StorageDao storageDao;

    @Override
    public List<Tentamen> getLocalTentamens() throws SQLException {
        List<Tentamen> tentamens = new ArrayList<>();

        ResultSet resultSet = storageDao.executeQuery("SELECT * FROM MODULE_UITVOEREN_TENTAMEN");
        while (resultSet.next()) {
            Tentamen tentamen = new Tentamen();
            tentamen.setId(resultSet.getString("tentamenid"));
            tentamen.setStudentNr(resultSet.getInt("studentnr"));
            tentamen.setNaam(resultSet.getString("naam"));
            tentamen.setHash(resultSet.getString("hash"));

            tentamens.add(tentamen);
        }

        return tentamens;
    }

    @Override
    public Tentamen fetchTentamenFromDatabase(String tentamenId) {
        Tentamen tentamen = new Tentamen();
        try {
            // Maak tentamen object aan met data uit database
            String query = "SELECT * FROM MODULE_UITVOEREN_TENTAMEN where tentamenid = '" + tentamenId + "'";
            ResultSet result = storageDao.executeQuery(query);
            if (result.next()) {
                tentamen.setId(tentamenId);
                tentamen.setStudentNr(result.getInt("studentnr"));
                Versie versie = new Versie();
                versie.setNummer(result.getString("versieNummer"));
                // TODO datum goed zetten in Versie
                versie.setOmschrijving(result.getString("omschrijving"));
                tentamen.setVersie(versie);
                tentamen.setNaam(result.getString("naam"));
                tentamen.setHash(result.getString("hash"));

                // Haal de vragen en antwoorden op, zet ze in een object en voeg ze toe
                List<Vraag> vragen = new ArrayList<Vraag>();
                String vragenQuery = "SELECT * FROM MODULE_UITVOEREN_VRAAG where tentamenid = '" + tentamenId + "'";
                ResultSet vragenResult = storageDao.executeQuery(vragenQuery);
                while (vragenResult.next()) {
                    Vraag newVraag = new Vraag();
                    newVraag.setId(vragenResult.getString("vraagid"));
                    newVraag.setNaam(vragenResult.getString("naam"));
                    newVraag.setDescription(vragenResult.getString("omschrijving"));
                    newVraag.setThema(vragenResult.getString("thema"));
                    newVraag.setPunten(vragenResult.getInt("punten"));
                    newVraag.setVraagtype(vragenResult.getString("vraagtype"));
                    Antwoord newAntwoord = new Antwoord();
                    newAntwoord.setTentamenId(tentamenId);
                    newAntwoord.setVraagId(vragenResult.getString("vraagid"));
                    String antwoordQuery = "SELECT gegevenAntwoord FROM MODULE_UITVOEREN_ANTWOORD WHERE " +
                            "vraagid = '" + vragenResult.getString("vraagid") + "' AND " +
                            "tentamenid = '" + tentamenId + "'";
                    ResultSet antwoordResult = storageDao.executeQuery(antwoordQuery);
                    if (antwoordResult.next()) {
                        newAntwoord.setGegevenAntwoord(antwoordResult.getString("gegevenAntwoord"));
                    }
                    newVraag.setAntwoord(newAntwoord);
                    vragen.add(newVraag);
                }
                tentamen.setVraagList(vragen);
            }
        } catch (SQLException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            storageDao.closeConnection();
        }
        return tentamen;
    }

    @Override
    public String saveTentamen(Tentamen tentamen) throws SQLException {
        String print = "";
        int studNo = tentamen.getStudentNr();
        String tentamenId = tentamen.getId();
        try {
            String studentQuery = "SELECT * FROM MODULE_UITVOEREN_STUDENT where studentnr = " + studNo;
            ResultSet resultStudent = storageDao.executeQuery(studentQuery);
            if (resultStudent.next()) {
                print += "Student bestaat\n";
                // Student bestaat al in lokale database
            } else {
                // Student bestaat nog niet -> maak aan
                print += "Student bestaat niet. Nu wel.\n";
                createStudentInLocalDatabase(studNo);
            }
            String tentamenQuery = "SELECT * FROM MODULE_UITVOEREN_TENTAMEN where studentnr = " + studNo +
                    " AND tentamenid = " + tentamenId;
            ResultSet resultTentamen = storageDao.executeQuery(tentamenQuery);
            if (resultTentamen.next()) {
                // Tentamen bestaat al in database -> updaten
                print += "Tentamen bestaat\n";
                updateAntwoordenInLocalDatabase(tentamen);
            } else {
                // Tentamen bestaat niet -> aanmaken
                print += "Tentamen bestaat niet.\n";
                createTentamenInLocalDatabase(tentamen);
            }
        } catch (SQLException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            storageDao.closeConnection();
        }
        return print;
    }

    @Override
    public void saveAntwoord(Vraag vraag, String tentamenId) {
        try {
            String query = "UPDATE MODULE_UITVOEREN_ANTWOORD SET " +
                    "gegevenAntwoord = '" + vraag.getAntwoord().getGegevenAntwoord() + "' " +
                    "WHERE vraagid = '" + vraag.getId() + "' " +
                    "AND tentamenid = '" + tentamenId + "'";
            storageDao.executeUpdate(query);
        } catch (SQLException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            storageDao.closeConnection();
        }

    }

    private void updateAntwoordenInLocalDatabase(Tentamen tentamen) {
        List<Vraag> vragen = tentamen.getVragen();
        String tentamenId = tentamen.getId();
        for (int i = 0; i < vragen.size(); i++) {
            Vraag vraag = vragen.get(i);
            saveAntwoord(vraag, tentamenId);
        }
    }

    private void createStudentInLocalDatabase(int studNo) {
        try {
            //TODO klas dynamisch maken
            String query = "INSERT INTO MODULE_UITVOEREN_STUDENT (studentnr, klas) VALUES (" + studNo + ", 'ASD-B')";
            storageDao.executeUpdate(query);
        } catch (SQLException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            storageDao.closeConnection();
        }
    }

    private void createTentamenInLocalDatabase(Tentamen tentamen) {
        try {
            // Maak tentamen aan
            String createTentamenQuery = "INSERT INTO MODULE_UITVOEREN_TENTAMEN (tentamenid, studentnr, versienummer, datum, omschrijving, naam, hash) " +
                    "VALUES ('" + tentamen.getId() + "', "+ tentamen.getStudentNr() + ", '" + tentamen.getVersie().getNummer() +
                    "', '" + tentamen.getStartdatum().toString() + "', '" + tentamen.getBeschrijving() + "', '" + tentamen.getNaam() + "', '" +
                    tentamen.getHash() + "')";
            storageDao.executeUpdate(createTentamenQuery);

            // Maak vragen aan met bijbehorend antwoord
            List<Vraag> vragen = tentamen.getVragen();
            for (int i = 0; i < vragen.size(); i++) {
                // Voor elke vraag, voeg de vraag toe en het antwoord
                Vraag currentVraag = vragen.get(i);
                String createVraagQuery = "INSERT INTO MODULE_UITVOEREN_VRAAG (vraagid, tentamenid, naam, omschrijving, thema, punten, vraagtype, vraagdata) " +
                        "VALUES ('" + currentVraag.getId() + "', '" + tentamen.getId() + "', '" + currentVraag.getNaam() +"','" + currentVraag.getDescription() +
                        "', '"+ currentVraag.getThema() +"', "+ currentVraag.getPunten() +",'" + currentVraag.getVraagtype() + "', '" + currentVraag.getData() + "')";
                storageDao.executeUpdate(createVraagQuery);

                Antwoord currentAntwoord = currentVraag.getAntwoord();
                String gegevenAntwoord = currentAntwoord.getGegevenAntwoord();
                String createAntwoordQuery = "INSERT INTO MODULE_UITVOEREN_ANTWOORD (vraagid, tentamenid, gegevenAntwoord) VALUES (" +
                        "'" + currentVraag.getId() + "', '" + tentamen.getId() + "', '" + gegevenAntwoord + "')";
                storageDao.executeUpdate(createAntwoordQuery);
            }
        } catch (SQLException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        } finally {
            storageDao.closeConnection();
        }
    }
}
