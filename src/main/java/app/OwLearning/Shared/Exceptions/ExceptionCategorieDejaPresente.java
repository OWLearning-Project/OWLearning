package app.OwLearning.Shared.Exceptions;

public class ExceptionCategorieDejaPresente extends RuntimeException
{
  private String label;
  private int idCours;
  public ExceptionCategorieDejaPresente(String message, String label, int idCours)
  {
    super(message);
    this.label = label;
    this.idCours = idCours;
  }

  public String toString()
  {
    return  super.toString() + " La catégorie: " + this.label +
            " est déjà associée au cours d'id : " + this.idCours;
  }
}
