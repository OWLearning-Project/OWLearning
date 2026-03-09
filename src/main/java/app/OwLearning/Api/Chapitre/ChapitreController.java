package app.OwLearning.Api.Chapitre;


import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IServices.IServiceChapitre;
import app.OwLearning.Shared.DTO.AjoutRessourceDTO;
import app.OwLearning.Shared.DTO.ChapitreDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller ChapitreController permettant d'accéder aux données liées aux chapitres
 */
@RestController
@RequestMapping("api/chapitre")
public class ChapitreController {
    private final IServiceChapitre serviceChapitre;

    public ChapitreController(IServiceChapitre serviceChapitre){
        this.serviceChapitre = serviceChapitre;
    }

    @GetMapping("/{idChapitre}")
    public ResponseEntity<ChapitreDTO> getChapitre(@PathVariable("idChapitre")int idChapitre)
    {
        Chapitre chapitre = this.serviceChapitre.getContenuChapitre(idChapitre);
        ChapitreDTO chapitreDTO = new ChapitreDTO();
        chapitreDTO.setTitre(chapitre.getTitre());
        chapitreDTO.setDescription(chapitre.getDescription());
        return ResponseEntity.ok(chapitreDTO);
    }

    @PutMapping("/{idChapitre}")
    public ResponseEntity<Void> modifierChapitre(@PathVariable("idChapitre") int idChapitre, @RequestBody ChapitreDTO chapitreDTO){
        this.serviceChapitre.modifier(idChapitre, chapitreDTO.getTitre(), chapitreDTO.getDescription());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idChapitre}/ressources")
    public ResponseEntity<Ressource> ajouterRessource(
            @PathVariable("idChapitre") int idChapitre,
            @RequestBody AjoutRessourceDTO dto) {

        Ressource nouvelleRessource = new Ressource(dto.getNom(), dto.getType(), dto.getUrl());
        this.serviceChapitre.ajouterRessource(idChapitre, nouvelleRessource);
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
