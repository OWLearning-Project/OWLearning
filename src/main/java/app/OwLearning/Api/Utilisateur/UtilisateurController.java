package app.OwLearning.Api.Utilisateur;

import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IServices.IServiceUtilisateur;
import app.OwLearning.Infrastructure.Config.UtilisateurAuthentifie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


/**
 * Controller permettant de gérer les utilisateurs
 */

@RestController
@RequestMapping("/api/utilisateurs")
@PreAuthorize("isAuthenticated()")
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

    @PutMapping("/edit_profil")
    public ResponseEntity<?> modifierProfil(@AuthenticationPrincipal UtilisateurAuthentifie utilisateurAuthentifie, @RequestParam String pseudo, @RequestParam String email,
                                            @RequestParam(required = false) Integer age, @RequestParam(required = false) String niveauEtude)
    {
        Utilisateur utilisateur = serviceUtilisateur.modifierProfil(utilisateurAuthentifie.getId(), pseudo, email, age, niveauEtude);
        return ResponseEntity.ok(utilisateur);
    }
}
