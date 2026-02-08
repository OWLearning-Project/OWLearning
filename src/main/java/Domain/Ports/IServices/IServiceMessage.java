package Domain.Ports.IServices;

import Domain.Models.Message;
import Domain.Models.Ressource;

import java.util.ArrayList;

public interface IServiceMessage
{
    public ArrayList<Message> trouverMessageParDiscussion(int idDiscussion);
    public void ajouterRessource(int idMessage, int idRessource);
    public Ressource supprimerRessource(int idMessage, int idRessource);
}
