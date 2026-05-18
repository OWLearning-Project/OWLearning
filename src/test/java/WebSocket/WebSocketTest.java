package WebSocket;

import app.OwLearning.Api.DTO.request.MessageEnvoiRequest;
import app.OwLearning.Api.DTO.response.DiscussionResponse;
import app.OwLearning.Services.Services.ServiceDiscussion;
import app.OwLearning.Infrastructure.Repositories.MessageRepository;
import app.OwLearning.Main;
import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurNonAutorise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest
{
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private WebSocketStompClient stompClient;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ServiceDiscussion serviceDiscussion;

    private int idParticipant;
    private int idNonParticipant;
    private int idDiscussion;

    @BeforeEach
    public void setUp()
    {
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.stompClient.setMessageConverter(new JacksonJsonMessageConverter());

        String emailParticipant = "ws_participant" + System.currentTimeMillis() + "@test.com";
        String emailNonParticipant = "ws_nonparticipant" + System.currentTimeMillis() + "@test.com";

        KeyHolder keyHolderParticipant = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO utilisateur (nom, prenom, email, pseudo, date_inscription, mot_de_passe) VALUES ('Test', 'Participant', ?, ?, CURRENT_TIMESTAMP, 'motdepasse')",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, emailParticipant);
            ps.setString(2, "participant_" + System.currentTimeMillis());
            return ps;
        }, keyHolderParticipant);
        idParticipant = keyHolderParticipant.getKey().intValue();

        jdbcTemplate.update(
                "INSERT INTO eleve (id_utilisateur, age) VALUES (?, 0)",
                idParticipant
        );

        KeyHolder keyHolderPirate = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO utilisateur (nom, prenom, email, pseudo, date_inscription, mot_de_passe) VALUES ('Test', 'Pirate', ?, ?, CURRENT_TIMESTAMP, 'motdepasse')",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, emailNonParticipant);
            ps.setString(2, "pirate_" + System.currentTimeMillis());
            return ps;
        }, keyHolderPirate);
        idNonParticipant = keyHolderPirate.getKey().intValue();

        jdbcTemplate.update(
                "INSERT INTO eleve (id_utilisateur, age) VALUES (?, 0)",
                idNonParticipant
        );

        KeyHolder keyHolderDiscussion = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> connection.prepareStatement(
                "INSERT INTO discussion DEFAULT VALUES",
                Statement.RETURN_GENERATED_KEYS
        ), keyHolderDiscussion);
        idDiscussion = keyHolderDiscussion.getKey().intValue();

        jdbcTemplate.update(
                "INSERT INTO participation_discussion (id_utilisateur, id_discussion) VALUES (?, ?)",
                idParticipant, idDiscussion
        );
    }

    @AfterEach
    public void tearDown()
    {
        jdbcTemplate.update("DELETE FROM message WHERE id_discussion = ?", idDiscussion);
        jdbcTemplate.update("DELETE FROM participation_discussion WHERE id_discussion = ?", idDiscussion);
        jdbcTemplate.update("DELETE FROM eleve WHERE id_utilisateur IN (?, ?)", idParticipant, idNonParticipant);

        jdbcTemplate.update("DELETE FROM utilisateur WHERE id_utilisateur IN (?, ?)", idParticipant, idNonParticipant);
        jdbcTemplate.update("DELETE FROM discussion WHERE id_discussion = ?", idDiscussion);
    }

    @Test
    public void envoiEtRecoitUnMessage() throws ExecutionException, InterruptedException, TimeoutException
    {
        String url = "ws://localhost:" + port + "/ws-messagerie";
        StompSession session = stompClient.connectAsync(url, new StompSessionHandlerAdapter() {}).get(1, TimeUnit.SECONDS);

        CompletableFuture<DiscussionResponse> futureReponse = new CompletableFuture<>();

        session.subscribe("/topic/discussion/" + idDiscussion, new StompFrameHandler()
        {
            @Override
            public Type getPayloadType(StompHeaders headers)
            {
                return DiscussionResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload)
            {
                futureReponse.complete((DiscussionResponse) payload);
            }
        });

        MessageEnvoiRequest nouveauMessage = new MessageEnvoiRequest(idParticipant, "Test d'integration WS", null);
        session.send("/app/messagerie/" + idDiscussion + "/envoyer", nouveauMessage);

        DiscussionResponse discussionReception = futureReponse.get(3, TimeUnit.SECONDS);

        assertNotNull(discussionReception, "La discussion reçue ne peut pas être nulle");
        int indexDernierMessage = discussionReception.getMessages().size() - 1;
        String contenuRecu = discussionReception.getMessages().get(indexDernierMessage).getContenu();

        assertEquals("Test d'integration WS", contenuRecu, "Le contenu du message reçu ne correspond pas !");
    }

    @Test
    public void testEnvoiMessageUtilisateurNonAutorise_DoitEchouer() {
        long nombreMessagesAvant = messageRepository.trouverParDiscussion(idDiscussion).size();

        MessageEnvoiRequest messagePirate = new MessageEnvoiRequest(idNonParticipant, "hack", null);

        assertThrows(
                ExceptionUtilisateurNonAutorise.class,
                () -> serviceDiscussion.envoyerMessage(
                        idDiscussion,
                        messagePirate.getAuteurId(),
                        messagePirate.getContenu()
                ),
                "Une ExceptionUtilisateurNonAutorise doit être levée"
        );
        long nombreMessagesApres = messageRepository.trouverParDiscussion(idDiscussion).size();
        assertEquals(nombreMessagesAvant, nombreMessagesApres, "La base ne doit pas être modifiée");
    }
}