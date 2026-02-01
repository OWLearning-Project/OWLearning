package Infrastructure.Persistence.Repository;

import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;
import Infrastructure.Persistence.Interface.JpaUtilisateurRepository;
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
}
