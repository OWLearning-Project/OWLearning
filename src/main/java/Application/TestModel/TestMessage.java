package Application.TestModel;

import Domain.Models.Message;
import Domain.Models.Ressource;

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
        Ressource ressourceSupprimer = new Ressource();
        ressourceSupprimer.setId_ressource(1);
        testMessage.ajouterRessource(ressourceSupprimer);

        //Act
        Ressource ressourceRetirer = testMessage.retirerRessource(1);

        //Assert
        assertEquals(1, ressourceRetirer.getId_ressource());
        assertTrue(testMessage.getRessources().isEmpty());
    }

}
