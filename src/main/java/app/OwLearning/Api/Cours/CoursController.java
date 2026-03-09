package app.OwLearning.Api.Cours;

import app.OwLearning.Domain.Models.Categorie;
import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IServices.IServiceCours;
import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Infrastructure.Config.UtilisateurAuthentifie;
import app.OwLearning.Shared.DTO.ChapitreDTO;
import app.OwLearning.Shared.DTO.CoursCreationDTO;
import app.OwLearning.Shared.DTO.CoursModificationDTO;
import app.OwLearning.Shared.Exceptions.ExceptionCoursInexistant;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisIdChapitre;
import app.OwLearning.Shared.Exceptions.ExceptionMauvaisLabelCategorie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Controller CoursController permettant d'accéder aux données liées aux cours
 */
@RestController
@RequestMapping("/api/cours")
@PreAuthorize("isAuthenticated()")
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
     * @return la liste des cours créés au format JSON
     */
    @PreAuthorize("hasAuthority('CREATEUR')")
    @GetMapping("/utilisateurs/publications")
    public ResponseEntity<ArrayList<Cours>> getCoursCrees(@AuthenticationPrincipal UtilisateurAuthentifie utilisateurAuthentifie)
    {
        return ResponseEntity.ok(serviceCours.getCoursCrees(utilisateurAuthentifie.getId()));
    }

    /**
     * Récupération des cours où un élève est inscrit via son id
     *
     * @return la liste des cours inscrits au format JSON
     */
    @PreAuthorize("hasAuthority('ELEVE')")
    @GetMapping("/utilisateurs/inscriptions")
    public ResponseEntity<ArrayList<Cours>> getCoursInscriptions(@AuthenticationPrincipal UtilisateurAuthentifie utilisateurAuthentifie)
    {
        return ResponseEntity.ok(serviceCours.getCoursInscrits(utilisateurAuthentifie.getId()));
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

    @PreAuthorize("hasAuthority('CREATEUR')")
    @PostMapping
    public ResponseEntity<?> creerCours(@RequestBody CoursCreationDTO coursCreationDTO,@AuthenticationPrincipal UtilisateurAuthentifie utilisateurAuthentifie)
    {
        Cours cours = serviceCours.creerCours(coursCreationDTO.getTitre(), coursCreationDTO.getDescription(), coursCreationDTO.getDifficulte(),utilisateurAuthentifie.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cours);
    }

    @PreAuthorize("hasAuthority('CREATEUR')")
    @PostMapping("/{idCours}/publier")
    public ResponseEntity<?> publierCours(@PathVariable("idCours") int idCours)
    {
        serviceCours.publierCours(idCours);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('CREATEUR')")
    @PutMapping("/{idCours}")
    public ResponseEntity<?> modifierInformationsCours(@PathVariable("idCours") int idCours, @RequestBody CoursModificationDTO dto){
        serviceCours.modifierInformationsCours(idCours, dto.getTitre(), dto.getDescription(), dto.getDifficulte(), dto.getEstPrive());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('CREATEUR')")
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
    @PreAuthorize("hasAuthority('CREATEUR')")
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
    @PreAuthorize("hasAuthority('CREATEUR')")
    @DeleteMapping("/{idCours}/chapitres/{idChapitre}")
    public ResponseEntity<?> retirerChapitre(@PathVariable("idCours") int coursId, @PathVariable("idChapitre") int chapitreId) throws ExceptionMauvaisIdChapitre {
        serviceCours.retirerChapitre(coursId, chapitreId);
        return ResponseEntity.accepted().body("le chapitre a été retiré");
    }

    /**
     * Ajouter une catégorie à un cours
     * @param coursId
     * @param uneCategorie
     * @return
     */
    @PreAuthorize("hasAuthority('CREATEUR')")
    @PostMapping("/{idCours}/categories")
    public ResponseEntity<?> ajouterCategorie(@PathVariable("idCours") int coursId, @RequestBody Categorie uneCategorie) {
        serviceCours.ajouterCategorieCours(coursId, uneCategorie);
        return ResponseEntity.accepted().body("La catégorie a été ajoutée au cours : " + coursId);
    }

    /**
     * Enlever une catégorie à un cours
     * @param coursId
     * @param unNomCategorie
     * @return
     */
    @PreAuthorize("hasAuthority('CREATEUR')")
    @DeleteMapping("/{idCours}/categories/{nomCategorie}")
    public ResponseEntity<?> supprimerCategorie(@PathVariable("idCours") int coursId, @PathVariable("nomCategorie") String unNomCategorie) throws ExceptionMauvaisLabelCategorie {
        Categorie laCategorie = Categorie.stringEnCategorie(unNomCategorie);
        serviceCours.supprimerCategorieCours(coursId, laCategorie);
        return ResponseEntity.accepted().build();
    }
}