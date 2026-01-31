package Domain.Ports.IServices;

import Domain.Models.Utilisateur;

public interface IServiceToken
{
    String genererToken(Utilisateur utilisateur);

    boolean validerToken(String token);

    void invaliderToken(String token);

    int extraireID(String token);
}
