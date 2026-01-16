package Domain.TestModel;

import Domain.Models.Message;
import org.junit.jupiter.api.Test;
import Domain.Models.Ressource;
import Domain.Models.TypeRessource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestMessage {


    @Test
    public void testInitialisationMessageSansRessource(){

        //Arrange
        Message messageTest = new Message();

        //Assert
        assertNotNull(messageTest.getRessources());
        assertEquals(0, messageTest.getRessources().size());
    }

    @Test
    public void testAjouterRessource(){

        //Arrange
        Message messageTest = new Message();
        Ressource ressourceAjouter = new Ressource();

        //Act
        messageTest.ajouterRessource(ressourceAjouter);

        //Assert
        assertEquals(1, messageTest.getRessources().size());
        assertTrue(messageTest.getRessources().contains(ressourceAjouter));
    }

    @Test
    public void testAjouterPlusieursRessource(){

        //Arrange
        Message messageTest = new Message();
        Ressource r1 = new Ressource();
        Ressource r2 = new Ressource();

        //Act
        messageTest.ajouterRessource(r1);
        messageTest.ajouterRessource(r2);

        //Assert
        assertEquals(2, messageTest.getRessources().size());
        assertTrue(messageTest.getRessources().contains(r1));
        assertTrue(messageTest.getRessources().contains(r2));
    }

    @Test
    public void testRessourceNullDoitRetournerException(){

        //Arrange
        Message messageTest = new Message();

        //Act, Assert
        assertThrows(IllegalArgumentException.class, ()-> {
            messageTest.ajouterRessource(null);
        });
    }

    @Test
    public void testRetirerRessource(){

        //Arrange
        Message messageTest = new Message();
        TypeRessource unType = TypeRessource.VIDEO;
        Ressource ressourceSupprimer = new Ressource("Ressource tester", unType, "http://url.test.com");
        int idRessourceSupprimer = ressourceSupprimer.getId_ressource();
        messageTest.ajouterRessource(ressourceSupprimer);

        //Act
        Ressource ressourceRetirer = messageTest.retirerRessource(idRessourceSupprimer);

        //Assert
        assertEquals(idRessourceSupprimer, ressourceRetirer.getId_ressource());
        assertTrue(messageTest.getRessources().isEmpty());
    }

    @Test
    public void testRetirerRessourceInexistanteDoitRetournerException(){

        //Arrange
        Message messageTest = new Message();

        //Act, Assert
        assertThrows(NoSuchElementException.class, () -> {
            messageTest.retirerRessource(99);
        });
    }
}
