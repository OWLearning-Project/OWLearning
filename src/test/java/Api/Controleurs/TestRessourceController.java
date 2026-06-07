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
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestRessourceController extends AbstractIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRessourceRetourneLaRessource() throws Exception
    {
        int createurId = insererCreateur("ressource-get-createur");
        int ressourceId = insererRessource("Ressource get", TypeRessource.IMAGE);

        mockMvc.perform(get("/api/ressources/" + ressourceId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ressourceId))
                .andExpect(jsonPath("$.nom").value("Ressource get"))
                .andExpect(jsonPath("$.type").value("IMAGE"));
    }

    @Test
    public void createurPeutCreerModifierEtSupprimerUneRessource() throws Exception
    {
        int createurId = insererCreateur("ressource-crud-createur");

        mockMvc.perform(post("/api/ressources")
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "Ressource creee",
                                  "url": "https://test.com/ressource-creee",
                                  "type": "VIDEO"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Ressource creee"));

        synchroniserPersistenceContext();
        int ressourceId = jdbcTemplate.queryForObject(
                "SELECT id_ressource FROM ressource WHERE nom = ?",
                Integer.class,
                "Ressource creee"
        );

        mockMvc.perform(put("/api/ressources/" + ressourceId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "Ressource modifiee",
                                  "url": "https://test.com/ressource-modifiee",
                                  "type": "FICHIER_ZIP"
                                }
                                """))
                .andExpect(status().isOk());

        synchroniserPersistenceContext();
        assertThat(jdbcTemplate.queryForObject("SELECT nom FROM ressource WHERE id_ressource = ?", String.class, ressourceId))
                .isEqualTo("Ressource modifiee");

        mockMvc.perform(delete("/api/ressources/" + ressourceId)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ressourceId));

        synchroniserPersistenceContext();
        Integer ressourcesRestantes = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ressource WHERE id_ressource = ?", Integer.class, ressourceId);
        assertThat(ressourcesRestantes).isZero();
    }

    @Test
    public void createurPeutUploaderUneRessourceMultipart() throws Exception
    {
        int createurId = insererCreateur("ressource-upload-createur");
        MockMultipartFile fichier = new MockMultipartFile(
                "fichier",
                "support.pdf",
                "application/pdf",
                "Contenu PDF".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/api/ressources/upload")
                        .file(fichier)
                        .with(authentication(authentification(createurId, "CREATEUR")))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("support.pdf"))
                .andExpect(jsonPath("$.type").value("FICHIER_PDF"))
                .andExpect(jsonPath("$.url").value(containsString("/uploads/ressources/")));

        synchroniserPersistenceContext();
        Integer ressources = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ressource WHERE nom = ? AND type_ressource = ?",
                Integer.class,
                "support.pdf",
                "FICHIER_PDF"
        );
        assertThat(ressources).isEqualTo(1);
    }
}
