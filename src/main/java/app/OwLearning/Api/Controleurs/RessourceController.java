package app.OwLearning.Api.Controleurs;

import app.OwLearning.Api.DTO.request.RessourceRequest;
import app.OwLearning.Api.DTO.response.RessourceResponse;
import app.OwLearning.Api.Mapper.RessourceDTOMapper;
import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Services.Interfaces.IServiceRessource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ressources")
@PreAuthorize("isAuthenticated()")
public class RessourceController {
    private final IServiceRessource serviceRessource;
    private final RessourceDTOMapper mapper;

    public RessourceController(IServiceRessource serviceRessource, RessourceDTOMapper mapper){
        this.serviceRessource = serviceRessource;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<RessourceResponse> creerRessource(@RequestBody RessourceRequest ressourceDTO){
        Ressource ressource = this.serviceRessource.creeRessource(ressourceDTO.getNom(), ressourceDTO.getUrl(), ressourceDTO.getType());
        RessourceResponse request = this.mapper.toResponse(ressource);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @GetMapping("/{idRessource}")
    public ResponseEntity<RessourceResponse> getRessource(@PathVariable("idRessource") int idRessource){
        Ressource ressource = this.serviceRessource.getContenuRessource(idRessource);
        return ResponseEntity.ok(this.mapper.toResponse(ressource));
    }

    @PutMapping("/{idRessource}")
    public ResponseEntity<String> modifierRessource(@PathVariable("idRessource") int idRessource, @RequestBody RessourceRequest ressourceDTO){
        this.serviceRessource.modifier(idRessource,ressourceDTO.getNom(), ressourceDTO.getUrl(), ressourceDTO.getType());
        return  ResponseEntity.ok().body("La ressource a été modifiée avec succès");
    }

    @DeleteMapping("/{idRessource}")
    public ResponseEntity<RessourceResponse> supprimerRessource(@PathVariable("idRessource") int idRessource){
        Ressource ressource = this.serviceRessource.supprimerRessource(idRessource);
        return  ResponseEntity.ok(this.mapper.toResponse(ressource));
    }
}
