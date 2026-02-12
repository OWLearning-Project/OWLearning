package Domain.TestModel;

import app.OwLearning.Domain.Models.Createur;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class TestCreateur {

    @Test
    void creerCreateur() {

        // Arrange
        String nom = "Dupont";
        String prenom = "Maxime";
        String email = "Dupont@email.com";
        String motDePasse = "hash123";

        // Act
        Createur createur = new Createur(nom, prenom, email, motDePasse);

        // Assert
        assertThat(createur.getNom()).isEqualTo(nom);
        assertThat(createur.getPrenom()).isEqualTo(prenom);
        assertThat(createur.getEmail()).isEqualTo(email);
        assertThat(createur.getMotDePasse()).isEqualTo(motDePasse);
        assertThat(createur.getDateInscription()).isNotNull();
    }

    @Test
    void creerCreateur_ConstructeurVide() {

        // Act
        Createur createur = new Createur();

        // Assert
        assertThat(createur.getNom()).isNull();
        assertThat(createur.getPrenom()).isNull();
        assertThat(createur.getEmail()).isNull();
        assertThat(createur.getMotDePasse()).isNull();
        assertThat(createur.getDateInscription()).isNull();
    }

    @Test
    void creerCreateur_ChampsVidesAcceptes() {

        // Arrange
        String nom = "";
        String prenom = "";
        String email = "";
        String motDePasse = "";

        // Act
        Createur createur = new Createur(nom, prenom, email, motDePasse);

        // Assert
        assertThat(createur.getNom()).isEmpty();
        assertThat(createur.getPrenom()).isEmpty();
        assertThat(createur.getEmail()).isEmpty();
        assertThat(createur.getMotDePasse()).isEmpty();
        assertThat(createur.getDateInscription()).isNotNull();
    }
}

