package app.OwLearning.Api.Progression;

import Application.Services.ServiceProgression;
import Domain.Ports.IServices.IServiceProgression;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/progression")
public class ProgressionController {
    private final IServiceProgression serviceProgression;

    public ProgressionController(ServiceProgression unServiceProgression){
        this.serviceProgression = unServiceProgression;
    }

    @GetMapping(" /{idCours}/{idEleve}")
    public ResponseEntity<?> getTauxProgression(@PathVariable("idCours") int coursId, @PathVariable("idEleve") int eleveId){
        try {
            if (coursId <= 0 || eleveId <= 0) {
                return ResponseEntity.badRequest().body("id incorrect");
            }
            float taux = serviceProgression.getProgressionEleve(eleveId, coursId);
            return ResponseEntity.ok(taux);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body("Aucune progression trouver pour cet eleve sur ce cours");
        }
    }
}
