package app.OwLearning.Services.Services;

import app.OwLearning.Domaine.Entités.Createur;
import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Domaine.Entités.Message;
import app.OwLearning.Domaine.Entités.Utilisateur;
import app.OwLearning.Domaine.Enumérations.StatutMessage;
import app.OwLearning.Domaine.Interfaces.IDiscussionRepository;
import app.OwLearning.Domaine.Interfaces.IMessageRepository;
import app.OwLearning.Domaine.Interfaces.IUtilisateurRepository;
import app.OwLearning.Services.Interfaces.IServiceDiscussion;
import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurNonAutorise;
import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurInexistant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
    private final IMessageRepository repositoryMessage;

    /**
     * Constructeur de ServiceDiscussion
     * @param repositoryDiscussion
     * @param repositoryUtilisateur
     */
    public ServiceDiscussion(IDiscussionRepository repositoryDiscussion, IUtilisateurRepository repositoryUtilisateur, IMessageRepository repositoryMessage)
    {
        this.repositoryDiscussion = repositoryDiscussion;
        this.repositoryUtilisateur = repositoryUtilisateur;
        this.repositoryMessage = repositoryMessage;
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

    @Override
    @Transactional
    public Discussion demarrerDiscussionAvecCreateur(int idUtilisateur, int idCreateur)
    {
        if (idUtilisateur == idCreateur)
        {
            throw new IllegalArgumentException("Impossible de créer une discussion avec soi-meme");
        }

        Utilisateur utilisateur = this.repositoryUtilisateur.trouverParId(idUtilisateur);
        if (utilisateur == null)
        {
            throw new ExceptionUtilisateurInexistant("L'utilisateur n'existe pas", idUtilisateur);
        }

        Utilisateur createur = this.repositoryUtilisateur.trouverParId(idCreateur);
        if (createur == null)
        {
            throw new ExceptionUtilisateurInexistant("Le créateur n'existe pas", idCreateur);
        }
        if (!(createur instanceof Createur))
        {
            throw new IllegalArgumentException("Le participant cible n'est pas un créateur");
        }

        return this.repositoryDiscussion.trouverDiscussionsParUtilisateurId(idUtilisateur)
                .stream()
                .filter(discussion -> discussion.getParticipants() != null)
                .filter(discussion -> discussion.getParticipants().size() == 2)
                .filter(discussion -> discussion.utilisateurFaitParti(idCreateur))
                .findFirst()
                .orElseGet(() -> this.repositoryDiscussion.sauvegarder(new Discussion(utilisateur, createur)));
    }

    @Override
    public List<Message> getMessagesDiscussion(int discussionId, int idUtilisateur) throws ExceptionUtilisateurNonAutorise
    {
        verifierAccesDiscussion(discussionId, idUtilisateur);
        return this.repositoryMessage.trouverParDiscussion(discussionId);
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
            log.warn("Échec de l'envoie du message : l'utilisateur {} n'existe pas", auteurId);
            throw new ExceptionUtilisateurInexistant("L'utilisateur n'existe pas", auteurId);
        }
        if (contenu == null || contenu.isBlank())
        {
            throw new IllegalArgumentException("Le contenu du message est obligatoire");
        }

        verifierParticipation(discussion, auteurId);
        Message message = new Message(contenu, auteur);
        message.setDateCreation(new Timestamp(System.currentTimeMillis()));
        message.setStatutMessage(StatutMessage.ENVOYE);
        discussion.ajouterMessage(message);
        discussion.getMessages().size();
        log.info("Message envoyé avec succès par l'utilisateur {} dans la discussion {}", auteurId, discussionId);
        return this.repositoryDiscussion.sauvegarder(discussion);
    }

    private Discussion verifierAccesDiscussion(int discussionId, int idUtilisateur) throws ExceptionUtilisateurNonAutorise
    {
        Discussion discussion = this.repositoryDiscussion.trouverDiscussionParId(discussionId);
        verifierParticipation(discussion, idUtilisateur);
        return discussion;
    }

    private void verifierParticipation(Discussion discussion, int idUtilisateur) throws ExceptionUtilisateurNonAutorise
    {
        if (!discussion.utilisateurFaitParti(idUtilisateur))
        {
            throw new ExceptionUtilisateurNonAutorise("Accès refuse", idUtilisateur, discussion.getIdDiscussion());
        }
    }
}
