package app.OwLearning.Api.Chapitre;


import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Ports.IServices.IServiceChapitre;
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

    }
}
