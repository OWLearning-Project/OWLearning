package app.OwLearning.Api.Controleurs;


import app.OwLearning.Api.DTO.request.ChapitreRequest;
import app.OwLearning.Api.DTO.request.RessourceRequest;
import app.OwLearning.Api.DTO.request.UtilisateurAuthentifieRequest;
import app.OwLearning.Api.DTO.response.ChapitreResponse;
import app.OwLearning.Api.DTO.response.RessourceResponse;
import app.OwLearning.Api.Mapper.ChapitreDTOMapper;
import app.OwLearning.Api.Mapper.RessourceDTOMapper;
import app.OwLearning.Domaine.Entités.Chapitre;
import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Services.Interfaces.IServiceChapitre;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller ChapitreController permettant d'accéder aux données liées aux chapitres
 */
@RestController
@RequestMapping("/api/chapitres")
@PreAuthorize("isAuthenticated()")
public class ChapitreController {
    private final IServiceChapitre serviceChapitre;
    private final ChapitreDTOMapper chapitreMapper;
    private final RessourceDTOMapper resssourceMapper;

    public ChapitreController(IServiceChapitre serviceChapitre, ChapitreDTOMapper chapitreMapper, RessourceDTOMapper resssourceMapper){
        this.serviceChapitre = serviceChapitre;
        this.chapitreMapper = chapitreMapper;
        this.resssourceMapper = resssourceMapper;
    }

    @GetMapping("/{idChapitre}")
    public ResponseEntity<ChapitreResponse> getChapitre(@PathVariable("idChapitre")int idChapitre)
    {
        Chapitre chapitre = this.serviceChapitre.getContenuChapitre(idChapitre);
        return ResponseEntity.ok(this.chapitreMapper.toResponse(chapitre));
    }

    @PutMapping("/{idChapitre}")
    public ResponseEntity<Void> modifierChapitre(@PathVariable("idChapitre") int idChapitre, @RequestBody ChapitreRequest chapitreDTO){
        this.serviceChapitre.modifier(idChapitre, chapitreDTO.getTitre(), chapitreDTO.getDescription());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idChapitre}/ressources")
    public ResponseEntity<?> ajouterRessource(
            @PathVariable("idChapitre") int idChapitre,
            @RequestBody RessourceRequest dto) {
        Ressource nouvelleRessource = this.resssourceMapper.toDomain(dto);
        this.serviceChapitre.ajouterRessource(idChapitre, nouvelleRessource);
        return ResponseEntity.status(HttpStatus.CREATED).body("La ressource a été ajoutée");
    }

    @DeleteMapping("/{idChapitre}/ressources/{idRessource}")
    public ResponseEntity<RessourceResponse> retirerRessource(
            @PathVariable("idChapitre") int idChapitre,
            @PathVariable("idRessource") int idRessource) {
        Ressource ressource = this.serviceChapitre.retirerRessource(idChapitre, idRessource);
        return ResponseEntity.ok(this.resssourceMapper.toResponse(ressource));
    }

    @PostMapping("/{idChapitre}/terminer")
    @PreAuthorize("hasAuthority('ELEVE')")
    public ResponseEntity<Void> terminerChapitre(
            @PathVariable("idChapitre") int idChapitre,
            @AuthenticationPrincipal UtilisateurAuthentifieRequest utilisateurAuthentifieDTO)
    {
        int idEleve = utilisateurAuthentifieDTO.getId();
        this.serviceChapitre.terminerChapitre(idChapitre, idEleve);
        return ResponseEntity.noContent().build();
    }
}
