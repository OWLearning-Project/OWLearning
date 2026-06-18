package app.OwLearning.Api.Controleurs;

import app.OwLearning.Api.DTO.request.MessageEnvoiRequest;
import app.OwLearning.Api.DTO.response.DiscussionResponse;
import app.OwLearning.Api.Mapper.DiscussionDTOMapper;
import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurNonAutorise;
import app.OwLearning.Services.Services.ServiceDiscussion;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessagerieWebSocketController
{
    private final ServiceDiscussion serviceDiscussion;
    private final DiscussionDTOMapper discussionMapper;

    public MessagerieWebSocketController(ServiceDiscussion serviceDiscussion, DiscussionDTOMapper discussionMapper)
    {
        this.serviceDiscussion = serviceDiscussion;
        this.discussionMapper = discussionMapper;
    }

    @MessageMapping("/messagerie/{idDiscussion}/envoyer")
    @SendTo("/topic/discussion/{idDiscussion}")
    public DiscussionResponse envoyerMessageEnTempsReel(@DestinationVariable int idDiscussion, MessageEnvoiRequest messageEnvoiDTO) throws ExceptionUtilisateurNonAutorise
    {
        Discussion discussionMiseAJour = serviceDiscussion.envoyerMessage(idDiscussion, messageEnvoiDTO.getAuteurId(), messageEnvoiDTO.getContenu(), messageEnvoiDTO.getRessourceId());
        return discussionMapper.toResponse(discussionMiseAJour);
    }
}
