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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestInscriptionController extends AbstractIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void elevePeutSInscrireAUnCours() throws Exception
    {
        int createurId = insererCreateur("inscription-createur");
        int eleveId = insererEleve("inscription-eleve");
        int coursId = insererCours(createurId, "Cours inscription", true);

        mockMvc.perform(post("/api/inscription/etudiants/cours/" + coursId)
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(coursId));

        synchroniserPersistenceContext();
        Integer inscriptions = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM inscription WHERE id_cours = ? AND id_eleve = ?",
                Integer.class,
                coursId,
                eleveId
        );
        assertThat(inscriptions).isEqualTo(1);
    }

    @Test
    public void getEtudiantsInscritsRetourneLesElevesDuCours() throws Exception
    {
        int createurId = insererCreateur("inscription-liste-createur");
        int eleveId = insererEleve("inscription-liste-eleve");
        int coursId = insererCours(createurId, "Cours liste inscrits", true);
        inscrireEleveAuCours(eleveId, coursId);

        mockMvc.perform(get("/api/inscription/cours/" + coursId + "/etudiants")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(eleveId))
                .andExpect(jsonPath("$[0].role").value("ELEVE"));
    }

    @Test
    public void createurPeutRefuserUneInscription() throws Exception
    {
        int createurId = insererCreateur("inscription-refus-createur");
        int eleveId = insererEleve("inscription-refus-eleve");
        int coursId = insererCours(createurId, "Cours refus", true);
        inscrireEleveAuCours(eleveId, coursId);

        mockMvc.perform(post("/api/inscription/cours/" + coursId + "/etudiants/" + eleveId + "/refus")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf()))
                .andExpect(status().isOk());

        synchroniserPersistenceContext();
        Integer inscriptions = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM inscription WHERE id_cours = ? AND id_eleve = ?",
                Integer.class,
                coursId,
                eleveId
        );
        assertThat(inscriptions).isZero();
    }

    @Test
    public void eleveNePeutPasRefuserUneInscription() throws Exception
    {
        int createurId = insererCreateur("inscription-interdit-createur");
        int eleveId = insererEleve("inscription-interdit-eleve");
        int coursId = insererCours(createurId, "Cours refus interdit", true);

        mockMvc.perform(post("/api/inscription/cours/" + coursId + "/etudiants/" + eleveId + "/refus")
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
