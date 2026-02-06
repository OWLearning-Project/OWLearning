package Infrastructure.Persistence.Repository;

import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import Shared.Exceptions.ExceptionUtilisateurInexistant;
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
     * Méthode qui permet d'insérer un utilisateur en base
     * @param utilisateur
     * @return le nombre de lignes insérés
     */
    @Override
    public int sauvegarder(Utilisateur utilisateur)
    {
        return jpaRepository.insertUtilisateurNative(utilisateur.getNom(), utilisateur.getPrenom(),
                                                     utilisateur.getEmail(), utilisateur.getMotDePasse());
    }

    /**
     * Méthode sauvegarder pour le créateur
     * @param id
     * @return le nombre de lignes insérés
     */
    @Override
    public int sauvegarderCreateur(int id)
    {
        return jpaRepository.insertCreateurNative(id);
    }

    /**
     * Méthode sauvegarder pour l'éléve
     * @param id
     * @return le nombre de lignes insérés
     */
    @Override
    public int sauvegarderEleve(int id)
    {
        return jpaRepository.insertEleveNative(id);
    }

    /**
     * Méthode qui permet de trouver un Id grace à l'email
     * @param email
     * @return l'ID trouver
     */
    @Override
    public int trouverIdParEmail(String email)
    {
        Integer id = jpaRepository.findIdByEmailNative(email);
        if (id == null)
            throw new ExceptionUtilisateurInexistant("L'utilisateur n'existe pas", email);
        return id;
    }
}
