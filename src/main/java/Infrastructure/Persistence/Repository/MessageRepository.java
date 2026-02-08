package Infrastructure.Persistence.Repository;

import Domain.Models.Message;
import Domain.Models.Ressource;
import Domain.Ports.IRepository.IMessageRepository;
import Infrastructure.Persistence.Interface.JpaMessageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
public class MessageRepository implements IMessageRepository
{
    private final JpaMessageRepository jpaMessageRepository;

    public MessageRepository(JpaMessageRepository jpaMessageRepository)
    {
        this.jpaMessageRepository = jpaMessageRepository;
    }

    @Override
    @Transactional
    public Message sauvegarder(Message message)
    {
        if (message.getId_message() == 0)
        {
            jpaMessageRepository.ajouterMessageNative(
                    message.getContenu(),
                    message.getDate_creation(),
                    message.getStatutMessage().getLabel(),
                    message.getDiscussion() != null ? message.getDiscussion().getId() : 0,
                    message.getUtilisateur().getId()
            );
            Message messageSauvegarde = jpaMessageRepository.trouverParAuteurEtParDateNative(
                    message.getUtilisateur().getId(),
                    message.getDate_creation()
            );

            if (messageSauvegarde == null)
            {
                throw new RuntimeException("Erreur : Impossible de récupérer le message inséré.");
            }
            for (Ressource ressource : message.getRessources())
            {
                jpaMessageRepository.ajouterLiaisonPieceJointeNative(
                        messageSauvegarde.getId_message(),
                        ressource.getId_ressource()
                );
            }
            messageSauvegarde.setRessources(messageSauvegarde.getRessources());
            return messageSauvegarde;
        }
        else
        {
            return jpaMessageRepository.save(message);
        }
    }

    @Override
    public Message trouverParId(int id)
    {
        return jpaMessageRepository.trouverParMessageIdNative(id);
    }

    @Override
    public ArrayList<Message> trouverParDiscussion(int id)
    {
        return jpaMessageRepository.trouverParDiscussionIdNative(id);
    }

    @Override
    public void supprimer(int id)
    {
        jpaMessageRepository.supprimerParIdNative(id);
    }
}
