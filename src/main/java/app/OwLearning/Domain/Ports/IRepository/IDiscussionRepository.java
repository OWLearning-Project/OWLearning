package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.Discussion;

import java.util.List;

/**
 * Interface IDiscussionRepository définissant le contrat pour la récupération des discussions
 */
public interface IDiscussionRepository
{
    /**
     * Méthdode pour trouver les discussions d'un utilisateur
     * @param utilisateurId id de l'utilisateur
     * @return la liste des discussions
     */
    public abstract List<Discussion> trouverDiscussionsParUtilisateurId(int utilisateurId);

    /**
     * Méthode pour trouver une discussion par son id
     * @param discussionId id de la discussion
     * @return l'objet discussion associé ou lance une exception sinon
     */
    public abstract Discussion trouverDiscussionParId(int discussionId);

    /**
     * Méthode pour sauvegarder ou mettre à jour une discussion
     * @param discussion
     * @return la discussion enregistrée
     */
    public abstract Discussion sauvegarder(Discussion discussion);
}
