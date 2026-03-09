package Domain.TestModel;

import app.OwLearning.Domain.Models.Eleve;
import app.OwLearning.Domain.Models.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
 
public class TestEleve {

    @Test
    public void creerEleve() {

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
        assertThat(eleve.getDateInscription()).isNotNull();
    }

}


