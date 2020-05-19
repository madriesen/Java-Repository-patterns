package fact.it.restaurantapp.model;

import fact.it.restaurantapp.betaling.BetaalStrategie;
import fact.it.restaurantapp.betaling.NormaleBetaling;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Bestelling {

    private GregorianCalendar datum;
    private boolean betaald;
    @ManyToOne
    private Zaalpersoneel zaalpersoneel;
    @ManyToOne
    private Tafel tafel;
    @OneToMany(mappedBy = "bestelling")
    private List<BesteldItem> besteldItems = new ArrayList<>();
    @Transient
    private BetaalStrategie betaalStrategie;

    public Bestelling() {
        this.betaalStrategie = new NormaleBetaling();
    }

    public GregorianCalendar getDatum() {
        return datum;
    }

    public void setDatum(GregorianCalendar datum) {
        this.datum = datum;
    }

    public void setBetaalStrategie(BetaalStrategie betaalStrategie) {
        this.betaalStrategie = betaalStrategie;
    }

    public Zaalpersoneel getZaalpersoneel() {
        return zaalpersoneel;
    }

    public void setZaalpersoneel(Zaalpersoneel zaalpersoneel) {
        this.zaalpersoneel = zaalpersoneel;
    }

    public Tafel getTafel() {
        return tafel;
    }

    public void setTafel(Tafel tafel) {
        this.tafel = tafel;
    }

    public List<BesteldItem> getBesteldItems() {
        return besteldItems;
    }

    public void setBesteldItems(List<BesteldItem> besteldItems) {
        this.besteldItems = besteldItems;
    }

    public void addItem(Gerecht gerecht, int aantal) {
        BesteldItem item = new BesteldItem();
        item.setBestelling(this);
        item.setGerecht(gerecht);
        item.setAantal(aantal);
        item.setToegepastePrijs(this.betaalStrategie.getToegepastePrijs(gerecht.getActuelePrijs()));
        besteldItems.add(item);
    }

    public double getTotaal() {
        double totaal = 0;
        for (BesteldItem item : besteldItems) {
            double prijs = item.getToegepastePrijs();
            int aantal = item.getAantal();
            totaal += (prijs * aantal);
        }
        return totaal;
    }
}
