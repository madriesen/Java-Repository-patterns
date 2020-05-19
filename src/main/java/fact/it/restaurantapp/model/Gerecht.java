package fact.it.restaurantapp.model;

public class Gerecht {
    private String naam;
    private double actuelePrijs;

    public Gerecht() {
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getActuelePrijs() {
        return actuelePrijs;
    }

    public void setActuelePrijs(double actuelePrijs) {
        this.actuelePrijs = actuelePrijs;
    }
}
