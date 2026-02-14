package app.OwLearning.Domain.Ports.IServices;
import app.OwLearning.Domain.Models.Utilisateur;

public interface IServiceUtilisateur {
    /**
     * Cette méthode permet de récuperer le profil d'un utilisateur via son identifiant
     * @param id identifiant du user
     * @return l'utilisateur correspondant
     */
    public abstract Utilisateur getProfil(int id);

    /**
     * Cette methode permet de modifier les informations de l'utilisateur
     * @param id identifiant du user
     * @param pseudo on met le nouveau pseudo
     * @param email le nouveau email
     */
    public abstract void modifierProfil(int id, String pseudo, String email);

}

