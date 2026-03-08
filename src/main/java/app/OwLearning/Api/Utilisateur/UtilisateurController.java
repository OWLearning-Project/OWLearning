package app.OwLearning.Api.Utilisateur;

import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IServices.IServiceUtilisateur;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * Controller permettant de gérer les utilisateurs
 */
@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    private final IServiceUtilisateur serviceUtilisateur;

    public UtilisateurController(IServiceUtilisateur serviceUtilisateur) {
        this.serviceUtilisateur = serviceUtilisateur;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfil(@PathVariable int id) {
        Utilisateur utilisateur = serviceUtilisateur.getProfil(id);
        return ResponseEntity.ok(utilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifierProfil(@PathVariable int id, @RequestParam String pseudo, @RequestParam String email,
                                            @RequestParam(required = false) Integer age, @RequestParam(required = false) String niveauEtude)
    {
        Utilisateur utilisateur = serviceUtilisateur.modifierProfil(id, pseudo, email, age, niveauEtude);
        return ResponseEntity.ok(utilisateur);
    }
}
