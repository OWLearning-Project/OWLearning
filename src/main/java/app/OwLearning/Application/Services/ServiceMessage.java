package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IRepository.IMessageRepository;
import app.OwLearning.Domain.Ports.IRepository.IRessourceRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceMessage;
import app.OwLearning.Shared.Exceptions.ExceptionMessageIntrouvable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceMessage implements IServiceMessage
{

    private final IMessageRepository messageRepository;
    private final IRessourceRepository ressourceRepository;

    public ServiceMessage(IMessageRepository messageRepository, IRessourceRepository ressourceRepository)
    {
        this.messageRepository = messageRepository;
        this.ressourceRepository = ressourceRepository;
    }

    /**
     * Méthode qui permet de récupérer tous les messages associés à une discussion
     * @param idDiscussion
     * @return la liste de messages
     */
    public List<Message> trouverMessageParDiscussion(int idDiscussion)
    {
        return messageRepository.trouverParDiscussion(idDiscussion);
    }

    /**
     * Méthode qui permet d'ajouter une ressource uploadée à un message
     * @param idMessage
     * @param idRessource
     */
    public void ajouterRessource(int idMessage, int idRessource)
    {
        Message message = messageRepository.trouverParId(idMessage);
        if (message == null)
        {
            throw new ExceptionMessageIntrouvable("Le message est introuvable ",idMessage);
        }
        Ressource ressource = ressourceRepository.trouverParId(idRessource);
        message.ajouterRessource(ressource);
        messageRepository.sauvegarder(message);
    }

    /**
     * Méthode qui permet de retirer une ressource à un message
     * @param idMessage
     * @param idRessource
     * @return la ressource supprimer
     */
    public Ressource supprimerRessource(int idMessage, int idRessource)
    {
        Message message = messageRepository.trouverParId(idMessage);
        if (message == null)
        {
            throw new ExceptionMessageIntrouvable("Le message est introuvable ",idMessage);
        }
        Ressource ressource = message.retirerRessource(idRessource);
        messageRepository.sauvegarder(message);
        return ressource;
    }
}
