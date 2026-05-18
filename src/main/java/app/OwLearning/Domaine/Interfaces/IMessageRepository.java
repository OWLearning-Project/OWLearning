package app.OwLearning.Domaine.Interfaces;

import app.OwLearning.Domaine.Entités.Message;

import java.util.List;

public interface IMessageRepository
{
    Message sauvegarder(Message message);
    Message trouverParId(int id);
    List<Message> trouverParDiscussion(int id);
    void supprimer(int id);
}
