package app.OwLearning.Api.Controleurs;

import app.OwLearning.Api.DTO.request.UtilisateurAuthentifieRequest;
import app.OwLearning.Api.DTO.request.UtilisateurConnexionRequest;
import app.OwLearning.Api.DTO.response.DiscussionResponse;
import app.OwLearning.Api.DTO.response.MessageResponse;
import app.OwLearning.Api.DTO.response.RessourceResponse;
import app.OwLearning.Services.Mapper.DiscussionDTOMapper;
import app.OwLearning.Services.Mapper.MessageDTOMapper;
import app.OwLearning.Services.Mapper.RessourceDTOMapper;
import app.OwLearning.Services.Services.ServiceDiscussion;
import app.OwLearning.Services.Services.ServiceMessage;
import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Domaine.Entités.Message;
import app.OwLearning.Domaine.Entités.Ressource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messagerie")
@PreAuthorize("isAuthenticated()")
public class MessagerieRestController
{
    private final ServiceDiscussion serviceDiscussion;
    private final ServiceMessage serviceMessage;
    private final MessageDTOMapper messageMapper;
    private final DiscussionDTOMapper discussionMapper;
    private final RessourceDTOMapper ressourceMapper;

    public MessagerieRestController(ServiceDiscussion serviceDiscussion, ServiceMessage serviceMessage, MessageDTOMapper messageMapper, DiscussionDTOMapper discussionMapper, RessourceDTOMapper ressourceMapper)
    {
        this.serviceDiscussion = serviceDiscussion;
        this.serviceMessage = serviceMessage;
        this.messageMapper = messageMapper;
        this.discussionMapper = discussionMapper;
        this.ressourceMapper = ressourceMapper;
    }

    @GetMapping("/mes-discussions")
    public ResponseEntity<List<DiscussionResponse>> trouverDiscussions(@AuthenticationPrincipal UtilisateurAuthentifieRequest utilisateurAuthentifieDTO)
    {
        List<Discussion> discussions = serviceDiscussion.getDiscussionsParIdUtilisateur(utilisateurAuthentifieDTO.getId());
        return ResponseEntity.ok(this.discussionMapper.toResponseList(discussions));
    }

    @GetMapping("/{idDiscussion}/messages")
    public ResponseEntity<List<MessageResponse>> trouverMessagesDiscussion(@PathVariable int idDiscussion)
    {
        List<Message> messages = serviceMessage.trouverMessageParDiscussion(idDiscussion);
        return ResponseEntity.ok(this.messageMapper.toResponseList(messages));
    }

    @PostMapping("/{idMessage}/ressources/{idRessource}")
    public ResponseEntity<String> lierRessourceMessage(@PathVariable int idMessage, @PathVariable int idRessource)
    {
        serviceMessage.ajouterRessource(idMessage, idRessource);
        return ResponseEntity.ok("Ressource ajoutée au message avec succès");
    }

    @DeleteMapping("/{idMessage}/ressources/{idRessource}")
    public ResponseEntity<RessourceResponse> retirerRessourceMessage(@PathVariable int idMessage, @PathVariable int idRessource)
    {
        Ressource ressourceSupprimee = serviceMessage.supprimerRessource(idMessage, idRessource);
        return ResponseEntity.ok(this.ressourceMapper.toResponse(ressourceSupprimee));
    }
}
