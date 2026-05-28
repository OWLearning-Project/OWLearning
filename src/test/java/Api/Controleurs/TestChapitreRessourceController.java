package Api.Controleurs;

import Integration.AbstractIntegrationTest;
import app.OwLearning.Api.DTO.request.UtilisateurAuthentifieRequest;
import app.OwLearning.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestChapitreRessourceController extends AbstractIntegrationTest
{
    private static final String CHAPITRE_JSON = """
            {
              "titre": "Titre modifie",
              "description": "Description modifiee"
            }
            """;

    private static final String RESSOURCE_JSON = """
            {
              "nom": "Support",
              "url": "https://test.com/support",
              "type": "VIDEO"
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void eleveNePeutPasModifierChapitre() throws Exception
    {
        mockMvc.perform(put("/api/chapitres/1")
                        .with(authentication(authentificationEleve()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CHAPITRE_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void eleveNePeutPasAjouterRessourceAChapitre() throws Exception
    {
        mockMvc.perform(post("/api/chapitres/1/ressources")
                        .with(authentication(authentificationEleve()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RESSOURCE_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void eleveNePeutPasRetirerRessourceDeChapitre() throws Exception
    {
        mockMvc.perform(delete("/api/chapitres/1/ressources/2")
                        .with(authentication(authentificationEleve()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void eleveNePeutPasSupprimerChapitre() throws Exception
    {
        mockMvc.perform(delete("/api/cours/1/chapitres/2")
                        .with(authentication(authentificationEleve()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void eleveNePeutPasCreerRessource() throws Exception
    {
        mockMvc.perform(post("/api/ressources")
                        .with(authentication(authentificationEleve()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RESSOURCE_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void eleveNePeutPasModifierRessource() throws Exception
    {
        mockMvc.perform(put("/api/ressources/1")
                        .with(authentication(authentificationEleve()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RESSOURCE_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void eleveNePeutPasSupprimerRessource() throws Exception
    {
        mockMvc.perform(delete("/api/ressources/1")
                        .with(authentication(authentificationEleve()))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    private UsernamePasswordAuthenticationToken authentificationEleve()
    {
        UtilisateurAuthentifieRequest principalMock = new UtilisateurAuthentifieRequest(
                1,
                "eleve@test.com",
                "ELEVE"
        );

        return new UsernamePasswordAuthenticationToken(
                principalMock,
                null,
                List.of(new SimpleGrantedAuthority("ELEVE"))
        );
    }
}

