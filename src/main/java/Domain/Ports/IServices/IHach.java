package Domain.Ports.IServices;

public interface IHach
{
    public String hacher(String motDePasse);

    public boolean valider(String motDePasse, String motDePasseHache);
}
