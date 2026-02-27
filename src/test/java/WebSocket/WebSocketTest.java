package WebSocket;

import app.OwLearning.Application.Services.ServiceDiscussion;
import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Infrastructure.Persistence.Repository.MessageRepository;
import app.OwLearning.Main;
import app.OwLearning.Shared.DTO.MessageEnvoiDTO;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurNonAutorise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

// Attention ces test ne passent que si la base de donnée est bien construite
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest
{
    @LocalServerPort
    private int port;

    private WebSocketStompClient stompClient;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ServiceDiscussion serviceDiscussion;

    @BeforeEach
    public void setUp()
    {
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.stompClient.setMessageConverter(new JacksonJsonMessageConverter());
    }

    @Test
    public void envoiEtRecoitUnMessage() throws ExecutionException, InterruptedException, TimeoutException
    {
        String url = "ws://localhost:" + port + "/ws-messagerie";
        StompSession session = stompClient.connectAsync(url, new StompSessionHandlerAdapter() {}).get(1, TimeUnit.SECONDS);
        CompletableFuture<Discussion> futureReponse = new CompletableFuture<>();

        session.subscribe("/topic/discussion/1", new StompFrameHandler()
        {
            @Override
            public Type getPayloadType(StompHeaders headers)
            {
                return Discussion.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload)
            {
                futureReponse.complete((Discussion) payload);
            }
        });

        MessageEnvoiDTO nouveauMessage = new MessageEnvoiDTO(1,"Test d'integration WS",null);
        session.send("/app/messagerie/1/envoyer", nouveauMessage);

        Discussion discussionReception = futureReponse.get(3, TimeUnit.SECONDS);

        assertNotNull(discussionReception,"La discussion reçue ne peut pas être nulle");
        int indexDernierMessage = discussionReception.getMessages().size() - 1;
        String contenuRecu = discussionReception.getMessages().get(indexDernierMessage).getContenu();

        assertEquals("Test d'integration WS", contenuRecu, "Le contenu du message reçu ne correspond pas !");

    }

    @Test
    public void testEnvoiMessageUtilisateurNonAutorise_DoitEchouer() {
        long nombreMessagesAvant = messageRepository.trouverParDiscussion(1).size();

        MessageEnvoiDTO messagePirate = new MessageEnvoiDTO(3, "hack", null);

        ExceptionUtilisateurNonAutorise exception = assertThrows(
                ExceptionUtilisateurNonAutorise.class,
                () ->
                {
                    serviceDiscussion.envoyerMessage(1, messagePirate.getAuteurId(), messagePirate.getContenu());
                },
                "Une ExceptionUtilisateurNonAutorise doit être levée"
        );

        long nombreMessagesApres = messageRepository.trouverParDiscussion(1).size();
        assertEquals(nombreMessagesAvant, nombreMessagesApres, "La base ne doit pas être modifié");
    }
}
