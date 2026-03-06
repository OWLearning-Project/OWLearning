package app.OwLearning.Api.Cours;

import app.OwLearning.Domain.Models.Categorie;
import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IServices.IServiceCours;
import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Shared.DTO.ChapitreDTO;
import app.OwLearning.Shared.DTO.CoursCreationDTO;
import app.OwLearning.Shared.DTO.CoursModificationDTO;
import app.OwLearning.Shared.DTO.CoursAccesDTO;
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

    public CoursController(IServiceCours serviceCours)
    {
        this.serviceCours = serviceCours;
    }

    /**
     * Récupération des cours publiés avec une méthode GET
     *
     * @return la liste des cours au format JSON
     */
    @GetMapping
    public ResponseEntity<ArrayList<Cours>> getCoursPublies()
    {
        return ResponseEntity.ok(serviceCours.getCoursPublies());
    }

    /**
     * Récupération des cours créés par un créateur via son id
     *
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
     *
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

    @PostMapping
    public ResponseEntity<?> creerCours(@RequestBody CoursCreationDTO coursCreationDTO)
    {
        Cours cours = serviceCours.creerCours(coursCreationDTO.getTitre(), coursCreationDTO.getDescription(), coursCreationDTO.getDifficulte(),coursCreationDTO.getCreateurId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cours);
    }

    @PostMapping("/{idCours}/publication")
    public ResponseEntity<?> publierCours(@PathVariable("idCours") int idCours)
    {
        serviceCours.publierCours(idCours);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{idCours}")
    public ResponseEntity<?> modifierInformationsCours(@PathVariable("idCours") int idCours, @RequestBody CoursModificationDTO dto){
        try{
            serviceCours.modifierInformationsCours(idCours, dto.getTitre(), dto.getDescription());
            return ResponseEntity.ok().build();
        }
        catch(ExceptionCoursInexistant e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PutMapping("/{idCours/Acces}")
    public ResponseEntity<?> coursPrive(@PathVariable("idCours") int idCours, @RequestBody CoursAccesDTO dto) {
        try{
            serviceCours.coursPrive(idCours, dto.getEstPrive());
            return ResponseEntity.ok().build();
        }
        catch(ExceptionCoursInexistant e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCours}")
    public ResponseEntity<?> supprimerCours(@PathVariable("idCours") int idCours)
    {
        Cours coursSupprime = serviceCours.supprimerCours(idCours);
        return ResponseEntity.accepted().body("le cours a été supprimé");
    }


    /**
     * Ajout d'un chapitre dans un cours
     * @param coursId id du cours
     * @param chapitreDto chapitre (titre et description)
     * @return
     */
    @PostMapping("/{idCours}/chapitres")
    public ResponseEntity<?> ajouterChapitre(@PathVariable("idCours") int coursId, @RequestBody ChapitreDTO chapitreDto) {

        Chapitre nouveauChapitre = new Chapitre(chapitreDto.getTitre(), chapitreDto.getDescription(), new ArrayList<Ressource>());
        serviceCours.ajouterChapitre(coursId, nouveauChapitre);
        return ResponseEntity.status(HttpStatus.CREATED).body("Le chapitre a été crée");
    }

    /**
     * Supprimer un chapitre associé à un cours
     * @param coursId
     * @param chapitreId
     * @return
     */
    @DeleteMapping("/{idCours}/chapitres/{idChapitre}")
    public ResponseEntity<?> retirerChapitre(@PathVariable("idCours") int coursId, @PathVariable("idChapitre") int chapitreId) throws ExceptionMauvaisIdChapitre {
        boolean chapitreRetiree = serviceCours.retirerChapitre(coursId, chapitreId);
        if (chapitreRetiree)
            return ResponseEntity.accepted().body("le chapitre a été retiré");
        return ResponseEntity.badRequest().body("Le chapitre n'a pas pu être retiré");
    }

    /**
     * Ajouter une catégorie à un cours
     * @param coursId
     * @param uneCategorie
     * @return
     */
    @PostMapping("/{idCours}/categories")
    public ResponseEntity<?> ajouterCategorie(@PathVariable("idCours") int coursId, @RequestBody Categorie uneCategorie) {
        serviceCours.ajouterCategorieCours(coursId, uneCategorie);
        return ResponseEntity.accepted().body("La catégorie a été ajoutée au cours : " + coursId);
    }

    /**
     * Enlever une catégorie à un cours
     * @param coursId
     * @param uneCategorie
     * @return
     */
    @DeleteMapping("/{idCours}/categories/{nomCategorie}")
    public ResponseEntity<?> supprimerCategorie(@PathVariable("idCours") int coursId, @PathVariable("nomCategorie") Categorie uneCategorie) throws ExceptionMauvaisLabelCategorie {
        boolean categorieSupprimee = serviceCours.supprimerCategorieCours(coursId, uneCategorie);
        if (categorieSupprimee)
            return ResponseEntity.accepted().build();
        return ResponseEntity.badRequest().body("Le chapitre n'a pas pu être retiré");
    }
}