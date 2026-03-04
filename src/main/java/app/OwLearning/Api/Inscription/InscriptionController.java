package app.OwLearning.Api.Inscription;

import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IServices.IServiceInscription;
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
    public ResponseEntity<?> inscrireEtudiant(@PathVariable("idEtudiant") int idEtudiant, @PathVariable("dCours") int idCours){
        try{
            int resultat = serviceInscription.inscrireEtudiant(idEtudiant, idCours);
            if(resultat <- 0)
                return ResponseEntity.badRequest().body("Inscription impossible");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/etudiants/{idEtudiant}")
    public ResponseEntity<?> getInscriptionsEtudiant(@PathVariable("idEtudiant") int idEtudiant){
        try{
            ArrayList<Cours> cours = serviceInscription.getInscriptionsEtudiant(idEtudiant);
            return ResponseEntity.ok(cours);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/cours/{idCours/etudiants/{idEtudiant}/validation")
    public ResponseEntity<?> validerInscription(@PathVariable("idCours") int idCours, @PathVariable("idEtudiant") int idEtudiant){
        try{
            serviceInscription.validerInscription(idCours, idEtudiant);
            return ResponseEntity.ok().build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/cours/{idCours}/etudiants/{idEtudiant}/refus")
    public ResponseEntity<?> refuserInscription(@PathVariable("idCours") int idCours, @PathVariable("idEtudiant") int idEtudiant){
        try{
            serviceInscription.refuserInscription(idCours, idEtudiant);
            return ResponseEntity.ok().build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/cours/{idCours}/etudiants")
    public ResponseEntity<?> getEtudiantsInscrits(@PathVariable("idCours") int idCours){
        try{
            ArrayList<Utilisateur> etudiant = serviceInscription.getEtudiantsInscrits(idCours);
            return ResponseEntity.ok(etudiant);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
