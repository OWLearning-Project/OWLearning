package app.OwLearning.Api.Controleurs;

import app.OwLearning.Api.DTO.request.UtilisateurAuthentifieRequest;
import app.OwLearning.Api.DTO.response.UtilisateurResponse;
import app.OwLearning.Api.Mapper.UtilisateurDTOMapper;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Services.Interfaces.IServiceUtilisateur;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller permettant de gérer les utilisateurs
 */

@RestController
@RequestMapping("/api/utilisateurs")
@PreAuthorize("isAuthenticated()")
public class UtilisateurController {
    private final IServiceUtilisateur serviceUtilisateur;
    private final UtilisateurDTOMapper mapper;

    public UtilisateurController(IServiceUtilisateur serviceUtilisateur, UtilisateurDTOMapper mapper) {
        this.serviceUtilisateur = serviceUtilisateur;
        this.mapper = mapper;
    }
    @GetMapping("/createurs")
    public ResponseEntity<List<UtilisateurResponse>> getCreateurs()
    {
        return ResponseEntity.ok(this.mapper.toResponseList(serviceUtilisateur.getCreateurs()));
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<UtilisateurResponse> getProfil(@PathVariable int id) {
        Utilisateur utilisateur = serviceUtilisateur.getProfil(id);
        return ResponseEntity.ok(this.mapper.toResponse(utilisateur));
    }

    @PutMapping("/edit_profil")
    public ResponseEntity<UtilisateurResponse> modifierProfil(@AuthenticationPrincipal UtilisateurAuthentifieRequest utilisateurAuthentifieDTO, @RequestParam(required = false) String pseudo, @RequestParam String email,
                                                              @RequestParam(required = false) Integer age, @RequestParam(required = false) String niveauEtude)
    {
        Utilisateur utilisateur = serviceUtilisateur.modifierProfil(utilisateurAuthentifieDTO.getId(), pseudo, email, age, niveauEtude);
        return ResponseEntity.ok(this.mapper.toResponse(utilisateur));
    }
}
