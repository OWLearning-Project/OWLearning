package Domaine.TestModel;

import app.OwLearning.Domaine.Entités.Eleve;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestEleve {

    @Test
    public void creerEleve() {
        String nom = "Bob";
        String prenom = "Martin";
        String email = "bobmartin@email.com";
        String motDePasse = "fauxmotdepasse";

        Eleve eleve = new Eleve(nom, prenom, email, motDePasse);

        assertThat(eleve.getNom()).isEqualTo(nom);
        assertThat(eleve.getPrenom()).isEqualTo(prenom);
        assertThat(eleve.getEmail()).isEqualTo(email);
        assertThat(eleve.getMotDePasseHash()).isEqualTo(motDePasse);
        assertThat(eleve.getDateInscription()).isNotNull();
    }
}
