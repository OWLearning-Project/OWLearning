package app.OwLearning.Application.Services;

import app.OwLearning.Domain.Models.Ressource;
import app.OwLearning.Domain.Models.TypeRessource;
import app.OwLearning.Domain.Ports.IRepository.IRessourceRepository;
import app.OwLearning.Domain.Ports.IServices.IServiceRessource;
import app.OwLearning.Shared.Exceptions.ExceptionRessourceIntrouvable;
import app.OwLearning.Shared.Exceptions.ExceptionRessourceIntrouvableDansChap;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ServiceRessource implements IServiceRessource {

    private final IRessourceRepository repository;

    public ServiceRessource(IRessourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ressource getContenuRessource(int id) {
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null) {
            throw new ExceptionRessourceIntrouvable(id);
        }
        return ressource;
    }

    @Override
    public Ressource creeRessource(String nom, String url, TypeRessource type) {
        if (nom == null || nom.isBlank()) throw new IllegalArgumentException("Le nom n'est pas valide");
        if (url == null || url.isBlank()) throw new IllegalArgumentException("L'url n'est pas valide");
        if (type == null) throw new IllegalArgumentException("Type de ressource non selectionné");

        return this.repository.sauvegarder(new Ressource(nom,type,url));
    }

    @Override
    public Ressource supprimerRessource(int id) {
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null) throw new ExceptionRessourceIntrouvable(id);

        this.repository.supprimer(id);
        return ressource;
    }

    @Override
    public void modifier(int id, String nom, String url, TypeRessource type) {
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null) throw new ExceptionRessourceIntrouvable(id);

        if (nom != null && !nom.isBlank()) ressource.setNom(nom);

        if (url != null && !url.isBlank()) ressource.setUrl(url);

        if (type != null ) ressource.setType(type);

        this.repository.sauvegarder(ressource);

    }
}
