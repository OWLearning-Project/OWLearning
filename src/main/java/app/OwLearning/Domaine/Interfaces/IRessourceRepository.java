package app.OwLearning.Domaine.Interfaces;

import app.OwLearning.Domaine.Entités.Ressource;

public interface IRessourceRepository
{
    Ressource sauvegarder(Ressource ressource);
    Ressource trouverParId(int id);
    void supprimer(int id);
}
