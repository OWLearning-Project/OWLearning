package app.OwLearning.Api.Utilisateur;

import app.OwLearning.Api.DTO.response.UtilisateurAuthentifieResponse;
import app.OwLearning.Application.Mapper.UtilisateurDTOMapper;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IServices.IServiceUtilisateur;
import app.OwLearning.Shared.DTO.UtilisateurAuthentifieDTO;
import org.springframework.http.ResponseEntity;
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
    private final UtilisateurDTOMapper mapper;

    public UtilisateurController(IServiceUtilisateur serviceUtilisateur, UtilisateurDTOMapper mapper) {
        this.serviceUtilisateur = serviceUtilisateur;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurAuthentifieResponse> getProfil(@PathVariable int id) {
        Utilisateur utilisateur = serviceUtilisateur.getProfil(id);
        return ResponseEntity.ok(this.mapper.toResponse(utilisateur));
    }

    @PutMapping("/edit_profil")
    public ResponseEntity<UtilisateurAuthentifieResponse> modifierProfil(@AuthenticationPrincipal UtilisateurAuthentifieDTO utilisateurAuthentifieDTO, @RequestParam String pseudo, @RequestParam String email,
                                            @RequestParam(required = false) Integer age, @RequestParam(required = false) String niveauEtude)
    {
        Utilisateur utilisateur = serviceUtilisateur.modifierProfil(utilisateurAuthentifieDTO.getId(), pseudo, email, age, niveauEtude);
        return ResponseEntity.ok(this.mapper.toResponse(utilisateur));
    }
}
