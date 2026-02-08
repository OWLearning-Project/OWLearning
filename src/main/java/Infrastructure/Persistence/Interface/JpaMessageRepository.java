package Infrastructure.Persistence.Interface;

import Domain.Models.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface JpaMessageRepository extends JpaRepository<Message, Integer>
{
    @Query(value = "SELECT * FROM Message WHERE id_discussion = :idDiscussion ORDER BY date_creation ASC", nativeQuery = true)
    ArrayList<Message> trouverParDiscussionIdNative(@Param("idDiscussion") int idDiscussion);

    @Query(value = "Select * FROM Message WHERE id_message = :idMessage",nativeQuery = true)
    Message trouverParMessageIdNative(@Param("idMessage") int idMessage);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Message (contenu, date_creation, statut, id_discussion, id_utilisateur) " +
            "VALUES (:contenu, :date, :statut, :idDiscussion, :idUtilisateur)", nativeQuery = true)
    void ajouterMessageNative(@Param("contenu") String contenu,
                             @Param("date") Timestamp date,
                             @Param("statut") String statut,
                             @Param("idDiscussion") int idDiscussion,
                             @Param("idUtilisateur") int idUtilisateur);

    @Query(value = "SELECT id_message FROM Message WHERE id_utilisateur = :idUtilisateur AND date_creation = :date LIMIT 1", nativeQuery = true)
    Message trouverParAuteurEtParDateNative(@Param("idUtilisateur") int idUtilisateur, @Param("date") Timestamp date);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO piece_jointe (id_message, id_ressource) VALUES (:idMessage, :idRessource)", nativeQuery = true)
    void ajouterLiaisonPieceJointeNative(@Param("idMessage") int idMessage, @Param("idRessource") int idRessource);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Message WHERE id_message = :id", nativeQuery = true)
    void supprimerParIdNative(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM piece_jointe WHERE id_message = :idMessage AND id_ressource = :idRessource", nativeQuery = true)
    void supprimerLiaisonPieceJointeNative(@Param("idMessage") int idMessage, @Param("idRessource") int idRessource);

}
