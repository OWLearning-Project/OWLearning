package Domain.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TypeRessource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_typeRessource;
    private String typeRessource;

    public TypeRessource(){}

    public TypeRessource(String unTypeMessage){
        this.typeRessource = unTypeMessage;
    }

    public int getIdTypeRessource(){
        return this.id_typeRessource;
    }
    public String getTypeRessource(){
        return this.typeRessource;
    }
}
