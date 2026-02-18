package app.OwLearning.Api;

import app.OwLearning.Shared.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController
{
    @ExceptionHandler({ExceptionMauvaisIdentifiants.class,
            ExceptionTokenInvalide.class})
    public ResponseEntity<String> unauthorized(RuntimeException ex)
    {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toString());
    }
    @ExceptionHandler(ExceptionUtilisateurNonAutorise.class)
    public ResponseEntity<String> forbidden(ExceptionUtilisateurNonAutorise ex)
    {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.toString());
    }
    @ExceptionHandler({ExceptionEleveDejaPresent.class,
            ExceptionCompteExistant.class,
            ExceptionCategorieDejaPresente.class})
    public ResponseEntity<String> conflict(RuntimeException ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
    }
    @ExceptionHandler({ExceptionMauvaisIdEleve.class,
            ExceptionMauvaisIdChapitre.class,
            ExceptionMauvaisLabelCategorie.class,
            ExceptionUtilisateurInexistant.class,
            ExceptionDiscussionInexistante.class})
    public ResponseEntity<String> not_found(Exception ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.toString());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception ex)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreure est survenue : " + ex.getMessage());
    }

}
