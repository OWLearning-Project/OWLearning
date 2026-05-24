package Infrastructure.Repositories;

import app.OwLearning.Domaine.Entités.Createur;
import app.OwLearning.Domaine.Entités.Cours;
import app.OwLearning.Domaine.Enumérations.Difficulte;
import app.OwLearning.Domaine.Exceptions.ExceptionCoursInexistant;
import app.OwLearning.Infrastructure.Repositories.CoursRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TestCoursRepository extends AbstractRepositoryIntegrationTest
{
    @Autowired
    private CoursRepository coursRepository;

    @Test
    public void trouveCoursParIdDansH2()
    {
        int createurId = insererCreateur("cours-createur");
        int coursId = insererCours(createurId, "SQL infra", true);

        Cours cours = coursRepository.trouverParId(coursId);

        assertThat(cours).isNotNull();
        assertThat(cours.getId()).isEqualTo(coursId);
        assertThat(cours.getTitre()).isEqualTo("SQL infra");
        assertThat(cours.getDifficulte()).isEqualTo(Difficulte.DEBUTANT);
        assertThat(cours.getCreateur().getIdUtilisateur()).isEqualTo(createurId);
    }

    @Test
    public void leveExceptionQuandCoursIntrouvable()
    {
        assertThatThrownBy(() -> coursRepository.trouverParId(11))
                .isInstanceOf(ExceptionCoursInexistant.class);
    }

    @Test
    public void trouveCoursParIdCreateur()
    {
        int createurId = insererCreateur("cours-filtres");
        int autreCreateurId = insererCreateur("autre-cours-filtres");

        int coursCibleId = insererCours(createurId, "Cours cible", true);
        int autreCoursId = insererCours(autreCreateurId, "Cours autre createur", true);

        List<Cours> coursDuCreateur = coursRepository.trouverParIdCreateur(createurId);

        assertThat(coursDuCreateur)
                .extracting(Cours::getId)
                .containsExactly(coursCibleId);
        assertThat(coursDuCreateur)
                .extracting(Cours::getId)
                .doesNotContain(autreCoursId);
    }

    @Test
    public void trouveCoursParIdEleve()
    {
        int createurId = insererCreateur("cours-eleve-createur");
        int eleveId = insererEleve("eleve-inscrit");
        int coursCibleId = insererCours(createurId, "Cours inscrit", true);
        insererCours(createurId, "Cours non inscrit", true);

        jdbcTemplate.update("INSERT INTO inscription (id_cours, id_eleve) VALUES (?, ?)", coursCibleId, eleveId);

        List<Cours> coursDeLEleve = coursRepository.trouverParIdEleve(eleveId);

        assertThat(coursDeLEleve)
                .extracting(Cours::getId)
                .containsExactly(coursCibleId);
    }

    @Test
    public void sauvegardeCours()
    {
        int createurId = insererCreateur("test-cours-createur");
        Createur createur = new Createur();
        createur.setIdUtilisateur(createurId);
        Cours nouveauCours = new Cours(
                "Cours TEST",
                "Description TEST",
                false,
                new ArrayList<>(),
                Difficulte.INTERMEDIAIRE,
                createur
        );

        coursRepository.sauvegarder(nouveauCours);
        synchroniserPersistenceContext();

        Integer coursId = jdbcTemplate.queryForObject(
                "SELECT id_cours FROM cours WHERE titre = ?",
                Integer.class,
                "Cours TEST"
        );
        assertThat(coursId).isNotNull();
        assertThat(coursRepository.coursExiste(coursId)).isTrue();
    }

    @Test
    public void metAJourCours()
    {
        int createurId = insererCreateur("maj-cours-createur");
        int coursId = insererCours(createurId, "Cours avant modification", true);
        Cours coursCree = coursRepository.trouverParId(coursId);
        coursCree.setTitre("Cours TEST modifie");
        coursCree.setDescription("Description modifiee");
        coursCree.setDifficulte(Difficulte.AVANCE);
        coursCree.setEstPrive(true);
        coursRepository.sauvegarder(coursCree);
        synchroniserPersistenceContext();

        Cours coursModifie = coursRepository.trouverParId(coursCree.getId());
        assertThat(coursModifie.getTitre()).isEqualTo("Cours TEST modifie");
        assertThat(coursModifie.getDescription()).isEqualTo("Description modifiee");
        assertThat(coursModifie.getDifficulte()).isEqualTo(Difficulte.AVANCE);
        assertThat(coursModifie.isEstPrive()).isTrue();
    }

    @Test
    public void supprimeCours()
    {
        int createurId = insererCreateur("suppression-cours-createur");
        int coursId = insererCours(createurId, "Cours a supprimer", true);
        Cours coursSupprime = coursRepository.supprimerCours(coursId);
        synchroniserPersistenceContext();

        assertThat(coursSupprime.getId()).isEqualTo(coursId);
        assertThat(coursRepository.coursExiste(coursId)).isFalse();
        assertThatThrownBy(() -> coursRepository.trouverParId(coursId))
                .isInstanceOf(ExceptionCoursInexistant.class);
    }
}
