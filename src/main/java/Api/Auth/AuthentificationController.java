package Api.Auth;

import Application.Services.Hach;
import Application.Services.ServiceAuthentification;
import Shared.DTO.UtilisateurInscriptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
