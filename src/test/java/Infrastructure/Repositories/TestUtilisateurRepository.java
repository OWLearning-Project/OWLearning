package Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Infrastructure.Repositories.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilisateurRepository extends AbstractRepositoryIntegrationTest
{
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    public void trouveUtilisateurParIdDansH2()
    {
        int utilisateurId = insererUtilisateur("utilisateur");

        Utilisateur utilisateur = utilisateurRepository.trouverParId(utilisateurId);

        assertThat(utilisateur).isNotNull();
        assertThat(utilisateur.getIdUtilisateur()).isEqualTo(utilisateurId);
        assertThat(utilisateur.getEmail()).contains("utilisateur");
    }

    @Test
    public void retourneNullQuandUtilisateurIntrouvable()
    {
        assertThat(utilisateurRepository.trouverParId(3)).isNull();
    }

    @Test
    public void sauvegardeUtilisateur()
    {
        String email = "test-utilisateur-" + System.nanoTime() + "@test.local";
        Utilisateur utilisateur = new Utilisateur("Nom", "Prenom", email, "hash");
        utilisateur.setPseudo("pseudo-test");

        Utilisateur sauvegarde = utilisateurRepository.sauvegarder(utilisateur);
        synchroniserPersistenceContext();

        assertThat(sauvegarde.getIdUtilisateur()).isGreaterThan(0);
        assertThat(utilisateurRepository.trouverParEmail(email).getIdUtilisateur())
                .isEqualTo(sauvegarde.getIdUtilisateur());
    }

    @Test
    public void metAJourUtilisateur()
    {
        String email = "test-utilisateur-" + System.nanoTime() + "@test.local";
        Utilisateur utilisateur = new Utilisateur("Nom", "Prenom", email, "hash");
        utilisateur.setPseudo("pseudo-test");
        Utilisateur sauvegarde = utilisateurRepository.sauvegarder(utilisateur);
        synchroniserPersistenceContext();
        String nouvelEmail = "test-utilisateur-maj-" + System.nanoTime() + "@test.local";

        sauvegarde.setPseudo("pseudo-maj");
        sauvegarde.setEmail(nouvelEmail);

        utilisateurRepository.sauvegarder(sauvegarde);
        synchroniserPersistenceContext();

        Utilisateur relu = utilisateurRepository.trouverParId(sauvegarde.getIdUtilisateur());
        assertThat(relu.getPseudo()).isEqualTo("pseudo-maj");
        assertThat(relu.getEmail()).isEqualTo(nouvelEmail);
    }
}
