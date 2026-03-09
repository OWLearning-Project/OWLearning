package app.OwLearning.Api.Ressource;

import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IServices.IServiceRessource;
import app.OwLearning.Shared.DTO.RessourceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ressource")
public class RessourceController {
    private final IServiceRessource serviceRessource;

    public RessourceController(IServiceRessource serviceRessource){
        this.serviceRessource = serviceRessource;
    }

    @PostMapping
    public ResponseEntity<?> creerRessource(@RequestBody RessourceDTO ressourceDTO){
        Ressource ressource = this.serviceRessource.creeRessource(ressourceDTO.getNom(), ressourceDTO.getUrl(), ressourceDTO.getType());
        return ResponseEntity.status(HttpStatus.CREATED).body(ressource);
    }

    @GetMapping("/{idRessource}")
    public ResponseEntity<Ressource> getRessource(@PathVariable("idRessource") int idRessource){
        return ResponseEntity.ok(this.serviceRessource.getContenuRessource(idRessource));
    }

    @PutMapping("/{idRessource}")
    public ResponseEntity<Void> modifierRessource(@PathVariable("idRessource") int idRessource, @RequestBody RessourceDTO ressourceDTO){
        this.serviceRessource.modifier(idRessource,ressourceDTO.getNom(), ressourceDTO.getUrl(), ressourceDTO.getType());
        return  ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idRessource}")
    public ResponseEntity<Ressource> supprimerRessource(@PathVariable("idRessource") int idRessource){
        Ressource ressource = this.serviceRessource.supprimerRessource(idRessource);
        return  ResponseEntity.ok(ressource);
    }
}
