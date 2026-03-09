package app.OwLearning.Api.Messagerie;

import app.OwLearning.Application.Services.ServiceDiscussion;
import app.OwLearning.Application.Services.ServiceMessage;
import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Shared.DTO.UtilisateurAuthentifieDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messagerie")
@PreAuthorize("isAuthenticated()")
public class MessagerieRestController
{
    private final ServiceDiscussion serviceDiscussion;
    private final ServiceMessage serviceMessage;

    public MessagerieRestController(ServiceDiscussion serviceDiscussion, ServiceMessage serviceMessage)
    {
        this.serviceDiscussion = serviceDiscussion;
        this.serviceMessage = serviceMessage;
    }

    @GetMapping("/mes-discussions")
    public ResponseEntity<List<Discussion>> trouverDiscussions(@AuthenticationPrincipal UtilisateurAuthentifieDTO utilisateurAuthentifieDTO)
    {
        List<Discussion> discussions = serviceDiscussion.getDiscussionsParIdUtilisateur(utilisateurAuthentifieDTO.getId());
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/{idDiscussion}/messages")
    public ResponseEntity<List<Message>> trouverMessagesDiscussion(@PathVariable int idDiscussion)
    {
        List<Message> messages = serviceMessage.trouverMessageParDiscussion(idDiscussion);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messages/{idMessage}/ressources/{idRessource}")
    public ResponseEntity<String> lierRessourceMessage(@PathVariable int idMessage, @PathVariable int idRessource)
    {
        serviceMessage.ajouterRessource(idMessage, idRessource);
        return ResponseEntity.ok("Ressource ajoutée au message avec succès");
    }

    @DeleteMapping("/messages/{idMessage}/ressources/{idRessource}")
    public ResponseEntity<Ressource> retirerRessourceMessage(@PathVariable int idMessage, @PathVariable int idRessource)
    {
        Ressource ressourceSupprimee = serviceMessage.supprimerRessource(idMessage, idRessource);
        return ResponseEntity.ok(ressourceSupprimee);
    }
}
