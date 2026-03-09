package app.OwLearning.Api.Messagerie;

import app.OwLearning.Application.Services.ServiceDiscussion;
import app.OwLearning.Application.Services.ServiceMessage;
import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Infrastructure.Services.ServiceTokenJWT;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messagerie")
public class MessagerieRestController
{
    private final ServiceDiscussion serviceDiscussion;
    private final ServiceMessage serviceMessage;
    private final ServiceTokenJWT serviceTokenJWT;

    public MessagerieRestController(ServiceDiscussion serviceDiscussion, ServiceMessage serviceMessage,  ServiceTokenJWT serviceTokenJWT)
    {
        this.serviceDiscussion = serviceDiscussion;
        this.serviceMessage = serviceMessage;
        this.serviceTokenJWT = serviceTokenJWT;
    }

    @GetMapping("/mes-discussions")
    public ResponseEntity<List<Discussion>> trouverDiscussions(@Parameter(hidden = true) @RequestHeader("Authorization") String authHeader)
    {
        String tokenPur = authHeader;
        if (authHeader != null && authHeader.startsWith("Bearer "))
        {
            tokenPur = authHeader.substring(7);
        }
        int idUtilisateur = serviceTokenJWT.extraireID(tokenPur);

        List<Discussion> discussions = serviceDiscussion.getDiscussionsParIdUtilisateur(idUtilisateur);
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
