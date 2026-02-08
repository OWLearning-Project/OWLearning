package Infrastructure.Persistence.Interface;

import Domain.Models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaRessourceRepository extends JpaRepository<Ressource, Integer>
{
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Ressource (nom, url, type_ressource) VALUES (:nom, :url, :type)", nativeQuery = true)
    void ajouterRessourceNative(@Param("nom") String nom,
                          @Param("url") String url,
                          @Param("type") String type);

    @Query(value = "SELECT id_ressource FROM Ressource WHERE url = :url LIMIT 1", nativeQuery = true)
    Ressource trouverParUrlNative(@Param("url") String url);

    @Query(value = "SELECT * FROM Ressource WHERE id_ressource = :id", nativeQuery = true)
    Ressource trouverParIdNative(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE REssource SET nom = :nom, url = :url, type_ressource = :type WHERE id_ressource = :id", nativeQuery = true)
    void changerRessourceNative(@Param("id") int id,
                          @Param("nom") String nom,
                          @Param("url") String url,
                          @Param("type") String type);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Ressource WHERE id_ressource = :id", nativeQuery = true)
    void supprimerRessourceNative(@Param("id") int id);
}
