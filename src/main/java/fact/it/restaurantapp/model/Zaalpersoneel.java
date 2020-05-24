package fact.it.restaurantapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Zaalpersoneel extends Personeel {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long zaalpersoneelId;

    public Zaalpersoneel() {
        super();
    }


    @Override
    public void update() {
        System.out.println("Ik ben "
                + this.naam
                + " en ga het nodige doen om voor "
                + IngangTeller.getInstance().getAantal()
                + " klanten een tafel klaar te maken.");
    }
}
