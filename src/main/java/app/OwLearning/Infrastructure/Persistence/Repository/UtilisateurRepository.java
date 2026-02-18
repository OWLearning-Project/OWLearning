package app.OwLearning.Infrastructure.Persistence.Repository;

import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurInexistant;
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
     * Méthode qui permet de trouver un utilisateur par son id
     * @param id id de l'utilisateur
     * @return l'objet Utilisateur associé ou lance une exception sinon
     */
    public Utilisateur trouverParId(int id)
    {
        return jpaRepository.findById(id).orElseThrow(() -> new ExceptionUtilisateurInexistant("L'utilisateur n'existe pas", id));
    }
}
