package Api.Controleurs;

import Infrastructure.Repositories.AbstractRepositoryIntegrationTest;
import app.OwLearning.Api.DTO.request.UtilisateurAuthentifieRequest;
import app.OwLearning.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestUtilisateurController extends AbstractRepositoryIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProfil_RetourneUtilisateurCreateur() throws Exception
    {
        int createurId = insererCreateur("createur-test");

        String emailGenere = jdbcTemplate.queryForObject(
                "SELECT email FROM utilisateur WHERE id_utilisateur = ?",
                String.class,
                createurId
        );

        UtilisateurAuthentifieRequest principalMock = new UtilisateurAuthentifieRequest(createurId, emailGenere, "CREATEUR");

        UsernamePasswordAuthenticationToken authPrincipal = new UsernamePasswordAuthenticationToken(
                principalMock,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        mockMvc.perform(get("/api/utilisateurs/" + createurId)
                .with(authentication(authPrincipal))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createurId))
                .andExpect(jsonPath("$.email").value(emailGenere))
                .andExpect(jsonPath("$.role").value("CREATEUR"));
    }

    @Test
    public void modifierProfil() throws Exception
    {
        int utilisateurId = insererEleve("edit-test");

        String emailInitial = jdbcTemplate.queryForObject(
                "SELECT email FROM utilisateur WHERE id_utilisateur = ?",
                String.class,
                utilisateurId
        );

        UtilisateurAuthentifieRequest principalMock = new UtilisateurAuthentifieRequest(utilisateurId, emailInitial, "USER");
        UsernamePasswordAuthenticationToken authPrincipal = new UsernamePasswordAuthenticationToken(
                principalMock,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        mockMvc.perform(put("/api/utilisateurs/edit_profil")
                        .with(authentication(authPrincipal))
                        .with(csrf())
                        .param("pseudo", "NouveauPseudo")
                        .param("email", "nouveau@email.com")
                        .param("age", "24")
                        .param("niveauEtude", "Master 1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(utilisateurId))
                .andExpect(jsonPath("$.email").value("nouveau@email.com"));


        Map<String, Object> dataEnBase = jdbcTemplate.queryForMap(
                "SELECT pseudo, email, age, niveau_etude FROM utilisateur WHERE id_utilisateur = ?",
                utilisateurId
        );

        assertThat(dataEnBase.get("pseudo")).isEqualTo("NouveauPseudo");
        assertThat(dataEnBase.get("email")).isEqualTo("nouveau@email.com");
        assertThat(dataEnBase.get("age")).isEqualTo(24);
        assertThat(dataEnBase.get("niveau_etude")).isEqualTo("Master 1");
    }
}
