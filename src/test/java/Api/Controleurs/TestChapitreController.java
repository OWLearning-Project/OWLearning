package Api.Controleurs;

import Integration.AbstractIntegrationTest;
import app.OwLearning.Domaine.Enumérations.TypeRessource;
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
public class TestChapitreController extends AbstractIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getChapitreRetourneSesRessources() throws Exception
    {
        int createurId = insererCreateur("chapitre-get-createur");
        int coursId = insererCours(createurId, "Cours chapitre get", true);
        int chapitreId = insererChapitre(coursId, "Chapitre lu");
        int ressourceId = insererRessource("Ressource lue", TypeRessource.VIDEO);
        lierRessourceAChapitre(chapitreId, ressourceId);
        int eleveId = insererEleve("chapitre-get-eleve");

        mockMvc.perform(get("/api/chapitres/" + chapitreId)
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(chapitreId))
                .andExpect(jsonPath("$.titre").value("Chapitre lu"))
                .andExpect(jsonPath("$.ressources[0].id").value(ressourceId));
    }

    @Test
    public void createurPeutModifierUnChapitre() throws Exception
    {
        int createurId = insererCreateur("chapitre-modifier-createur");
        int coursId = insererCours(createurId, "Cours chapitre modifier", false);
        int chapitreId = insererChapitre(coursId, "Ancien titre");

        mockMvc.perform(put("/api/chapitres/" + chapitreId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "titre": "Nouveau titre",
                                  "description": "Nouvelle description"
                                }
                                """))
                .andExpect(status().isNoContent());

        synchroniserPersistenceContext();
        String titre = jdbcTemplate.queryForObject("SELECT titre FROM chapitre WHERE id_chapitre = ?", String.class, chapitreId);
        assertThat(titre).isEqualTo("Nouveau titre");
    }

    @Test
    public void createurPeutAjouterEtRetirerUneRessource() throws Exception
    {
        int createurId = insererCreateur("chapitre-ressource-createur");
        int coursId = insererCours(createurId, "Cours chapitre ressource", false);
        int chapitreId = insererChapitre(coursId, "Chapitre ressources");

        mockMvc.perform(post("/api/chapitres/" + chapitreId + "/ressources")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "Support chapitre",
                                  "url": "https://test.com/support-chapitre",
                                  "type": "FICHIER_PDF"
                                }
                                """))
                .andExpect(status().isCreated());

        synchroniserPersistenceContext();
        int ressourceId = jdbcTemplate.queryForObject(
                "SELECT r.id_ressource FROM ressource r JOIN ressource_chapitre rc ON rc.id_ressource = r.id_ressource WHERE rc.id_chapitre = ? AND r.nom = ?",
                Integer.class,
                chapitreId,
                "Support chapitre"
        );

        mockMvc.perform(delete("/api/chapitres/" + chapitreId + "/ressources/" + ressourceId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ressourceId));

        synchroniserPersistenceContext();
        Integer liensRestants = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ressource_chapitre WHERE id_chapitre = ? AND id_ressource = ?",
                Integer.class,
                chapitreId,
                ressourceId
        );
        assertThat(liensRestants).isZero();
    }

    @Test
    public void elevePeutTerminerUnChapitre() throws Exception
    {
        int createurId = insererCreateur("chapitre-termine-createur");
        int eleveId = insererEleve("chapitre-termine-eleve");
        int coursId = insererCours(createurId, "Cours termine", true);
        int chapitreId = insererChapitre(coursId, "Chapitre termine");

        mockMvc.perform(post("/api/chapitres/" + chapitreId + "/terminer")
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .with(csrf()))
                .andExpect(status().isOk());

        synchroniserPersistenceContext();
        Integer chapitresTermines = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM chapitres_termines WHERE id_chapitre = ? AND id_eleve = ?",
                Integer.class,
                chapitreId,
                eleveId
        );
        assertThat(chapitresTermines).isEqualTo(1);
    }
}
