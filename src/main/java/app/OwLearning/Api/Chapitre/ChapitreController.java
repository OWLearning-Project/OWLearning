package app.OwLearning.Api.Chapitre;


import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IServices.IServiceChapitre;
import app.OwLearning.Shared.DTO.ChapitreDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/chapitre")
public class ChapitreController {
    private final IServiceChapitre serviceChapitre;

    public ChapitreController(IServiceChapitre serviceChapitre){
        this.serviceChapitre = serviceChapitre;
    }

    @GetMapping("/{idChapitre}")
    public ResponseEntity<Chapitre> getChapitre(@PathVariable("idChapitre")int idChapitre){
        return ResponseEntity.ok(this.serviceChapitre.getContenuChapitre(idChapitre));
    }

    @PutMapping("/{idChapitre}")
    public ResponseEntity<Void> modifierChapitre(@PathVariable("idChapitre") int idChapitre, @RequestBody ChapitreDTO chapitreDTO){
        this.serviceChapitre.modifier(idChapitre, chapitreDTO.getTitre(), chapitreDTO.getDescription());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ressources")
    public ResponseEntity<Void> ajouterRessource(
            @PathVariable("id") int id,
            @RequestBody Ressource ressource) {

        this.serviceChapitre.ajouterRessource(id, ressource);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idChapitre}/ressources/{idRessource}")
    public ResponseEntity<Ressource> retirerRessource(
            @PathVariable("idChapitre") int idChapitre,
            @PathVariable("idRessource") int idRessource) {

        Ressource ressource = this.serviceChapitre.retirerRessource(idChapitre, idRessource);
        return ResponseEntity.ok(ressource);
    }
}
