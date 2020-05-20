package fact.it.restaurantapp.model;

import fact.it.restaurantapp.observer.Observer;

import javax.persistence.ManyToOne;

public abstract class Personeel implements Observer {
    protected String naam;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public abstract void update();
}
