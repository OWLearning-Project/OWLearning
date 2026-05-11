package Domain.TestModel;

import app.OwLearning.Domain.Models.Createur;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCreateur {

    @Test
    void creerCreateur() {
        String nom = "Dupont";
        String prenom = "Maxime";
        String email = "Dupont@email.com";
        String motDePasse = "hash123";

        Createur createur = new Createur(nom, prenom, email, motDePasse);

        assertThat(createur.getNom()).isEqualTo(nom);
        assertThat(createur.getPrenom()).isEqualTo(prenom);
        assertThat(createur.getEmail()).isEqualTo(email);
        assertThat(createur.getMotDePasseHash()).isEqualTo(motDePasse);
        assertThat(createur.getDateInscription()).isNotNull();
    }

    @Test
    void creerCreateur_ConstructeurVide() {
        Createur createur = new Createur();

        assertThat(createur.getNom()).isNull();
        assertThat(createur.getPrenom()).isNull();
        assertThat(createur.getEmail()).isNull();
        assertThat(createur.getMotDePasseHash()).isNull();
        assertThat(createur.getDateInscription()).isNull();
    }

    @Test
    void creerCreateur_ChampsVidesAcceptes() {
        String nom = "";
        String prenom = "";
        String email = "";
        String motDePasse = "";

        Createur createur = new Createur(nom, prenom, email, motDePasse);

        assertThat(createur.getNom()).isEmpty();
        assertThat(createur.getPrenom()).isEmpty();
        assertThat(createur.getEmail()).isEmpty();
        assertThat(createur.getMotDePasseHash()).isEmpty();
        assertThat(createur.getDateInscription()).isNotNull();
    }
}
