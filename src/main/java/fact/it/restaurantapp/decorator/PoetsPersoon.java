package fact.it.restaurantapp.decorator;

public class PoetsPersoon extends ExtraTaak {

    public void schoonMaken() {
        System.out.println(
                "Ik ben "
                        + this.getPersoneel().getNaam()
                        + " en ik ga nu ook schoonmaken."
        );
    }
}
