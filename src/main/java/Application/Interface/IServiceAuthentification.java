package Application.Interface;

import Shared.DTO.ReponseAuthDTO;
import Shared.DTO.RequeteConnexionDTO;
import Shared.DTO.RequeteInscriptionDTO;

public interface IServiceAuthentification 
{
    /*
    Authentifie un utilisateur et retourne un Token JWT 
    @params request identifiants (email/mdp)
    @return token avec infos
    @throws ExceptionMauvaisIdentifiants si email/mdp faux
    */
    public ReponseAuthDTO login(RequeteConnexionDTO request);

    /*
    Enregistre un utilisateur
    @param request les infos pour enregistrer l'utilisateur
    @throws ExceptionUtilisateurExisteDeja si l'email est déjà pris
    */
    public void register(RequeteInscriptionDTO request);
}
