package Api.Controleurs;

import Integration.AbstractIntegrationTest;
import app.OwLearning.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestCoursController extends AbstractIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCoursPubliesRetourneSeulementLesCoursPublies() throws Exception
    {
        int createurId = insererCreateur("cours-publies-createur");
        int coursPublieId = insererCours(createurId, "Cours publie", true);
        insererCours(createurId, "Cours brouillon", false);
        int eleveId = insererEleve("cours-publies-eleve");

        mockMvc.perform(get("/api/cours")
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(coursPublieId))
                .andExpect(jsonPath("$[0].titre").value("Cours publie"));
    }

    @Test
    public void getCoursCreesRetourneLesCoursDuCreateurConnecte() throws Exception
    {
        int createurId = insererCreateur("cours-crees-createur");
        int autreCreateurId = insererCreateur("cours-crees-autre");
        int coursId = insererCours(createurId, "Mon cours", false);
        insererCours(autreCreateurId, "Cours autre", false);

        mockMvc.perform(get("/api/cours/utilisateurs/publications")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(coursId));
    }

    @Test
    public void getCoursInscriptionsRetourneLesCoursDeLEleveConnecte() throws Exception
    {
        int createurId = insererCreateur("cours-inscriptions-createur");
        int eleveId = insererEleve("cours-inscriptions-eleve");
        int coursId = insererCours(createurId, "Cours inscrit", true);
        inscrireEleveAuCours(eleveId, coursId);

        mockMvc.perform(get("/api/cours/utilisateurs/inscriptions")
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(coursId));
    }

    @Test
    public void getCoursParIdRetourneLeCours() throws Exception
    {
        int createurId = insererCreateur("cours-detail-createur");
        int coursId = insererCours(createurId, "Cours detail", true);
        int eleveId = insererEleve("cours-detail-eleve");

        mockMvc.perform(get("/api/cours/" + coursId)
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(coursId))
                .andExpect(jsonPath("$.titre").value("Cours detail"));
    }

    @Test
    public void createurPeutRecupererSonCoursParId() throws Exception
    {
        int createurId = insererCreateur("cours-detail-proprietaire");
        int coursId = insererCours(createurId, "Cours proprietaire", true);

        mockMvc.perform(get("/api/cours/" + coursId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(coursId))
                .andExpect(jsonPath("$.titre").value("Cours proprietaire"));
    }

    @Test
    public void createurNePeutPasRecupererLeCoursDUnAutreCreateur() throws Exception
    {
        int createurId = insererCreateur("cours-detail-createur-refuse");
        int autreCreateurId = insererCreateur("cours-detail-autre-createur");
        int coursId = insererCours(autreCreateurId, "Cours autre createur", true);

        mockMvc.perform(get("/api/cours/" + coursId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createurPeutCreerPublierModifierEtSupprimerUnCours() throws Exception
    {
        int createurId = insererCreateur("cours-crud-createur");

        mockMvc.perform(post("/api/cours")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titre": "Cours cree",
                                  "description": "Description creee",
                                  "difficulte": "DEBUTANT"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titre").value("Cours cree"));

        synchroniserPersistenceContext();
        int coursId = jdbcTemplate.queryForObject(
                "SELECT id_cours FROM cours WHERE titre = ? AND id_createur = ?",
                Integer.class,
                "Cours cree",
                createurId
        );

        mockMvc.perform(post("/api/cours/" + coursId + "/publier")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/cours/" + coursId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titre": "Cours modifie",
                                  "description": "Description modifiee",
                                  "difficulte": "INTERMEDIAIRE",
                                  "estPrive": true
                                }
                                """))
                .andExpect(status().isOk());

        synchroniserPersistenceContext();
        assertThat(jdbcTemplate.queryForObject("SELECT est_publie FROM cours WHERE id_cours = ?", Boolean.class, coursId)).isTrue();
        assertThat(jdbcTemplate.queryForObject("SELECT titre FROM cours WHERE id_cours = ?", String.class, coursId)).isEqualTo("Cours modifie");

        mockMvc.perform(delete("/api/cours/" + coursId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf()))
                .andExpect(status().isAccepted());

        synchroniserPersistenceContext();
        Integer coursRestants = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cours WHERE id_cours = ?", Integer.class, coursId);
        assertThat(coursRestants).isZero();
    }

    @Test
    public void createurPeutAjouterEtRetirerUnChapitre() throws Exception
    {
        int createurId = insererCreateur("cours-chapitre-createur");
        int coursId = insererCours(createurId, "Cours chapitres", false);

        mockMvc.perform(post("/api/cours/" + coursId + "/chapitres")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titre": "Chapitre cree",
                                  "description": "Description chapitre"
                                }
                                """))
                .andExpect(status().isCreated());

        synchroniserPersistenceContext();
        int chapitreId = jdbcTemplate.queryForObject(
                "SELECT id_chapitre FROM chapitre WHERE id_cours = ? AND titre = ?",
                Integer.class,
                coursId,
                "Chapitre cree"
        );

        mockMvc.perform(delete("/api/cours/" + coursId + "/chapitres/" + chapitreId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf()))
                .andExpect(status().isAccepted());

        synchroniserPersistenceContext();
        Integer chapitresRestants = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM chapitre WHERE id_chapitre = ?", Integer.class, chapitreId);
        assertThat(chapitresRestants).isZero();
    }

    @Test
    public void createurPeutAjouterEtSupprimerUneCategorie() throws Exception
    {
        int createurId = insererCreateur("cours-categorie-createur");
        int coursId = insererCours(createurId, "Cours categories", false);

        mockMvc.perform(post("/api/cours/" + coursId + "/categories")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Architecture\""))
                .andExpect(status().isAccepted());

        synchroniserPersistenceContext();
        Integer categories = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM categorie_cours WHERE id_cours = ? AND categorie = ?",
                Integer.class,
                coursId,
                "ARCHITECTURE"
        );
        assertThat(categories).isEqualTo(1);

        mockMvc.perform(delete("/api/cours/" + coursId + "/categories/Architecture")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf()))
                .andExpect(status().isAccepted());

        synchroniserPersistenceContext();
        Integer categoriesRestantes = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM categorie_cours WHERE id_cours = ? AND categorie = ?",
                Integer.class,
                coursId,
                "ARCHITECTURE"
        );
        assertThat(categoriesRestantes).isZero();
    }
}
