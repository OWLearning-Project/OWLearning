package Infrastructure.Persistence.Repository;

import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import org.springframework.stereotype.Component;

/**
 * Classe UtilisateurRepository qui permet d'intéragir avec la base de données
 */
@Component
public class UtilisateurRepository implements IUtilisateurRepository
{
    private final JpaUtilisateurRepository jpaRepository;

    /**
     * Constructeur de UtilisateurRepository
     * @param jpaRepository
     */
    public UtilisateurRepository (JpaUtilisateurRepository jpaRepository)
    {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Méthode qui permet de trouver un Utilisateur vie son email
     * @param email
     * @return l'Utilisateur trouvé
     */
    @Override
    public Utilisateur trouverParEmail(String email)
    {
        return jpaRepository.findByEmailNative(email);
    }

    /**
     * Cette méthode permet de trouver un utilisateur via son identifiant
     * @param id identifiant de user
     * @return l'utilisateur trouvé
     */
    @Override
    public Utilisateur trouverParId(int id)
    {
        return jpaRepository.findByIdNative(id);
    }

    /**
     * cette méthode permet de mettre à jour les infos d'un utilisateur
     * @param utilisateur l'utilisateur
     * @return les données mises à jour
     */
    @Override
    public int mettreAJour(Utilisateur utilisateur)
    {
        return jpaRepository.updateProfilNative(
                utilisateur.getId(),
                utilisateur.getPseudo(),
                utilisateur.getEmail()
        );
    }

}
