package Application.Services;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceUtilisateur;
import org.springframework.stereotype.Service;

/**
 * Le Service Utilisateur permet de gérer le traitement des utilisateurs
 */
public class ServiceUtilisateur implements IServiceUtilisateur {


    private final IUtilisateurRepository utilisateurRepository;

    /**
     * Constructeur
     * @param utilisateurRepository
     */
    public ServiceUtilisateur(IUtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Cette méthode permet de récuperer le profil d'un utilisateur via son identifiant
     * @param id identifiant du user
     * @return l'utilisateur correspondant
     */
    @Override
    public Utilisateur getProfil(int id) {
        if (id <= 0) {throw new IllegalArgumentException("L'identifiant n'est pas valide");}

        Utilisateur utilisateur = utilisateurRepository.trouverParId(id);

        if (utilisateur == null) {throw new IllegalStateException("Utilisateur introuvable");}

        return utilisateur;
    }
    /**
     * Cette methode permet de modifier les informations de l'utilisateur
     * @param id identifiant du user
     * @param pseudo on met le nouveau pseudo
     * @param email le nouveau email
     */
    @Override
    public void modifierProfil(int id, String pseudo, String email) {
        if (id <= 0) {throw new IllegalArgumentException("l'identifiant n'est pas valide");}


        Utilisateur utilisateur = utilisateurRepository.trouverParId(id);
        if (utilisateur == null) {
            throw new IllegalStateException("Utilisateur introuvable");
        }


        if (pseudo != null && pseudo.isBlank() == false) {
            utilisateur.setPseudo(pseudo);
        }


        if (email != null && email.isBlank() == false) {
            Utilisateur autre = utilisateurRepository.trouverParEmail(email);
            if (autre != null && autre.getId() != id) {
                throw new IllegalStateException("Email déjà Utilisé");
            }
            utilisateur.setEmail(email);
        }


        int lignes = utilisateurRepository.mettreAJour(utilisateur);
        if (lignes <= 0) {
            throw new IllegalStateException("Echec de la mise à jour");
        }
    }
}
