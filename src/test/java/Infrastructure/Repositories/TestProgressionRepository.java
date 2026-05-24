package Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Progression;
import app.OwLearning.Infrastructure.Repositories.ProgressionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TestProgressionRepository extends AbstractRepositoryIntegrationTest
{
    @Autowired
    private ProgressionRepository progressionRepository;

    @Test
    public void trouveProgressionParIdCompose()
    {
        int createurId = insererCreateur("progression-createur");
        int eleveId = insererEleve("progression-eleve");
        int coursId = insererCours(createurId, "Cours progression", true);

        jdbcTemplate.update(
                "INSERT INTO progression (id_cours, id_eleve, taux_progression) VALUES (?, ?, ?)",
                coursId,
                eleveId,
                42.5
        );

        Progression progression = progressionRepository.trouverParId(eleveId, coursId);

        assertThat(progression).isNotNull();
        assertThat(progression.getId().getIdCours()).isEqualTo(coursId);
        assertThat(progression.getId().getIdEleve()).isEqualTo(eleveId);
        assertThat(progression.getTauxProgression()).isEqualTo(42.5f);
    }

    @Test
    public void retourneNullQuandProgressionIntrouvable()
    {
        int createurId = insererCreateur("progression-absente-createur");
        int coursId = insererCours(createurId, "Cours progression absente", true);

        assertThat(progressionRepository.trouverParId(999_999, coursId)).isNull();
    }
}
