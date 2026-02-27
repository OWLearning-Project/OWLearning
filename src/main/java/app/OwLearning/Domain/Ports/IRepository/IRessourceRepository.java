package app.OwLearning.Domain.Ports.IRepository;

import app.OwLearning.Domain.Models.Ressource;

public interface IRessourceRepository
{
    Ressource sauvegarder(Ressource ressource);
    Ressource trouverParId(int id);
    void supprimer(int id);
}
