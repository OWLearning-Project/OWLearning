package Domain.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StatutMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_statutMessage;
    private String statutMessage;

    public StatutMessage(){}

    public StatutMessage(String unStatutMessage){
        this.statutMessage = unStatutMessage;
    }

    public int getIdStatutMessage(){
        return this.id_statutMessage;
    }
    public String getStatutMessage(){
        return this.statutMessage;
    }
}
