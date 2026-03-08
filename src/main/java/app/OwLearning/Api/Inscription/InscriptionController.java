package app.OwLearning.Api.Inscription;

import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IServices.IServiceInscription;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisIdEleve;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller inscription permettant de gérer les inscriptions aux cours
 */
@RestController
@RequestMapping("/api/inscription")
public class InscriptionController {
    private final IServiceInscription serviceInscription;
    public InscriptionController(IServiceInscription serviceInscription) {
        this.serviceInscription = serviceInscription;
    }

    @PostMapping("etudiants/{idEtudiant}/cours/{idCours}")
    public ResponseEntity<?> inscrireEtudiant(@PathVariable("idEtudiant") int idEtudiant, @PathVariable("idCours") int idCours){
        int resultat = serviceInscription.inscrireEtudiant(idEtudiant, idCours);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultat);
    }

    @PostMapping("/cours/{idCours}/etudiants/{idEtudiant}/refus")
    public ResponseEntity<?> refuserInscription(@PathVariable("idCours") int idCours, @PathVariable("idEtudiant") int idEtudiant) throws ExceptionMauvaisIdEleve {
        serviceInscription.supprimerInscriptionCours(idCours, idEtudiant);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/cours/{idCours}/etudiants")
    public ResponseEntity<?> getEtudiantsInscrits(@PathVariable("idCours") int idCours){
        ArrayList<Utilisateur> etudiant = serviceInscription.getEtudiantsInscrits(idCours);
        return ResponseEntity.ok(etudiant);
    }
}
