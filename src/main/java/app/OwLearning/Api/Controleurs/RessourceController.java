package app.OwLearning.Api.Controleurs;

import app.OwLearning.Api.DTO.request.RessourceRequest;
import app.OwLearning.Api.DTO.response.RessourceResponse;
import app.OwLearning.Api.Mapper.RessourceDTOMapper;
import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Services.Interfaces.IServiceRessource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @PreAuthorize("hasAuthority('CREATEUR')")
    @PostMapping
    public ResponseEntity<RessourceResponse> creerRessource(@RequestBody RessourceRequest ressourceDTO){
        Ressource ressource = this.serviceRessource.creeRessource(ressourceDTO.getNom(), ressourceDTO.getUrl(), ressourceDTO.getType());
        RessourceResponse request = this.mapper.toResponse(ressource);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploaderRessource(@RequestParam("fichier") MultipartFile fichier) {
        try {
            String urlBase = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            Ressource ressource = this.serviceRessource.uploaderRessource(fichier, urlBase);
            return ResponseEntity.status(HttpStatus.CREATED).body(this.mapper.toResponse(ressource));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }

    @GetMapping("/{idRessource}")
    public ResponseEntity<RessourceResponse> getRessource(@PathVariable("idRessource") int idRessource){
        Ressource ressource = this.serviceRessource.getContenuRessource(idRessource);
        return ResponseEntity.ok(this.mapper.toResponse(ressource));
    }

    @PreAuthorize("hasAuthority('CREATEUR')")
    @PutMapping("/{idRessource}")
    public ResponseEntity<String> modifierRessource(@PathVariable("idRessource") int idRessource, @RequestBody RessourceRequest ressourceDTO){
        this.serviceRessource.modifier(idRessource,ressourceDTO.getNom(), ressourceDTO.getUrl(), ressourceDTO.getType());
        return  ResponseEntity.ok().body("La ressource a été modifiée avec succès");
    }

    @PreAuthorize("hasAuthority('CREATEUR')")
    @DeleteMapping("/{idRessource}")
    public ResponseEntity<RessourceResponse> supprimerRessource(@PathVariable("idRessource") int idRessource){
        Ressource ressource = this.serviceRessource.supprimerRessource(idRessource);
        return  ResponseEntity.ok(this.mapper.toResponse(ressource));
    }
}
