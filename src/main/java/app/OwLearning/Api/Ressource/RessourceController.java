package app.OwLearning.Api.Ressource;

import app.OwLearning.Api.DTO.request.RessourceRequest;
import app.OwLearning.Api.DTO.response.RessourceResponse;
import app.OwLearning.Application.Mapper.RessourceDTOMapper;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IServices.IServiceRessource;
import app.OwLearning.Shared.DTO.RessourceDTO;
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
        Ressource domain = this.mapper.toDomain(ressourceDTO);
        Ressource ressource = this.serviceRessource.creeRessource(domain.getNom(), domain.getUrl(), domain.getType());
        RessourceResponse request = this.mapper.toResponse(ressource);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
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
