package nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub;

import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage.Antwoord;

import java.util.ArrayList;
import java.util.List;

public class VraagDaoStub implements VraagDao {

    private static List<Antwoord> vragen;

    public VraagDaoStub() {
        if (vragen != null)
            return;

        vragen = new ArrayList<>();

        /*
        // Vraag 1
        Antwoord vraag1 = new Antwoord();
        vraag1.setPlugin("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag1.setId(1);
        vraag1.setName("Vraag 1");
        vraag1.setData("{ \"nodes\": [ { \"name\": \"A\", \"connectedNodes\": [ { \"nodeInfo\": \"B\", \"distance\": 1 }, { \"nodeInfo\": \"C\", \"distance\": 7 } ] }, { \"name\": \"B\", \"connectedNodes\": [ { \"nodeInfo\": \"E\", \"distance\": 4 }, { \"nodeInfo\": \"D\", \"distance\": 2 } ] }, { \"name\": \"C\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 3 }, { \"nodeInfo\": \"D\", \"distance\": 3 } ] }, { \"name\": \"D\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 1 } ] }, { \"name\": \"E\", \"connectedNodes\": [ { \"nodeInfo\": \"F\", \"distance\": 1 } ] }, { \"name\": \"F\", \"connectedNodes\": [] } ], \"vraagText\": \"Laat stap voor stap in de onderstaande graaf zien hoe de kortste weg van node A naar alle andere\\nnodes wordt gevonden. Teken voor iedere stap de graaf en geef de lengte van het gevonden pad\\nnaar de vertices aan bij de desbetreffende vertices. Maak daarvoor gebruik van het algoritme van Dijkstra.\" }");

        // Vraag 2
        Vraag vraag2 = new Vraag();
        vraag2.setId(2);
        vraag2.setName("Vraag 2");
        vraag2.setPlugin("nl.han.toetsapplicatie.plugin.GraphPlugin");
        vraag2.setData("{ \"nodes\": [ { \"name\": \"1\", \"connectedNodes\": [ { \"nodeInfo\": \"2\" }, { \"nodeInfo\": \"5\" } ] }, { \"name\": \"2\", \"connectedNodes\": [ { \"nodeInfo\": \"3\" }, { \"nodeInfo\": \"5\" } ] }, { \"name\": \"5\", \"connectedNodes\": [] }, { \"name\": \"3\", \"connectedNodes\": [ { \"nodeInfo\": \"4\" } ] }, { \"name\": \"4\", \"connectedNodes\": [ { \"nodeInfo\": \"5\" }, { \"nodeInfo\": \"6\" } ] }, { \"name\": \"6\", \"connectedNodes\": [] } ], \"vraagText\": \"Geef de kortste pad vanuit 1 naar alle andere nodes.\" }");

        // Adding all the questions to the list
        vragen.add(vraag1);
        vragen.add(vraag2);*/
    }

    @Override
    public List<Antwoord> getAntwoorden() {
        return vragen;
    }

    @Override
    public Antwoord getAntwoord(String vraagId, String tentamenId) {
        return null;
    }

    @Override
    public void setAntwoord(String vraagId, String tentamenId, String gegevenAntwoord) {

    }

}
