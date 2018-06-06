package nl.han.toetsplatform.module.uitvoeren_tentamen.model.storage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VraagTest {

    private Vraag vraag;

    @Before
    public void setUp() {
        vraag = new Vraag();
    }

    @Test
    public void getPlugin() {
        vraag.setPlugin("han.plugin.graph");
        assertEquals(vraag.getPlugin(), "han.plugin.graph");
    }

    @Test
    public void setPlugin() {
        vraag.setPlugin("han.plugin.vrije.tekst");
        assertEquals(vraag.getPlugin(), "han.plugin.vrije.tekst");
    }

    @Test
    public void getName() {
        vraag.setName("Question 1");
        assertEquals(vraag.getName(), "Question 1");
    }

    @Test
    public void setName() {
        vraag.setName("Question 2");
        assertEquals(vraag.getName(), "Question 2");
    }

    @Test
    public void getDescription() {
        vraag.setDescription("Description 1");
        assertEquals(vraag.getDescription(), "Description 1");
    }

    @Test
    public void setDescription() {
        vraag.setDescription("Description 2");
        assertEquals(vraag.getDescription(), "Description 2");
    }

    @Test
    public void getVraagType() {
        vraag.setVraagType("Vraagtype 1");
        assertEquals(vraag.getVraagType(), "Vraagtype 1");
    }

    @Test
    public void setVraagType() {
        vraag.setVraagType("Vraagtype 2");
        assertEquals(vraag.getVraagType(), "Vraagtype 2");
    }

    @Test
    public void getData() {
        vraag.setData("Data 1");
        assertEquals(vraag.getData(), "Data 1");
    }

    @Test
    public void setData() {
        vraag.setData("Data 2");
        assertEquals(vraag.getData(), "Data 2");
    }

    @Test
    public void getId() {
        vraag.setId(1);
        assertEquals(vraag.getId(), 1);
    }

    @Test
    public void setId() {
        vraag.setId(2);
        assertEquals(vraag.getId(), 2);
    }
}