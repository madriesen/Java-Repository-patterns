package fact.it.restaurantapp.model;

import javax.persistence.*;

@Entity
public class BesteldItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int aantal;
    private double toegepastePrijs;
    @ManyToOne
    private Bestelling bestelling;
    @ManyToOne
    private Gerecht gerecht;

    public BesteldItem() {
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    public double getToegepastePrijs() {
        return toegepastePrijs;
    }

    public void setToegepastePrijs(double toegepastePrijs) {
        this.toegepastePrijs = toegepastePrijs;
    }

    public Bestelling getBestelling() {
        return bestelling;
    }

    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    public Gerecht getGerecht() {
        return gerecht;
    }

    public void setGerecht(Gerecht gerecht) {
        this.gerecht = gerecht;
    }
}
