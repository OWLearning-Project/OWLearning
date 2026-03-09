package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.Message;

import java.util.ArrayList;
import java.util.List;

public interface IMessageRepository
{
    Message sauvegarder(Message message);
    Message trouverParId(int id);
    List<Message> trouverParDiscussion(int id);
    void supprimer(int id);
}
