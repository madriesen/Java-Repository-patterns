package fact.it.restaurantapp.betaling;

public class NormaleBetaling implements BetaalStrategie {
    @Override
    public double getToegepastePrijs(double actuelePrijs) {
        return actuelePrijs;
    }
}
