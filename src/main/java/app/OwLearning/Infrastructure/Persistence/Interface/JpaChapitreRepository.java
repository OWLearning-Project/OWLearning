package app.OwLearning.Infrastructure.Persistence.Interface;


import app.OwLearning.Domain.Models.Chapitre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaChapitreRepository extends JpaRepository<Chapitre,Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Chapitre (titre, description, id_cours) VALUES (:titre, :description, :id)",
    nativeQuery = true)
    int insertChapitreNative(@Param("titre") String titre, @Param("description") String description, @Param("id") int id);

    @Transactional
    @Query(value = "INSERT INTO ressource_chapitre (id_ressource,id_chapitre) VALUES (:idResource, :idChapitre)", nativeQuery = true)
    int insertRessourceChapitre(@Param("idResource")int idRessource,@Param("idChapitre")int idChapitre);

    @Modifying
    @Transactional
    @Query(value = "DELETE resssource_chapitre WHERE id_resource = :idRes AND id_chapitre = :idChap", nativeQuery = true)
    int supprimerRessourceDuChapitre(@Param("idRes") int idRessource, @Param("idChap") int idChapitre);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Chapitre SET titre = :titre, description = :description WHERE id_chapitre = :id", nativeQuery = true)
    int maj(@Param("id") int id, @Param("titre") String titre, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "DELETE chapitre WHERE id_chapitre = :id", nativeQuery = true)
    Chapitre supprimerChapitre(@Param("id") int id);

    @Query(value = "SELECT * FROM Chapitre WHERE id_chapitre = :id", nativeQuery = true)
    Chapitre findByIdNative(@Param("id") int id);

    @Query(value = "SELECT * FROM Chapitre", nativeQuery = true)
    List<Chapitre> findAllNative();

}
