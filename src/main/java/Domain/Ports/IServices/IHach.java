package Domain.Ports.IServices;

public interface IHach
{
    String hacher(String motDePasse);

    boolean valider(String motDePasse, String motDePasseHache);
}
