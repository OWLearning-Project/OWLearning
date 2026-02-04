package Infrastructure.Persistence.Repository;

import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
import Shared.Exceptions.ExceptionUtilisateurInexistant;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurRepository implements IUtilisateurRepository
{
    private final JpaUtilisateurRepository jpaRepository;

    public UtilisateurRepository (JpaUtilisateurRepository jpaRepository)
    {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Utilisateur trouverParEmail(String email)
    {
        return jpaRepository.findByEmailNative(email);
    }

    @Override
    public int sauvegarder(Utilisateur utilisateur)
    {
        return jpaRepository.insertUtilisateurNative(utilisateur.getNom(), utilisateur.getPrenom(),
                                                     utilisateur.getEmail(), utilisateur.getMotDePasse());
    }

    @Override
    public int sauvegarderCreateur(int id)
    {
        return jpaRepository.insertCreateurNative(id);
    }

    @Override
    public int sauvegarderEleve(int id)
    {
        return jpaRepository.insertEleveNative(id);
    }

    @Override
    public int trouverIdParEmail(String email)
    {
        Integer id = jpaRepository.findIdByEmailNative(email);
        if (id == null)
            throw new ExceptionUtilisateurInexistant("L'utilisateur n'existe pas", email);
        return id;
    }
}
