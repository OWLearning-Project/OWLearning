package Application.Services;
import Domain.Models.Utilisateur;
import Domain.Ports.IRepository.IUtilisateurRepository;


public class ServiceUtilisateur {


    private final IUtilisateurRepository utilisateurRepository;


    public ServiceUtilisateur(IUtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }


    public Utilisateur getProfil(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("L'identifiant n'est pas valide");
        }


        Utilisateur utilisateur = utilisateurRepository.trouverParId(id);


        if (utilisateur == null) {
            throw new IllegalStateException("Utilisateur introuvable");
        }


        return utilisateur;
    }


    public void modifierProfil(int id, String pseudo, String email) {
        if (id <= 0) {
            throw new IllegalArgumentException("l'identifiant n'est pas valide");
        }


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


