package Api.Auth;

import Application.Services.Hach;
import Application.Services.ServiceAuthentification;
import Shared.DTO.UtilisateurConnexionDTO;
import Shared.DTO.UtilisateurInscriptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> inscription (@RequestBody UtilisateurInscriptionDTO u)
    {
        boolean inscrit = this.serviceAuth.inscription(u.getNom(), u.getPrenom(), u.getEmail(), u.getMdp(), u.getRole());
        if (!inscrit)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("L'inscription a échoue");
        return ResponseEntity.status(HttpStatus.CREATED).body("L'utilisateur est inscrit");
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody UtilisateurConnexionDTO u)
    {
        String token = serviceAuth.connexion(u.getEmail(),u.getMotDePasse());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<String> deconnexion (@RequestHeader("Authorization") String unToken)
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
