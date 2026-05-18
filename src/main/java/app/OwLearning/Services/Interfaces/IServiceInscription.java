package app.OwLearning.Services.Interfaces;

import app.OwLearning.Domaine.Exceptions.ExceptionMauvaisIdEleve;
import app.OwLearning.Domaine.Entités.Utilisateur;

import java.util.ArrayList;
public interface IServiceInscription {
    /**
     * cette méthode nous  permet d'inscrire un étudiant (élève) à un cours
     *
     * @param idEtudiant id de l'élève
     * @param idCours id du cours
     * @return un entier indiquant le résultat de l'inscription
     */
    public abstract int inscrireEtudiant(int idEtudiant, int idCours);


    /**
     * Refuser l'inscription d'un élève à un cours
     *
     * @param idCours id du cours
     * @param idEtudiant id de l'élève
     */
    public abstract void supprimerInscriptionCours(int idCours, int idEtudiant) throws ExceptionMauvaisIdEleve;
    /**
     * Méthode qui récupére les élèves inscrits à un cours
     *
     * @param idCours id du cours
     * @return la liste des utilisateurs inscrits
     */
    public abstract ArrayList<Utilisateur> getEtudiantsInscrits(int idCours);
}
