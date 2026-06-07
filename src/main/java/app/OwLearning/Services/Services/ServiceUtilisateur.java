package app.OwLearning.Services.Services;
import app.OwLearning.Domaine.Entités.Eleve;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Domaine.Interfaces.IUtilisateurRepository;
import app.OwLearning.Services.Interfaces.IServiceUtilisateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Le Service Utilisateur permet de gérer le traitement des utilisateurs
 */
@Slf4j
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
        log.debug("Demande de récupération du profil utilisateur {}", id);
        if (id <= 0){
            log.warn("La récupération du profil a échoué. L'identifiant {} n'est pas valide. ", id);
            {throw new IllegalArgumentException("L'identifiant n'est pas valide");}
        }

        Utilisateur utilisateur = utilisateurRepository.trouverParId(id);

        if (utilisateur == null) {
            log.warn("La récupération du profil a échoué. L'utilisateur {} est introuvable.", id);
            throw new IllegalStateException("Utilisateur introuvable");}

        log.debug("Profil utilisateur {} récupéré: {}", id, utilisateur);
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
        log.debug("Modification du profil utilisateur {}", id);
        if (id <= 0) {
            log.warn("La modification a échoué. L'identifiant {} n'est pas valide. ", id);
            throw new IllegalArgumentException("l'identifiant n'est pas valide");}

        Utilisateur utilisateur = utilisateurRepository.trouverParId(id);
        if (utilisateur == null) {
            log.warn("La modification a échoué. L'utilisateur {} est introuvable.", id);
            throw new IllegalStateException("Utilisateur introuvable");
        }

        if (pseudo != null && !pseudo.isBlank()) {
            utilisateur.setPseudo(pseudo);
        }

        if (email == null || email.isBlank()) {
            log.warn("La modification du profil {} a échoué. Le email n'est pas valide.", email);
            throw new IllegalArgumentException("l'email n'est pas valide");
        }

        Utilisateur autre = utilisateurRepository.trouverParEmail(email);
        if (autre != null && autre.getIdUtilisateur() != id) {
            log.warn("La modification du profil {} a échoué. L'email {} est déjà utilisé.", email, autre);
            throw new IllegalStateException("Email déjà Utilisé");
        }
        utilisateur.setEmail(email);

        if (utilisateur instanceof Eleve)
        {
            Eleve eleve = (Eleve)utilisateur;

            if (age == null || age < 0)
            {
                log.warn("Echec de la modification du profil {}. L'age n'est pas renseigné.", id);
                throw new IllegalArgumentException("l'age n'est pas valide");
            }
            eleve.setAge(age);
            if (niveauEtude == null || niveauEtude.isBlank())
            {
                log.warn("Echec de la modification du profil {}. Le niveau d'étude n'est pas renseigné.", id);
                throw new IllegalArgumentException("le niveau d'étude n'est pas valide");
            }
            eleve.setNiveauEtude(niveauEtude);
        }
        Utilisateur utilisateurModifie = utilisateurRepository.sauvegarder(utilisateur);
        log.info("Profil utilisateur {} modifié avec succès", id);
        return utilisateurModifie;
    }

    @Override
    public List<Utilisateur> getCreateurs()
    {
        log.debug("Demande de recuperation de l'annuaire des créateurs");
        return utilisateurRepository.trouverCreateurs();
    }
}
