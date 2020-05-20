package fact.it.restaurantapp.decorator;

import fact.it.restaurantapp.model.IngangTeller;
import fact.it.restaurantapp.model.Personeel;

public class Administrator extends ExtraTaak {
    @Override
    public void update() {
        super.update();
        System.out.println(
                "Vervolgens registreer ik de "
                        + IngangTeller.getInstance().getAantal()
                        + " klanten in het klantenbestand."
        );
    }
}
