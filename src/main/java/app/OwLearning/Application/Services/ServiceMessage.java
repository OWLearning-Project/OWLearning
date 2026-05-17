package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Ports.IRepository.IMessageRepository;
import app.OwLearning.Domain.Ports.IRepository.IRessourceRepository;
import app.OwLearning.Application.Ports.IServices.IServiceMessage;
import app.OwLearning.Application.Exceptions.ExceptionMessageIntrouvable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        log.debug("Demande de récuperation des messages de la discussion ID {}",idDiscussion);

        List<Message> messages = messageRepository.trouverParDiscussion(idDiscussion);
        log.debug("{} message(s) récupéré(s) pour la discussion ID {}", messages.size(), idDiscussion);

        return messages;
    }

    /**
     * Méthode qui permet d'ajouter une ressource uploadée à un message
     * @param idMessage
     * @param idRessource
     */
    public void ajouterRessource(int idMessage, int idRessource)
    {
        log.debug("Demande d'ajout de la ressource ID {} au message ID {}", idRessource, idMessage);
        Message message = messageRepository.trouverParId(idMessage);
        if (message == null)
        {
            log.warn("Echec de l'ajout: le message ID {} est introuvable", idMessage);
            throw new ExceptionMessageIntrouvable("Le message est introuvable ",idMessage);
        }
        Ressource ressource = ressourceRepository.trouverParId(idRessource);
        message.ajouterRessource(ressource);
        messageRepository.sauvegarder(message);
        log.info("La ressource ID {} a été ajoutée au message ID {}", idRessource, idMessage);
    }

    /**
     * Méthode qui permet de retirer une ressource à un message
     * @param idMessage
     * @param idRessource
     * @return la ressource supprimer
     */
    public Ressource supprimerRessource(int idMessage, int idRessource)
    {
        log.debug("Demande de retirer la ressource ID {} au message ID {}", idRessource, idMessage);
        Message message = messageRepository.trouverParId(idMessage);
        if (message == null)
        {
            log.warn("Echec de la suppression du lien ressource - message: message ID {} est introuvale", idMessage);
            throw new ExceptionMessageIntrouvable("Le message est introuvable ",idMessage);
        }
        Ressource ressource = message.retirerRessource(idRessource);
        messageRepository.sauvegarder(message);
        log.info("Lien entre la ressource ID {} et le message ID {} supprimé", idRessource, idMessage);
        return ressource;
    }
}
