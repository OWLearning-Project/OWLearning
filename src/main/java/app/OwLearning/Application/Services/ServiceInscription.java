package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Domain.Models.Eleve;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.ICoursRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceInscription;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        if (idEtudiant <= 0) throw new IllegalArgumentException("Identifiant étudiant invalide");
        if (idCours <= 0) throw new IllegalArgumentException("Identifiant cours invalide");

        Cours cours = coursRepository.trouverParId(idCours);
        Eleve eleve = (Eleve) utilisateurRepository.trouverParId(idEtudiant);
        cours.ajouterEleve(eleve);
        coursRepository.sauvegarder(cours);

        return cours.getId();
    }

    /**
     * On valide l'inscription d'un élève à un cours
     *
     * @param idCours id du cours
     * @param idEtudiant id de l'élève
     */
    @Override
    public void validerInscription(int idCours, int idEtudiant)
    {
        if (idCours <= 0) throw new IllegalArgumentException("Identifiant cours invalide");
        if (idEtudiant <= 0) throw new IllegalArgumentException("Identifiant étudiant invalide");

        coursRepository.validerInscription(idCours, idEtudiant);
    }

    /**
     * Refuser l'inscription d'un élève à un cours
     *
     * @param idCours id du cours
     * @param idEtudiant id de l'élève
     */
    @Override
    public void refuserInscription(int idCours, int idEtudiant)
    {
        if (idCours <= 0) throw new IllegalArgumentException("Identifiant cours invalide");
        if (idEtudiant <= 0) throw new IllegalArgumentException("Identifiant étudiant invalide");

        coursRepository.refuserInscription(idCours, idEtudiant);
    }

    /**
     * Méthode qui récupére les élèves inscrits à un cours
     *
     * @param idCours id du cours
     * @return la liste des utilisateurs inscrits
     */
    @Override
    public ArrayList<Utilisateur> getEtudiantsInscrits(int idCours)
    {
        if (idCours <= 0) throw new IllegalArgumentException("Identifiant cours invalide");

        return utilisateurRepository.trouverEtudiantsInscrits(idCours);
    }
}
