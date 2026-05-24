package Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Chapitre;
import app.OwLearning.Domaine.Entités.ChapitreTermine;
import app.OwLearning.Infrastructure.Repositories.ChapitreRepository;
import app.OwLearning.Infrastructure.Repositories.ChapitreTermineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TestChapitreTermineRepository extends AbstractRepositoryIntegrationTest
{
    @Autowired
    private ChapitreTermineRepository chapitreTermineRepository;

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Test
    public void trouveChapitreTermineParIds()
    {
        int createurId = insererCreateur("termine-createur");
        int eleveId = insererEleve("termine-eleve");
        int coursId = insererCours(createurId, "Cours termine", true);
        int chapitreId = insererChapitre(coursId, "Chapitre termine");

        jdbcTemplate.update(
                "INSERT INTO chapitres_termines (id_chapitre, id_eleve) VALUES (?, ?)",
                chapitreId,
                eleveId
        );

        assertThat(chapitreTermineRepository.existe(chapitreId, eleveId)).isTrue();
    }

    @Test
    public void neTrouvePasChapitreTermineInexistant()
    {
        int createurId = insererCreateur("termine-absent-createur");
        int coursId = insererCours(createurId, "Cours termine absent", true);
        int chapitreId = insererChapitre(coursId, "Chapitre termine absent");

        assertThat(chapitreTermineRepository.existe(chapitreId, 8)).isFalse();
    }

    @Test
    public void sauvegardeChapitreTermine()
    {
        int createurId = insererCreateur("test-termine-createur");
        int eleveId = insererEleve("test-termine-eleve");
        int coursId = insererCours(createurId, "Cours TEST termine", true);
        int chapitreId = insererChapitre(coursId, "Chapitre TEST termine");
        Chapitre chapitre = chapitreRepository.trouverParId(chapitreId);

        ChapitreTermine chapitreTermine = new ChapitreTermine(chapitre, eleveId);
        chapitreTermineRepository.sauvegarder(chapitreTermine);
        synchroniserPersistenceContext();

        assertThat(chapitreTermineRepository.existe(chapitreId, eleveId)).isTrue();
    }
}
