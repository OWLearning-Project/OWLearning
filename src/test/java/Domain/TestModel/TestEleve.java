package Domain.TestModel;

import app.OwLearning.Domain.Models.Eleve;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
 
public class TestEleve {

    @Test
    void creerEleve() {

        // Arrange
        String nom = "Bob";
        String prenom = "Martin";
        String email = "bobmartin@email.com";
        String motDePasse = "fauxmotdepasse";

        // Act
        Eleve eleve = new Eleve(nom, prenom, email, motDePasse);

        // Assert
        assertThat(eleve.getNom()).isEqualTo(nom);
        assertThat(eleve.getPrenom()).isEqualTo(prenom);
        assertThat(eleve.getEmail()).isEqualTo(email);
        assertThat(eleve.getMotDePasse()).isEqualTo(motDePasse);
        assertThat(eleve.getDateInscription()).isNotNull();
    }
}


