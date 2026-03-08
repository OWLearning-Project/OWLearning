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
        int age = 23;
        String niveauEtude = "BUT3";

        // Assert
        assertThat(eleve.getNom()).isEqualTo(nom);
        assertThat(eleve.getPrenom()).isEqualTo(prenom);
        assertThat(eleve.getEmail()).isEqualTo(email);
        assertThat(eleve.getMotDePasse()).isEqualTo(motDePasse);
        assertThat(eleve.getDateInscription()).isNotNull();
        assertThat(eleve.getAge()).isEqualTo(age);
        assertThat(eleve.getNiveauEtude()).isEqualTo(niveauEtude);
        assertThat(eleve.getDateInscription()).isNotNull();
    }

    @Test
    public void creerEleveAgeInvalide() {

        // Arrange
        String nom = "Bob";
        String prenom = "Martin";
        String email = "bobmartin@email.com";
        String motDePasse = "fauxmotdepasse";
        int ageInvalide = -1;
        String niveauEtude = "BUT3";

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Eleve(nom, prenom, email, motDePasse, ageInvalide, niveauEtude)
        );
    }

    @Test
    public void creerEleveNiveauVide() {

        // Arrange
        String nom = "Bob";
        String prenom = "Martin";
        String email = "bobmartin@email.com";
        String motDePasse = "fauxmotdepasse";
        int age = 23;
        String niveauVide = "";

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Eleve(nom, prenom, email, motDePasse, age, niveauVide)
        );
    }
}


