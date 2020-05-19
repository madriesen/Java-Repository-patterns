package fact.it.restaurantapp.model;

public class Keukenpersoneel extends Personeel {

    @Override
    public void update() {
        System.out.println("Ik ben "
                + this.naam
                + " en ik begin onmiddellijk met het maken van "
                + IngangTeller.getInstance().getAantal()
                + " amuse-gueules!");
    }
}