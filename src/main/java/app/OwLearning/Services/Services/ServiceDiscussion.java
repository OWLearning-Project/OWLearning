package app.OwLearning.Services.Services;

import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Domaine.Entités.Message;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Domaine.Interfaces.IDiscussionRepository;
import app.OwLearning.Domaine.Interfaces.IUtilisateurRepository;
import app.OwLearning.Services.Interfaces.IServiceDiscussion;
import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurNonAutorise;
import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurInexistant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
/**
 * Classe ServiceDiscussion, permet de gérer les traitements de discussions
 */
@Service
public class ServiceDiscussion implements IServiceDiscussion
{
    private final IDiscussionRepository repositoryDiscussion;
    private final IUtilisateurRepository repositoryUtilisateur;

    /**
     * Constructeur de ServiceDiscussion
     * @param repositoryDiscussion
     * @param repositoryUtilisateur
     */
    public ServiceDiscussion(IDiscussionRepository repositoryDiscussion, IUtilisateurRepository repositoryUtilisateur)
    {
        this.repositoryDiscussion = repositoryDiscussion;
        this.repositoryUtilisateur = repositoryUtilisateur;
    }

    /**
     * Méthode qui permet de récupérer les discussions d'un utilisateur
     * @param idUtilisateur id de l'utilisateur
     * @return la liste des discussions
     */
    @Override
    public List<Discussion> getDiscussionsParIdUtilisateur(int idUtilisateur)
    {
        log.info("Récupération des discussions de l'utilisateur {}", idUtilisateur);
        return this.repositoryDiscussion.trouverDiscussionsParUtilisateurId(idUtilisateur);
    }

    /**
     * Méthode qui permet d'envoyer un message d'un utilisateur
     * @param discussionId id de la discussion
     * @param auteurId id de l'utilisateur auteur
     * @param contenu contenu du message
     * @return la discussion avec le nouveau message envoyé
     * @throws ExceptionUtilisateurNonAutorise
     */
    @Override
    @Transactional
    public Discussion envoyerMessage(int discussionId, int auteurId, String contenu) throws ExceptionUtilisateurNonAutorise, ExceptionUtilisateurInexistant
    {
        Discussion discussion = this.repositoryDiscussion.trouverDiscussionParId(discussionId);
        Utilisateur auteur = this.repositoryUtilisateur.trouverParId(auteurId);
        if (auteur == null) {
            log.warn("Echec de l'envoie du message : l'utilisateur {} n'existe pas", auteurId);
            throw new ExceptionUtilisateurInexistant("L'utilisateur n'existe pas", auteurId);
        }
        Message message = new Message(contenu, auteur);
        discussion.ajouterMessage(message);
        discussion.getMessages().size();
        log.info("Message envoyé avec succès par l'utilisateur {} dans la discussion {}", auteurId, discussionId);
        return this.repositoryDiscussion.sauvegarder(discussion);
    }
}
