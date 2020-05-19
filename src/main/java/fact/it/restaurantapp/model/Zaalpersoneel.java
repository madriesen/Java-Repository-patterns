package fact.it.restaurantapp.model;

public class Zaalpersoneel extends Personeel {

    @Override
    public void update() {
        System.out.println("Ik ben "
                + this.naam
                + " en ga het nodige doen om voor "
                + IngangTeller.getInstance().getAantal()
                + " klanten een tafel klaar te maken.");
    }
}
