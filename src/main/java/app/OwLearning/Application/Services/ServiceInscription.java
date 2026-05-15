package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Domain.Models.Eleve;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Application.Ports.IServices.IServiceInscription;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Domain.Exceptions.ExceptionMauvaisIdEleve;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Slf4j
@Service
public class ServiceInscription implements IServiceInscription
{
    private final ICoursRepository coursRepository;
    private final app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository utilisateurRepository;

    /**
     * Constructeur du ServiceInscription
     *
     * @param coursRepository repository des cours
     * @param utilisateurRepository repository des utilisateurs
     */
    public ServiceInscription(ICoursRepository coursRepository, IUtilisateurRepository utilisateurRepository)
    {
        this.coursRepository = coursRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * cette méthode nous  permet d'inscrire un étudiant (élève) à un cours
     *
     * @param idEtudiant id de l'élève
     * @param idCours id du cours
     * @return un entier indiquant le résultat de l'inscription
     */
    @Override
    public int inscrireEtudiant(int idEtudiant, int idCours)
    {
        log.debug("Inscription de l'étudiant {} au cours {}", idEtudiant, idCours);
        if (idEtudiant <= 0){
            log.warn("Echec de l'inscription. L'identifiant étudiant{} n'est pas valide.", idEtudiant);
            throw new IllegalArgumentException("Identifiant étudiant invalide");
        }
        if (idCours <= 0){
            log.warn("Echec de l'inscription. L'identifiant cours{} n'est pas valide.", idCours);
            throw new IllegalArgumentException("Identifiant cours invalide");
        }

        Cours cours = coursRepository.trouverParId(idCours);
        Eleve eleve = (Eleve) utilisateurRepository.trouverParId(idEtudiant);
        cours.ajouterEleve(eleve);
        coursRepository.sauvegarder(cours);

        log.info("Inscription de l'étudiant {} au cours {} a réussi.", idEtudiant, idCours);
        return cours.getId();
    }

    /**
     * Refuser l'inscription d'un élève à un cours
     *
     * @param idCours id du cours
     * @param idEtudiant id de l'élève
     */
    @Override
    public void supprimerInscriptionCours(int idCours, int idEtudiant) throws ExceptionMauvaisIdEleve {
        log.debug("Demande de suppression de l'inscription de l'étudiant{} au cours {}", idEtudiant, idCours);
        if (idCours <= 0){
            log.warn("Echec de la suppression. L'identifiant cours {} est invalide.",idCours);
            throw new IllegalArgumentException("Identifiant cours invalide");
        }
        if (idEtudiant <= 0){
            log.warn("Echec de la suppression. L'identifiant étudiant {} est invalide.", idEtudiant);
            throw new IllegalArgumentException("Identifiant étudiant invalide");
        }

        Cours cours = coursRepository.trouverParId(idCours);
        cours.supprimerEleve(idEtudiant);
        coursRepository.sauvegarder(cours);

        log.info("Suppression de l'étudiant {} au cours {} réussie.", idEtudiant, idCours);
    }

    /**
     * Méthode qui récupére les élèves inscrits à un cours
     * @param idCours id du cours
     * @return la liste des utilisateurs inscrits
     */
    @Override
    public ArrayList<Utilisateur> getEtudiantsInscrits(int idCours)
    {
        log.debug("Récupération des étudiants inscrits au cours {}",  idCours);
        if (idCours <= 0){
            log.warn("La récupération des étudiants a échoué");
            throw new IllegalArgumentException("Identifiant cours invalide");
        }

        Cours cours = coursRepository.trouverParId(idCours);
        log.info("Récupération de {} étudiants inscrits au cours {}", cours.getEleves().size(), idCours);
        return new ArrayList<>(cours.getEleves());
    }
}
