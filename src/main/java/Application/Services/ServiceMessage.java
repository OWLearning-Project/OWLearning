package Application.Services;

import Domain.Models.Message;
import Domain.Models.Ressource;
import Domain.Ports.IRepository.IMessageRepository;
import Domain.Ports.IRepository.IRessourceRepository;
import Domain.Ports.IServices.IServiceMessage;
import Shared.Exceptions.ExceptionMessageIntrouvable;
import Shared.Exceptions.ExceptionRessourceIntrouvable;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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
    public ArrayList<Message> trouverMessageParDiscussion(int idDiscussion)
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
