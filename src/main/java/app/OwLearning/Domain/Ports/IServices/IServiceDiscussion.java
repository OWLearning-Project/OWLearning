package app.OwLearning.Domain.Ports.IServices;

import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Shared.Exceptions.ExceptionUtilisateurNonAutorise;

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
     * Méthode qui permet d'envoyer un message d'un utilisateur
     * @param discussionId id de la discussion
     * @param auteurId id de l'utilisateur auteur
     * @param contenu contenu du message
     * @return la discussion avec le nouveau message envoyé
     * @throws ExceptionUtilisateurNonAutorise
     */
    public abstract Discussion envoyerMessage(int discussionId, int auteurId, String contenu) throws ExceptionUtilisateurNonAutorise;
}
