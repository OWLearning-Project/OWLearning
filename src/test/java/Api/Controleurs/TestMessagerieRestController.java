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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestMessagerieRestController extends AbstractIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMesDiscussionsRetourneLesDiscussionsDuParticipant() throws Exception
    {
        int eleveId = insererEleve("messagerie-discussions-eleve");
        int discussionId = insererDiscussion();
        ajouterParticipantDiscussion(eleveId, discussionId);

        mockMvc.perform(get("/api/messagerie/mes-discussions")
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(discussionId));
    }

    @Test
    public void getMessagesDiscussionRetourneLesMessages() throws Exception
    {
        int eleveId = insererEleve("messagerie-messages-eleve");
        int discussionId = insererDiscussion();
        ajouterParticipantDiscussion(eleveId, discussionId);
        int messageId = insererMessage(discussionId, eleveId, "Message de test", "2026-05-28 10:00:00");

        mockMvc.perform(get("/api/messagerie/" + discussionId + "/messages")
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(messageId))
                .andExpect(jsonPath("$[0].contenu").value("Message de test"));
    }

    @Test
    public void peutLierEtRetirerUneRessourceDUnMessage() throws Exception
    {
        int eleveId = insererEleve("messagerie-ressource-eleve");
        int discussionId = insererDiscussion();
        ajouterParticipantDiscussion(eleveId, discussionId);
        int messageId = insererMessage(discussionId, eleveId, "Message avec ressource", "2026-05-28 10:00:00");
        int ressourceId = insererRessource("Ressource message", TypeRessource.FICHIER_PDF);

        mockMvc.perform(post("/api/messagerie/" + messageId + "/ressources/" + ressourceId)
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .with(csrf()))
                .andExpect(status().isOk());

        synchroniserPersistenceContext();
        Integer liens = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM piece_jointe WHERE id_message = ? AND id_ressource = ?",
                Integer.class,
                messageId,
                ressourceId
        );
        assertThat(liens).isEqualTo(1);

        mockMvc.perform(delete("/api/messagerie/" + messageId + "/ressources/" + ressourceId)
                        .with(authentication(authentification(eleveId, "ELEVE")))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ressourceId));

        synchroniserPersistenceContext();
        Integer liensRestants = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM piece_jointe WHERE id_message = ? AND id_ressource = ?",
                Integer.class,
                messageId,
                ressourceId
        );
        assertThat(liensRestants).isZero();
    }
}
