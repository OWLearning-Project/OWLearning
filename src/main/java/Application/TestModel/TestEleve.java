package Application.TestModel;

import Domain.Models.*;
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
        int age = 23;
        String niveauEtude = "BUT3";

        // Act
        Eleve eleve = new Eleve(nom, prenom, email, motDePasse, age, niveauEtude);

        // Assert
        assertThat(eleve.getNom()).isEqualTo(nom);
        assertThat(eleve.getPrenom()).isEqualTo(prenom);
        assertThat(eleve.getEmail()).isEqualTo(email);
        assertThat(eleve.getMotDePasse()).isEqualTo(motDePasse);
        assertThat(eleve.getAge()).isEqualTo(age);
        assertThat(eleve.getNiveauEtude()).isEqualTo(niveauEtude);
        assertThat(eleve.getDateInscription()).isNotNull();
    }

    @Test
    void creerEleve_AgeInvalide() {

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
    void creerEleve_NiveauVide() {

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


