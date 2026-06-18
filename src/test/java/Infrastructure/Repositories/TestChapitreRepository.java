package Infrastructure.Repositories;

import Integration.AbstractIntegrationTest;

import app.OwLearning.Domaine.Entités.Chapitre;
import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Domaine.Enumérations.TypeRessource;
import app.OwLearning.Infrastructure.Repositories.ChapitreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class TestChapitreRepository extends AbstractIntegrationTest
{
    @Autowired
    private ChapitreRepository chapitreRepository;

    @Test
    public void trouveChapitreParIdAvecSesRessources()
    {
        int createurId = insererCreateur("chapitre-createur");
        int coursId = insererCours(createurId, "Cours chapitre", true);
        int ressourceId = insererRessource("Support chapitre", TypeRessource.FICHIER_PDF);
        int chapitreId = insererChapitre(coursId, "Chapitre SQL");

        jdbcTemplate.update(
                "INSERT INTO ressource_chapitre (id_chapitre, id_ressource) VALUES (?, ?)",
                chapitreId,
                ressourceId
        );

        Chapitre chapitre = chapitreRepository.trouverParId(chapitreId);

        assertThat(chapitre).isNotNull();
        assertThat(chapitre.getId()).isEqualTo(chapitreId);
        assertThat(chapitre.getTitre()).isEqualTo("Chapitre SQL");
        assertThat(chapitre.getRessources())
                .extracting(Ressource::getId_ressource)
                .containsExactly(ressourceId);
    }

    @Test
    public void retourneNullQuandChapitreIntrouvable()
    {
        assertThat(chapitreRepository.trouverParId(999)).isNull();
    }

    @Test
    public void creeChapitre()
    {
        Chapitre chapitre = new Chapitre("Chapitre TEST", "Description TEST", new ArrayList<>());

        int chapitreId = chapitreRepository.sauvegarder(chapitre);
        synchroniserPersistenceContext();

        Chapitre relu = chapitreRepository.trouverParId(chapitreId);
        assertThat(relu).isNotNull();
        assertThat(relu.getTitre()).isEqualTo("Chapitre TEST");
        assertThat(relu.getDescription()).isEqualTo("Description TEST");
    }

    @Test
    public void metAJourChapitre()
    {
        Chapitre chapitreCree = new Chapitre("Chapitre avant modification", "Description avant modification", new ArrayList<>());
        int chapitreId = chapitreRepository.sauvegarder(chapitreCree);
        synchroniserPersistenceContext();

        Chapitre chapitre = chapitreRepository.trouverParId(chapitreId);
        chapitre.setTitre("Chapitre modifie");
        chapitre.setDescription("Description modifiee");

        chapitreRepository.sauvegarder(chapitre);
        synchroniserPersistenceContext();

        Chapitre chapitreModifie = chapitreRepository.trouverParId(chapitreId);

        assertThat(chapitreModifie.getTitre()).isEqualTo("Chapitre modifie");
        assertThat(chapitreModifie.getDescription()).isEqualTo("Description modifiee");
    }

    @Test
    public void metAJourChapitreSansPerdreSonCours()
    {
        int createurId = insererCreateur("chapitre-update-createur");
        int coursId = insererCours(createurId, "Cours avec chapitre", true);
        int chapitreId = insererChapitre(coursId, "Chapitre lie");

        Chapitre chapitre = chapitreRepository.trouverParId(chapitreId);
        chapitre.setTitre("Chapitre lie modifie");

        chapitreRepository.sauvegarder(chapitre);
        synchroniserPersistenceContext();

        Integer coursAssocie = jdbcTemplate.queryForObject(
                "SELECT id_cours FROM chapitre WHERE id_chapitre = ?",
                Integer.class,
                chapitreId
        );

        assertThat(coursAssocie).isEqualTo(coursId);
    }

    @Test
    public void supprimeChapitre()
    {
        Chapitre chapitreCree = new Chapitre("Chapitre a supprimer", "Description a supprimer", new ArrayList<>());
        int chapitreId = chapitreRepository.sauvegarder(chapitreCree);
        synchroniserPersistenceContext();
        Chapitre chapitreSupprime = chapitreRepository.supprimerParId(chapitreId);
        synchroniserPersistenceContext();

        assertThat(chapitreSupprime.getId()).isEqualTo(chapitreId);
        assertThat(chapitreRepository.existe(chapitreId)).isFalse();
    }

    @Test
    public void retourneNullQuandSuppressionChapitreIntrouvable()
    {
        Chapitre chapitreSupprime = chapitreRepository.supprimerParId(999);

        assertThat(chapitreSupprime).isNull();
    }

    @Test
    public void retourneFalseQuandChapitreInexistant()
    {
        assertThat(chapitreRepository.existe(8)).isFalse();
    }
}
