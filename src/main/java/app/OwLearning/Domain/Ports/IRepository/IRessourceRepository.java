package Domain.Ports.IRepository;

import Domain.Models.Ressource;

public interface IRessourceRepository
{
    Ressource sauvegarder(Ressource ressource);
    Ressource trouverParId(int id);
    void supprimer(int id);
}
