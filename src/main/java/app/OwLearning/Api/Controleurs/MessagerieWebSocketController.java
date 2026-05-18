package app.OwLearning.Api.Controleurs;

import app.OwLearning.Api.DTO.request.MessageEnvoiRequest;
import app.OwLearning.Services.Services.ServiceDiscussion;
import app.OwLearning.Services.Services.ServiceMessage;
import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Domaine.Entités.Message;
import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurNonAutorise;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MessagerieWebSocketController
{
    private final ServiceDiscussion serviceDiscussion;
    private final ServiceMessage serviceMessage;

    public MessagerieWebSocketController(ServiceDiscussion serviceDiscussion,  ServiceMessage serviceMessage)
    {
        this.serviceDiscussion = serviceDiscussion;
        this.serviceMessage = serviceMessage;
    }

    @MessageMapping("/messagerie/{idDiscussion}/envoyer")
    @SendTo("/topic/discussion/{idDiscussion}")
    public Discussion envoyerMessageEnTempsReel(@DestinationVariable int idDiscussion, MessageEnvoiRequest messageEnvoiDTO) throws ExceptionUtilisateurNonAutorise
    {
        Discussion discussionMiseAJour = serviceDiscussion.envoyerMessage(idDiscussion, messageEnvoiDTO.getAuteurId(), messageEnvoiDTO.getContenu());
        if (messageEnvoiDTO.getRessourceId() != null)
        {
            List<Message> messages = discussionMiseAJour.getMessages();
            if (messages != null && !messages.isEmpty())
            {
                Message nouveauMessage = messages.get(messages.size() - 1);
                serviceMessage.ajouterRessource(nouveauMessage.getId_message(), messageEnvoiDTO.getRessourceId());
            }
        }
        return discussionMiseAJour;
    }
}
