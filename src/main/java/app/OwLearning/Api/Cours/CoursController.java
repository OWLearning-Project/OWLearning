package app.OwLearning.Api.Cours;

import app.OwLearning.Domain.Ports.IServices.IServiceCours;
import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
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
public class CoursController
{
    private final IServiceCours serviceCours;

    public CoursController (IServiceCours serviceCours)
    {
        this.serviceCours = serviceCours;
    }

    /**
     * Récupération des cours publiés avec une méthode GET
     * @return la liste des cours au format JSON
     */
    @GetMapping
    public ResponseEntity<ArrayList<Cours>> getCoursPublies()
    {
        return ResponseEntity.ok(serviceCours.getCoursPublies());
    }

    /**
     * Récupération des cours créés par un créateur via son id
     * @param idCreateur id du créateur
     * @return la liste des cours créés au format JSON
     */
    @GetMapping("/utilisateurs/{idCreateur}/publications")
    public ResponseEntity<ArrayList<Cours>> getCoursCrees(@PathVariable("idCreateur") int idCreateur)
    {
        return ResponseEntity.ok(serviceCours.getCoursCrees(idCreateur));
    }

    /**
     * Récupération des cours où un élève est inscrit via son id
     * @param idEleve id de l'élève
     * @return la liste des cours inscrits au format JSON
     */
    @GetMapping("/utilisateurs/{idEleve}/inscriptions")
    public ResponseEntity<ArrayList<Cours>> getCoursInscriptions(@PathVariable("idEleve") int idEleve)
    {
        return ResponseEntity.ok(serviceCours.getCoursInscrits(idEleve));
    }

    /**
     * Récupération d'un cours par son id
     * @param idCours du cours
     * @return le cours, une erreur http Not_found si le cours n'existe pas, une erreur http Bad_request sinon
     */
    @GetMapping("/{idCours}")
    public ResponseEntity<?> getCoursParId(@PathVariable("idCours") int idCours)
    {
        try
        {
            Cours cours = serviceCours.getCoursParId(idCours);
            return ResponseEntity.ok(cours);
        }
        catch(ExceptionCoursInexistant e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
