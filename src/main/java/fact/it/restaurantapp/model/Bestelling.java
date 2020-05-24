package fact.it.restaurantapp.model;

import fact.it.restaurantapp.betaling.BetaalStrategie;
import fact.it.restaurantapp.betaling.NormaleBetaling;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
public class Bestelling {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private GregorianCalendar datum;
    private boolean betaald;
    @ManyToOne(cascade = {CascadeType.PERSIST})
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

    private static double round(double value) {
        if (2 < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public boolean isBetaald() {
        return betaald;
    }

    public void setBetaald(boolean betaald) {
        this.betaald = betaald;
    }

    public long getBestellingId() {
        return id;
    }

    public GregorianCalendar getDatum() {
        return datum;
    }

    public void setDatum(GregorianCalendar datum) {
        this.datum = datum;
    }

    public String getFormattedDatum() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(this.getDatum().getTimeInMillis());
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
        return round(totaal);
    }
}
