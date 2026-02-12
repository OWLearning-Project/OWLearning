
package Domain.Ports.IServices;

import Domain.Models.Cours;
import Domain.Models.Utilisateur;

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
     * On récupère les cours auxquels un élève est inscrit
     *
     * @param idEtudiant id de l'élève
     * @return la liste de ses cours
     */
    public abstract ArrayList<Cours> getInscriptionsEtudiant(int idEtudiant);

    /**
     * On valide l'inscription d'un élève à un cours
     *
     * @param idCours id du cours
     * @param idEtudiant id de l'élève
     */
    public abstract void validerInscription(int idCours, int idEtudiant);

    /**
     * Refuser l'inscription d'un élève à un cours
     *
     * @param idCours id du cours
     * @param idEtudiant id de l'élève
     */
    public abstract void refuserInscription(int idCours, int idEtudiant);
    /**
     * Méthode qui récupére les élèves inscrits à un cours
     *
     * @param idCours id du cours
     * @return la liste des utilisateurs inscrits
     */
    public abstract ArrayList<Utilisateur> getEtudiantsInscrits(int idCours);
}
