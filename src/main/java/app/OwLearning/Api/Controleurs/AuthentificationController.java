package app.OwLearning.Api.Controleurs;

import app.OwLearning.Api.DTO.request.UtilisateurConnexionRequest;
import app.OwLearning.Api.DTO.request.UtilisateurInscriptionRequest;
import app.OwLearning.Services.Services.ServiceAuthentification;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller AuthentificationController permettant d'accéder aux données liées à l'authentification
 */
@RestController
@RequestMapping("/api/authentification")
public class AuthentificationController
{
    private final ServiceAuthentification serviceAuth;

    public AuthentificationController(ServiceAuthentification serviceAuth)
    {
        this.serviceAuth = serviceAuth;
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> inscription (@RequestBody UtilisateurInscriptionRequest u)
    {
        this.serviceAuth.inscription(u.getNom(), u.getPrenom(), u.getEmail(), u.getMotDePasse(), u.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body("L'utilisateur est inscrit");
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody UtilisateurConnexionRequest u)
    {
        String token = serviceAuth.connexion(u.getEmail(),u.getMotDePasse());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnexion (@Parameter(hidden = true) @RequestHeader("Authorization") String unToken)
    {
        String tokenPur = unToken;
        // On retire le préfixe Bearer ajouté par le front
        if (unToken != null && unToken.startsWith("Bearer "))
        {
            tokenPur = unToken.substring(7);
        }
        serviceAuth.deconnexion((tokenPur));
        return ResponseEntity.ok("Déconnecté avec succès");
    }
}
