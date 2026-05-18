package app.OwLearning.Api.Controleurs;

import app.OwLearning.Api.DTO.request.UtilisateurAuthentifieRequest;
import app.OwLearning.Services.Mapper.UtilisateurDTOMapper;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Services.Interfaces.IServiceInscription;
import app.OwLearning.Domaine.Exceptions.ExceptionMauvaisIdEleve;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller inscription permettant de gérer les inscriptions aux cours
 */

@RestController
@RequestMapping("/api/inscription")
@PreAuthorize("isAuthenticated()")
public class InscriptionController {
    private final IServiceInscription serviceInscription;
    private final UtilisateurDTOMapper mapper;
    public InscriptionController(IServiceInscription serviceInscription, UtilisateurDTOMapper mapper) {
        this.serviceInscription = serviceInscription;
        this.mapper = mapper;
    }

    @PostMapping("/etudiants/cours/{idCours}")
    public ResponseEntity<?> inscrireEtudiant(@AuthenticationPrincipal UtilisateurAuthentifieRequest utilisateurAuthentifieDTO, @PathVariable("idCours") int idCours){
        int resultat = serviceInscription.inscrireEtudiant(utilisateurAuthentifieDTO.getId(), idCours);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultat);
    }

    @PreAuthorize("hasAuthority('CREATEUR')")
    @PostMapping("/cours/{idCours}/etudiants/{idEtudiant}/refus")
    public ResponseEntity<?> refuserInscription(@PathVariable("idCours") int idCours, @PathVariable("idEtudiant") int idEtudiant) throws ExceptionMauvaisIdEleve {
        serviceInscription.supprimerInscriptionCours(idCours, idEtudiant);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cours/{idCours}/etudiants")
    public ResponseEntity<?> getEtudiantsInscrits(@PathVariable("idCours") int idCours){
        ArrayList<Utilisateur> etudiants = serviceInscription.getEtudiantsInscrits(idCours);
        return ResponseEntity.ok(this.mapper.toResponseList(etudiants));
    }
}
