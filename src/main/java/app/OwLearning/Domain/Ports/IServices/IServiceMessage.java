package app.OwLearning.Domain.Ports.IServices;

import app.OwLearning.Domain.Models.Message;
import app.OwLearning.Domain.Models.Ressource;

import java.util.ArrayList;
import java.util.List;

public interface IServiceMessage
{
    public List<Message> trouverMessageParDiscussion(int idDiscussion);
    public void ajouterRessource(int idMessage, int idRessource);
    public Ressource supprimerRessource(int idMessage, int idRessource);
}
