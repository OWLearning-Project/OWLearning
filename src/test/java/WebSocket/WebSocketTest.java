package WebSocket;

import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Main;
import app.OwLearning.Shared.DTO.MessageEnvoiDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest
{
    @LocalServerPort
    private int port;

    private WebSocketStompClient stompClient;

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
}
