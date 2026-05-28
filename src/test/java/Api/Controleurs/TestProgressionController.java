package Api.Controleurs;

import Integration.AbstractIntegrationTest;
import app.OwLearning.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestProgressionController extends AbstractIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void elevePeutRecupererSaProgression() throws Exception
    {
        int createurId = insererCreateur("progression-createur");
        int eleveId = insererEleve("progression-eleve");
        int coursId = insererCours(createurId, "Cours progression", true);
        insererProgression(coursId, eleveId, 42.5f);

        mockMvc.perform(get("/api/progression/" + coursId)
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tauxProgression").value(42.5));
    }

    @Test
    public void createurNePeutPasRecupererLaProgressionEleve() throws Exception
    {
        int createurId = insererCreateur("progression-interdit-createur");
        int coursId = insererCours(createurId, "Cours progression interdit", true);

        mockMvc.perform(get("/api/progression/" + coursId)
                        .with(authentication(authentification(createurId, "CREATEUR"))))
                .andExpect(status().isForbidden());
    }

    @Test
    public void progressionRefuseUnIdCoursInvalide() throws Exception
    {
        int eleveId = insererEleve("progression-bad-request-eleve");

        mockMvc.perform(get("/api/progression/0")
                        .with(authentication(authentification(eleveId, "ELEVE"))))
                .andExpect(status().isBadRequest());
    }
}
