package app.OwLearning.Services.Services;

import app.OwLearning.Domaine.Entités.Ressource;
import app.OwLearning.Domaine.Enumérations.TypeRessource;
import app.OwLearning.Domaine.Interfaces.IRessourceRepository;
import app.OwLearning.Services.Interfaces.IServiceRessource;
import app.OwLearning.Services.Exceptions.ExceptionRessourceIntrouvable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class ServiceRessource implements IServiceRessource {

    private final IRessourceRepository repository;

    @Value("${owlearning.upload-dir:uploads/ressources}")
    private String dossierUpload;

    public ServiceRessource(IRessourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ressource getContenuRessource(int id) {
        log.debug("Demande de la récupération de l'id de la ressource: {}", id);
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null) {
            log.warn("La ressource {} est introuvable dans la base", id);
            throw new ExceptionRessourceIntrouvable("La ressource est introuvable", id);
        }
        log.debug("Ressource {} récupérée: {}", id, ressource);
        return ressource;
    }

    @Override
    public Ressource creeRessource(String nom, String url, TypeRessource type) {
        log.debug("Demande de création d'une ressource: nom='{}', url='{}', type='{}'", nom, url, type);
        if (nom == null || nom.isBlank()){
            log.warn("Echec de création de la ressource: le nom n'est pas disponible");
            throw new IllegalArgumentException("Le nom n'est pas valide");
        }
        if (url == null || url.isBlank()){
            log.error("Echec de création de la ressource: l'url n'est pas disponible");
            throw new IllegalArgumentException("L'url n'est pas valide");
        }
        if (type == null){
            log.error("Echec de création de la ressource: le type de ressource n'est pas disponible ");
            throw new IllegalArgumentException("Type de ressource non selectionné");
        }

        Ressource ressource = this.repository.sauvegarder(new Ressource(nom,type,url));

        log.info("Création de ressource: {}", ressource);
        return ressource;
    }

    @Override
    public Ressource uploaderRessource(MultipartFile fichier, String urlBase)
    {
        if (fichier == null || fichier.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est obligatoire");
        }

        String nomOriginal = StringUtils.cleanPath(Objects.requireNonNullElse(fichier.getOriginalFilename(), "ressource"));
        if (!StringUtils.hasText(nomOriginal)) {
            nomOriginal = "ressource";
        }

        TypeRessource type = determinerTypeRessource(fichier, nomOriginal);
        String nomStockage = genererNomStockage(nomOriginal);

        try {
            Path dossier = Paths.get(dossierUpload).toAbsolutePath().normalize();
            Files.createDirectories(dossier);

            Path destination = dossier.resolve(nomStockage).normalize();
            if (!destination.startsWith(dossier)) {
                throw new IllegalArgumentException("Nom de fichier invalide");
            }

            try (InputStream fluxFichier = fichier.getInputStream()) {
                Files.copy(fluxFichier, destination);
            }

            String urlRessource = urlBase + "/uploads/ressources/" + nomStockage;
            return creeRessource(nomOriginal, urlRessource, type);
        }
        catch (IOException e) {
            throw new IllegalStateException("Impossible d'enregistrer le fichier", e);
        }
    }

    @Override
    public Ressource supprimerRessource(int id) {
        log.debug("Demande de suppression de la ressource (id): {}", id);
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null){
            log.warn("Echec de la suppression de la ressource (id): {}, id introuvable dans la base", id);
            throw new ExceptionRessourceIntrouvable("La ressource est introuvable", id);
        }

        this.repository.supprimer(id);
        log.info("Suppression de la ressource: {}", ressource);
        return ressource;
    }

    @Override
    public void modifier(int id, String nom, String url, TypeRessource type) {
        log.debug("Demande de modification de la ressource ID {}: nom='{}', url='{}', type='{}", id, nom, url, type);
        Ressource ressource = this.repository.trouverParId(id);

        if (ressource == null){
            log.warn("Echec de la modification. L'id {} est introuvable dans la base", id);
            throw new ExceptionRessourceIntrouvable("La ressource est introuvable", id);
        }

        if (nom != null && !nom.isBlank()) ressource.setNom(nom);

        if (url != null && !url.isBlank()) ressource.setUrl(url);

        if (type != null ) ressource.setType(type);

        this.repository.sauvegarder(ressource);
        log.info("Modification de la ressource ID {}", id);

    }

    private TypeRessource determinerTypeRessource(MultipartFile fichier, String nomFichier) {
        String contentType = fichier.getContentType();
        if (contentType != null) {
            String typeMime = contentType.toLowerCase();
            if (typeMime.startsWith("image/")) {
                return TypeRessource.IMAGE;
            }
            if (typeMime.startsWith("video/")) {
                return TypeRessource.VIDEO;
            }
            if (typeMime.equals("application/pdf")) {
                return TypeRessource.FICHIER_PDF;
            }
            if (typeMime.contains("zip") || typeMime.equals("application/x-zip-compressed")) {
                return TypeRessource.FICHIER_ZIP;
            }
        }

        String extension = StringUtils.getFilenameExtension(nomFichier);
        if (extension != null) {
            return switch (extension.toLowerCase()) {
                case "jpg", "jpeg", "png", "gif", "webp", "svg" -> TypeRessource.IMAGE;
                case "mp4", "webm", "mov", "avi", "mkv" -> TypeRessource.VIDEO;
                case "pdf" -> TypeRessource.FICHIER_PDF;
                case "zip" -> TypeRessource.FICHIER_ZIP;
                default -> throw new IllegalArgumentException("Type de fichier non supporté");
            };
        }

        throw new IllegalArgumentException("Type de fichier non supporté");
    }

    private String genererNomStockage(String nomOriginal) {
        String extension = StringUtils.getFilenameExtension(nomOriginal);
        String suffixe = StringUtils.hasText(extension) ? "." + extension.toLowerCase() : "";

        return UUID.randomUUID() + suffixe;
    }
}
