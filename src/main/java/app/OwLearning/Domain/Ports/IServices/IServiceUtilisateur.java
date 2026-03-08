package app.OwLearning.Domain.Ports.IServices;
import app.OwLearning.Domain.Models.Utilisateur;

/**
 * Interface IServiceUtilisateur définissant le contrat pour le traitement des utilisateurs
 */
public interface IServiceUtilisateur {
    /**
     * Cette méthode permet de récupérer le profil d'un utilisateur via son identifiant
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur correspondant
     */
    public abstract Utilisateur getProfil(int id);

    /**
     * Cette methode permet de modifier les informations de l'utilisateur
     * @param id identifiant de l'utilisateur
     * @param pseudo le nouveau pseudo
     * @param email le nouveau email
     * @param age l'age
     * @param niveauEtude le niveau d'étude
     * @return l'utilisateur modifié
     */
    public abstract Utilisateur modifierProfil(int id, String pseudo, String email, Integer age, String niveauEtude);

}

