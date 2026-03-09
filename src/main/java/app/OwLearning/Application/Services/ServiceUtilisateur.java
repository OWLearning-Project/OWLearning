package app.OwLearning.Application.Services;
import app.OwLearning.Domain.Models.Eleve;
import app.OwLearning.Domain.Models.Utilisateur;
import app.OwLearning.Domain.Ports.IRepository.IUtilisateurRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceUtilisateur;
import org.springframework.stereotype.Service;

/**
 * Le Service Utilisateur permet de gérer le traitement des utilisateurs
 */
@Service
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
     * Cette méthode permet de récupérer le profil d'un utilisateur via son identifiant
     * @param id identifiant de l'utilisateur
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
     * @param id identifiant de l'utilisateur
     * @param pseudo on met le nouveau pseudo
     * @param email le nouveau email
     * @param age l'age
     * @param niveauEtude le niveau d'étude
     * @return l'utilisateur modifié
     */
    @Override
    public Utilisateur modifierProfil(int id, String pseudo, String email, Integer age, String niveauEtude) {
        if (id <= 0) {throw new IllegalArgumentException("l'identifiant n'est pas valide");}

        Utilisateur utilisateur = utilisateurRepository.trouverParId(id);
        if (utilisateur == null) {
            throw new IllegalStateException("Utilisateur introuvable");
        }

        if (pseudo == null || pseudo.isBlank()) {
            throw new IllegalArgumentException("le pseudo n'est pas valide");
        }

        utilisateur.setPseudo(pseudo);

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("l'email n'est pas valide");
        }

        Utilisateur autre = utilisateurRepository.trouverParEmail(email);
        if (autre != null && autre.getId() != id) {
            throw new IllegalStateException("Email déjà Utilisé");
        }
        utilisateur.setEmail(email);

        if (utilisateur instanceof Eleve)
        {
            Eleve eleve = (Eleve)utilisateur;

            if (age == null || age < 0)
            {
                throw new IllegalArgumentException("l'age n'est pas valide");
            }
            eleve.setAge(age);
            if (niveauEtude == null || niveauEtude.isBlank())
            {
                throw new IllegalArgumentException("le niveau d'étude n'est pas valide");
            }
            eleve.setNiveauEtude(niveauEtude);
        }

        return utilisateurRepository.sauvegarder(utilisateur);
    }
}
