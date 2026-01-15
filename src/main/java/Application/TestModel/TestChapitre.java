package Application.TestModel;

import Domain.Models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class TestChapitre {
    private Chapitre chapitre;

    @BeforeEach
    void setUp(){
        chapitre = new Chapitre();
    }

    @Test
    void ajouterRessourceALaListe(){
        // Arrange
        Ressource ressource = new Ressource();
        ressource.setNom("Vidéo Test");

        // Act
        chapitre.ajouterRessource(ressource);

        // Assert
        assertThat(chapitre.getRessources()).hasSize(1);
        assertThat(chapitre.getRessources()).contains(ressource);
    }

    @Test
    void supprimerRessourceExistante(){
        // Arrange
        Ressource ressource = new Ressource();
        chapitre.ajouterRessource(ressource);

        // (Pre)Assert
        assertThat(chapitre.getRessources()).hasSize(1);

        // Act
        Ressource resultat = chapitre.supprimerRessource(ressource);

        // Assert
        assertThat(resultat).isEqualTo(ressource);
        assertTrue(chapitre.getRessources().isEmpty());
    }

    @Test
    void supprimerRessourceNonExistante(){
        // Arrange
        Ressource ressourceInconnue = new Ressource();

        // Act
        Ressource resultat = chapitre.supprimerRessource(ressourceInconnue);

        // Assert
        assertThat(resultat).isNull();
    }
}
