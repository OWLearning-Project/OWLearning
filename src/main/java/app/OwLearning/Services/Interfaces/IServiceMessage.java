package app.OwLearning.Services.Interfaces;

import app.OwLearning.Domaine.Entités.Message;
import app.OwLearning.Domaine.Entités.Ressource;

import java.util.List;

public interface IServiceMessage
{
    public List<Message> trouverMessageParDiscussion(int idDiscussion);
    public void ajouterRessource(int idMessage, int idRessource);
    public Ressource supprimerRessource(int idMessage, int idRessource);
}
