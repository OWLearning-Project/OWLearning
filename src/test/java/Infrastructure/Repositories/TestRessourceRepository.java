package Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Domaine.Enumérations.TypeRessource;
import app.OwLearning.Infrastructure.Repositories.RessourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRessourceRepository extends AbstractRepositoryIntegrationTest
{
    @Autowired
    private RessourceRepository ressourceRepository;

    @Test
    public void trouveRessourceParId()
    {
        int ressourceId = insererRessource("Document infra", TypeRessource.FICHIER_PDF);

        Ressource parId = ressourceRepository.trouverParId(ressourceId);

        assertThat(parId).isNotNull();
        assertThat(parId.getId_ressource()).isEqualTo(ressourceId);
        assertThat(parId.getNom()).isEqualTo("Document infra");
        assertThat(parId.getType()).isEqualTo(TypeRessource.FICHIER_PDF);
    }

    @Test
    public void retourneNullQuandRessourceIntrouvableParId()
    {
        assertThat(ressourceRepository.trouverParId(999)).isNull();
    }

    @Test
    public void trouveRessourceParUrl()
    {
        int ressourceId = insererRessource("Document infra", TypeRessource.FICHIER_PDF);

        Ressource parUrl = ressourceRepository.findByUrl("https://test.local/ressource-" + ressourceId);

        assertThat(parUrl.getId_ressource()).isEqualTo(ressourceId);
    }

    @Test
    public void retourneNullQuandRessourceIntrouvableParUrl()
    {
        assertThat(ressourceRepository.findByUrl("https://test.local/introuvable")).isNull();
    }

    @Test
    public void sauvegardeRessource()
    {
        Ressource ressource = new Ressource(
                "Image TEST",
                TypeRessource.IMAGE,
                "https://test.local/image-test.png"
        );

        Ressource sauvegardee = ressourceRepository.sauvegarder(ressource);
        synchroniserPersistenceContext();

        assertThat(sauvegardee.getId_ressource()).isGreaterThan(0);
        assertThat(ressourceRepository.trouverParId(sauvegardee.getId_ressource()).getNom())
                .isEqualTo("Image TEST");
    }

    @Test
    public void metAJourRessource()
    {
        Ressource ressource = new Ressource(
                "Image TEST",
                TypeRessource.IMAGE,
                "https://test.local/image-test.png"
        );
        Ressource sauvegardee = ressourceRepository.sauvegarder(ressource);
        synchroniserPersistenceContext();
        sauvegardee.setNom("Video TEST");
        sauvegardee.setType(TypeRessource.VIDEO);
        sauvegardee.setUrl("https://test.local/video-test.mp4");

        ressourceRepository.sauvegarder(sauvegardee);
        synchroniserPersistenceContext();

        Ressource modifiee = ressourceRepository.trouverParId(sauvegardee.getId_ressource());
        assertThat(modifiee.getNom()).isEqualTo("Video TEST");
        assertThat(modifiee.getType()).isEqualTo(TypeRessource.VIDEO);
        assertThat(modifiee.getUrl()).isEqualTo("https://test.local/video-test.mp4");
    }

    @Test
    public void supprimeRessource()
    {
        int ressourceId = insererRessource("Ressource a supprimer", TypeRessource.IMAGE);
        ressourceRepository.supprimer(ressourceId);
        synchroniserPersistenceContext();

        assertThat(ressourceRepository.trouverParId(ressourceId)).isNull();
    }
}
