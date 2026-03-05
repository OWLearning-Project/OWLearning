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
        try{
            Utilisateur utilisateur = serviceUtilisateur.getProfil(id);
            return ResponseEntity.ok(utilisateur);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/id")
    public ResponseEntity<?> modifierProfil(@PathVariable int id, @RequestParam String pseudo, @RequestParam String email) {
        try {
            serviceUtilisateur.modifierProfil(id, pseudo, email);
            return ResponseEntity.ok().build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
