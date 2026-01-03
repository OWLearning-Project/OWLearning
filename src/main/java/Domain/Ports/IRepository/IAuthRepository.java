package Domain.Ports.IRepository;
import Domain.Models.Utilisateur;

public interface IAuthRepository 
{
    /*
    Recherche un utilisateur avec son email
    @param email L'email à chercher
    @return Utilisateur l'Utilisateur existant sinon null
    */
    public Utilisateur findByEmail(String email);

    /*
    Vérifie si un email existe déjà en base
    @param email L'email à vérif
    @return true si l'email est déjà pris
    */
    public boolean existsByEmail(String email);

    /*
    Sauvegarde un nouvel utilisateur
    @param Utilisateur l'objet à enregistrer
    @return L'uUtilisateur sauvegardé (ID généré avec JPA)
    */
    public Utilisateur save(Utilisateur utilisateur);
}
