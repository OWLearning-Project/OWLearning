package Api.Controleurs;

import Integration.AbstractIntegrationTest;
import app.OwLearning.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestAuthentificationController extends AbstractIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void inscriptionCreeUnEleve() throws Exception
    {
        String email = "inscription-" + System.nanoTime() + "@test.com";

        mockMvc.perform(post("/api/authentification/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "Test",
                                  "prenom": "Eleve",
                                  "email": "%s",
                                  "motDePasse": "Password123",
                                  "role": "eleve"
                                }
                """.formatted(email)))
                .andExpect(status().isCreated());

        synchroniserPersistenceContext();
        int nombreEleves = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM utilisateur u JOIN eleve e ON e.id_utilisateur = u.id_utilisateur WHERE u.email = ?",
                Integer.class,
                email
        );
        assertThat(nombreEleves).isEqualTo(1);
    }

    @Test
    public void connexionRetourneUnTokenEtPermetLaDeconnexion() throws Exception
    {
        String email = "connexion-" + System.nanoTime() + "@test.com";
        String motDePasse = "Password123";

        mockMvc.perform(post("/api/authentification/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "Test",
                                  "prenom": "Createur",
                                  "email": "%s",
                                  "motDePasse": "%s",
                                  "role": "createur"
                                }
                """.formatted(email, motDePasse)))
                .andExpect(status().isCreated());

        synchroniserPersistenceContext();
        MvcResult connexion = mockMvc.perform(post("/api/authentification/connexion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "motDePasse": "%s"
                                }
                                """.formatted(email, motDePasse)))
                .andExpect(status().isOk())
                .andReturn();

        String token = connexion.getResponse().getContentAsString();
        assertThat(token).isNotBlank().contains(".");

        mockMvc.perform(post("/api/authentification/deconnexion")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }
}