package Application.Interface;

import Shared.DTO.AuthResponseDTO;
import Shared.DTO.LoginRequestDTO;
import Shared.DTO.RegisterRequestDTO;

public interface IAuthenticationService 
{
    /*
    Authentifie un utilisateur et retourne un Token JWT 
    @params request identifiants (email/mdp)
    @return token avec infos
    @throws BadCredentialsException si email/mdp faux
    */
    public AuthResponseDTO login(LoginRequestDTO request);

    /*
    Enregistre un utilisateur
    @param request les infos pour enregistrer l'utilisateur
    @throws UserAlreadyExistsException si l'email est déjà pris
    */
    public void register(RegisterRequestDTO request);
}
