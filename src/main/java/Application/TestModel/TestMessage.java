package Application.TestModel;

import Domain.Models.Message;
import Domain.Models.Ressource;
import Domain.Models.TypeRessource;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestMessage {

    @Test
    public void testAjouterRessource(){

        //Arrange
        Message testMessage = new Message();
        Ressource ressourceAjouter = new Ressource();

        //Act
        testMessage.ajouterRessource(ressourceAjouter);

        //Assert
        assertEquals(1, testMessage.getRessources().size());
        assertTrue(testMessage.getRessources().contains(ressourceAjouter));
    }

    @Test
    public void testRetirerRessource(){

        //Arrange
        Message testMessage = new Message();
        TypeRessource unType = new TypeRessource();
        Ressource ressourceSupprimer = new Ressource("Ressource tester", unType, "http://url.test.com");
        int idRessourceSupprimer = ressourceSupprimer.getId_ressource();
        testMessage.ajouterRessource(ressourceSupprimer);

        //Act
        Ressource ressourceRetirer = testMessage.retirerRessource(idRessourceSupprimer);

        //Assert
        assertEquals(idRessourceSupprimer, ressourceRetirer.getId_ressource());
        assertTrue(testMessage.getRessources().isEmpty());
    }
}
