package fact.it.restaurantapp.decorator;

import fact.it.restaurantapp.model.Personeel;

import javax.persistence.ManyToOne;

public class ExtraTaak extends Personeel {

    @ManyToOne
    private Personeel personeel;

    public Personeel getPersoneel() {
        return personeel;
    }

    public void setPersoneel(Personeel personeel) {
        this.personeel = personeel;
    }

    @Override
    public void update() {
        this.personeel.update();
    }
}
