package app.OwLearning.Services.Interfaces;

import app.OwLearning.Domaine.Exceptions.ExceptionUtilisateurNonAutorise;
import app.OwLearning.Domaine.Entités.Discussion;
import app.OwLearning.Domaine.Entités.Message;

import java.util.List;

/**
 * Interface IServiceDiscussion définissant le contrat pour le traitement des discussions
 */
public interface IServiceDiscussion
{

    /**
     * Méthode qui permet de récupérer les discussions d'un utilisateur
     * @param idUtilisateur id de l'utilisateur
     * @return la liste des discussions
     */
    public abstract List<Discussion> getDiscussionsParIdUtilisateur(int idUtilisateur);

    /**
     * Methode qui permet de créer ou récupérer une discussion entre un utilisateur et un créateur.
     * @param idUtilisateur id de l'utilisateur connecte
     * @param idCreateur id du créateur à contacter
     * @return la discussion existante ou créée
     */
    public abstract Discussion demarrerDiscussionAvecCreateur(int idUtilisateur, int idCreateur);

    /**
     * Methode qui permet de créer une discussion avec un eleve
     * @param IdUtilisateur id de l'utilisateur connecter
     * @param idDestinataire id de la personne que l'on veut contacter
     * @return la discission existante ou créée
     */
    public abstract Discussion demarrerDiscussion(int IdUtilisateur, int idDestinataire);
    /**
     * Methode qui permet de récupérer les messages d'une discussion si l'utilisateur y participe.
     * @param discussionId id de la discussion
     * @param idUtilisateur id de l'utilisateur connecte
     * @return la liste des messages
     * @throws ExceptionUtilisateurNonAutorise
     */
    public abstract List<Message> getMessagesDiscussion(int discussionId, int idUtilisateur) throws ExceptionUtilisateurNonAutorise;

    /**
     * Méthode qui permet d'envoyer un message d'un utilisateur
     * @param discussionId id de la discussion
     * @param auteurId id de l'utilisateur auteur
     * @param contenu contenu du message
     * @return la discussion avec le nouveau message envoyé
     * @throws ExceptionUtilisateurNonAutorise
     */
    public abstract Discussion envoyerMessage(int discussionId, int auteurId, String contenu, Integer ressourceId) throws ExceptionUtilisateurNonAutorise;
}
