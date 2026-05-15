package app.OwLearning.Api.Progression;

import app.OwLearning.Application.Services.ServiceProgression;
import app.OwLearning.Application.Ports.IServices.IServiceProgression;
import app.OwLearning.Shared.DTO.UtilisateurAuthentifieDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller ProgressionController permettant d'accéder aux données liées à la progression
 */
@RestController
@RequestMapping("api/progression")
@PreAuthorize("isAuthenticated()")
public class ProgressionController {
    private final IServiceProgression serviceProgression;

    public ProgressionController(ServiceProgression unServiceProgression){
        this.serviceProgression = unServiceProgression;
    }

    @PreAuthorize("hasAuthority('ELEVE')")
    @GetMapping("/{idCours}")
    public ResponseEntity<?> getTauxProgression(@PathVariable("idCours") int coursId, @AuthenticationPrincipal UtilisateurAuthentifieDTO utilisateurAuthentifieDTO){
        if (coursId <= 0 )
        {
            return ResponseEntity.badRequest().body("id incorrect");
        }
        float taux = serviceProgression.getProgressionEleve(utilisateurAuthentifieDTO.getId(), coursId);
        return ResponseEntity.ok(Map.of("tauxProgression", taux));
    }
}
