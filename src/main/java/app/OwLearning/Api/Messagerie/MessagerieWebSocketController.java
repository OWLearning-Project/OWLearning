package app.OwLearning.Api.Messagerie;

import app.OwLearning.Application.Services.ServiceDiscussion;
import app.OwLearning.Application.Services.ServiceMessage;
import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Shared.DTO.MessageEnvoiDTO;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurNonAutorise;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
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
    public Discussion envoyerMessageEnTempsReel(@DestinationVariable int idDiscussion, MessageEnvoiDTO messageEnvoiDTO) throws ExceptionUtilisateurNonAutorise
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
