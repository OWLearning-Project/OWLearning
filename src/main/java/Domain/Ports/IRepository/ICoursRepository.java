package Domain.Ports.IRepository;

import Domain.Models.Cours;

import java.util.List;

public interface ICoursRepository
{
    public List<Cours> trouverTousLesCours();

    public List<Cours> trouverParTitre(String titre);

    public Cours trouverParId(int id);

    public List<Cours> trouverParIdCreateur(int createurId);

    public List<Cours> trouverParIdEleve(int eleveId);
}
