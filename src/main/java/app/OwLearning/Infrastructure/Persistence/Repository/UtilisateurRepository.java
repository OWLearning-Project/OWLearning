package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
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
     * @return l'utilisateur trouvé
     */
    @Override
    public Utilisateur trouverParEmail(String email)
    {
        return jpaRepository.findByEmail(email);
    }

    /**
     * Méthode qui permet d'insérer un utilisateur en base
     * @param utilisateur
     * @return l'utilisateur inséré
     */
    @Override
    public Utilisateur sauvegarder(Utilisateur utilisateur)
    {
        return jpaRepository.save(utilisateur);
    }

    /**
     * Cette méthode permet de trouver un utilisateur via son identifiant
     * @param id identifiant de user
     * @return l'utilisateur trouvé
     */
    @Override
    public Utilisateur trouverParId(int id)
    {
        return jpaRepository.findById(id).orElse(null);
    }

    /**
     * cette méthode permet de mettre à jour les infos d'un utilisateur
     * @param utilisateur l'utilisateur
     * @return les données mises à jour
     */
    @Override
    public int mettreAJour(Utilisateur utilisateur)
    {
        Utilisateur sauvegarde = jpaRepository.save(utilisateur);
        // On retourne 1 si ça a été sauvegardé correctement, sinon 0
        return (sauvegarde != null) ? 1 : 0;
    }
}
