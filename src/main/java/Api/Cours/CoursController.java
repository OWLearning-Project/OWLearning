package Api.Cours;

import Domain.Models.Cours;
import Domain.Ports.IServices.IServiceCours;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
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
     * @return la liste des cours au format JSON, une erreur http Bad_request
     */
    @GetMapping
    public ResponseEntity<?> getCoursPublies(@RequestParam(required = false, value = "titre") String titre,
                                            @RequestParam(required = false, value = "createur") String nomCreateur,
                                            @RequestParam(required = false, value = "difficulte") String difficulte,
                                            @RequestParam(required = false, value = "categorie") String categorie,
                                            @RequestParam(required = false, value = "prive") String estPrive)
    {
        try
        {
            Boolean prive;
            if (estPrive != null)
                prive = Boolean.valueOf(estPrive);
            else
                prive = null;
            return ResponseEntity.ok(serviceCours.getLesCours(titre, nomCreateur, difficulte, categorie, prive));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les paramètres ne sont pas valides");
        }
    }

    /**
     * Récupération des cours créés par un créateur via son id
     * @param idCreateur id du créateur en chaine de caractères
     * @return la liste des cours crées au format JSON, une erreur http Bad_request sinon
     */
    @GetMapping("/utilisateurs/{idCreateur}/publications")
    public ResponseEntity<?> getCoursCrees(@PathVariable("idCreateur") String idCreateur,
                                          @RequestParam(required = false, value = "titre") String titre,
                                          @RequestParam(required = false, value = "difficulte") String difficulte,
                                          @RequestParam(required = false, value = "categorie") String categorie,
                                          @RequestParam(required = false, value = "prive") String estPrive)
    {
        try
        {
            int idCreateurInt = parseInt(idCreateur);
            Boolean prive;
            if (estPrive != null)
                prive = Boolean.valueOf(estPrive);
            else
                prive = null;
            return ResponseEntity.ok(serviceCours.getCoursCrees(titre, idCreateurInt, difficulte, categorie, prive));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les paramètres ne sont pas valides");
        }
    }

    /**
     * Récupération des cours où un élève est inscrit via son id
     * @param idEleve id de l'élève en chaine de caractères
     * @return la liste des cours inscrits au format JSON, une erreur http Bad_request sinon
     */
    @GetMapping("/utilisateurs/{idEleve}/inscriptions")
    public ResponseEntity<?> getCoursInscriptions(@PathVariable("idEleve") String idEleve,
                                                 @RequestParam(required = false, value = "titre") String titre,
                                                 @RequestParam(required = false, value = "createur") String nomCreateur,
                                                 @RequestParam(required = false, value = "difficulte") String difficulte,
                                                 @RequestParam(required = false, value = "categorie") String categorie,
                                                 @RequestParam(required = false, value = "prive") String estPrive)
    {
        try
        {
            int idEleveInt = parseInt(idEleve);
            Boolean prive;
            if (estPrive != null)
                prive = Boolean.valueOf(estPrive);
            else
                prive = null;
            return ResponseEntity.ok(serviceCours.getCoursInscrits(idEleveInt, titre, nomCreateur, difficulte, categorie, prive));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les paramètres ne sont pas valides");
        }
    }

    /**
     * Récupération d'un cours par son id
     * @param id du cours en chaine de caractères
     * @return le cours, une erreur http Not_found si le cours n'existe pas, une erreur http Bad_request sinon
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCoursParId(@PathVariable("id") String id)
    {
        try
        {
            int idInt = parseInt(id);
            Cours cours = serviceCours.getCoursParId(idInt);
            if(cours == null)
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun cours n'existe à cet id");
            return ResponseEntity.ok(cours);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id n'est pas du bon type");
        }
    }
}
