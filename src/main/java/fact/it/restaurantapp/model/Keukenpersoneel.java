package fact.it.restaurantapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Keukenpersoneel extends Personeel {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long keukenpersoneelId;

    public Keukenpersoneel() {
        super();
    }

    @Override
    public void update() {
        System.out.println("Ik ben "
                + this.naam
                + " en ik begin onmiddellijk met het maken van "
                + IngangTeller.getInstance().getAantal()
                + " amuse-gueules!");
    }
}