package app.OwLearning.Api.Cours;

import app.OwLearning.Domain.Models.Categorie;
import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Ports.IServices.IServiceCours;
import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Shared.DTO.ChapitreDTO;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisIdChapitre;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisLabelCategorie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Controller CoursController permettant d'accéder aux données liées aux cours
 */
@RestController
@RequestMapping("/api/cours")
public class CoursController {
    private final IServiceCours serviceCours;

    public CoursController(IServiceCours serviceCours) {
        this.serviceCours = serviceCours;
    }

    /**
     * Récupération des cours publiés avec une méthode GET
     *
     * @return la liste des cours au format JSON
     */
    @GetMapping
    public ResponseEntity<ArrayList<Cours>> getCoursPublies() {
        return ResponseEntity.ok(serviceCours.getCoursPublies());
    }

    /**
     * Récupération des cours créés par un créateur via son id
     *
     * @param idCreateur id du créateur
     * @return la liste des cours créés au format JSON
     */
    @GetMapping("/utilisateurs/{idCreateur}/publications")
    public ResponseEntity<ArrayList<Cours>> getCoursCrees(@PathVariable("idCreateur") int idCreateur) {
        return ResponseEntity.ok(serviceCours.getCoursCrees(idCreateur));
    }

    /**
     * Récupération des cours où un élève est inscrit via son id
     *
     * @param idEleve id de l'élève
     * @return la liste des cours inscrits au format JSON
     */
    @GetMapping("/utilisateurs/{idEleve}/inscriptions")
    public ResponseEntity<ArrayList<Cours>> getCoursInscriptions(@PathVariable("idEleve") int idEleve) {
        return ResponseEntity.ok(serviceCours.getCoursInscrits(idEleve));
    }

    /**
     * Récupération d'un cours par son id
     *
     * @param idCours du cours
     * @return le cours, une erreur http Not_found si le cours n'existe pas, une erreur http Bad_request sinon
     */
    @GetMapping("/{idCours}")
    public ResponseEntity<?> getCoursParId(@PathVariable("idCours") int idCours) {
        try {
            Cours cours = serviceCours.getCoursParId(idCours);
            return ResponseEntity.ok(cours);
        } catch (ExceptionCoursInexistant e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }


    @PostMapping("/{idCours}/chapitres")
    public ResponseEntity<?> ajouterChapitre(@PathVariable int coursId, @RequestBody ChapitreDTO chapitreDto) {
        try {
            Chapitre nouveauChapitre = new Chapitre(chapitreDto.getTitre(), chapitreDto.getDescription(), new ArrayList<>());
            serviceCours.ajouterChapitre(coursId, nouveauChapitre);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCours}/chapitres/{idChapitre}")
    public ResponseEntity<?> retirerChapitre(@PathVariable("idCours") int coursId, @PathVariable("idChapitre") int chapitreId) {
        try {
            Chapitre chapitreRetirer = serviceCours.retirerChapitre(coursId, chapitreId);
            return ResponseEntity.ok(chapitreRetirer);
        } catch (ExceptionMauvaisIdChapitre e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{idCours}/categories")
    public ResponseEntity<?> ajouterCategorie(@PathVariable int coursId, @RequestBody Categorie uneCategorie) {
        try {
            serviceCours.ajouterCategorieCours(coursId, uneCategorie);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCours}/categories/{nomCategorie}")
    public ResponseEntity<?> supprimerCategorie(@PathVariable("idCours") int coursId, @PathVariable("nomCategorie") Categorie uneCategorie) {
        try {
            Categorie categorieSupprimer = serviceCours.supprimerCategorieCours(coursId, uneCategorie);
            return ResponseEntity.ok(categorieSupprimer);
        } catch (ExceptionMauvaisLabelCategorie e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}