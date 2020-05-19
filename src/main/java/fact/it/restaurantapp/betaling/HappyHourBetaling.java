package fact.it.restaurantapp.betaling;

public class HappyHourBetaling implements BetaalStrategie{
    @Override
    public double getToegepastePrijs(double actuelePrijs) {
        return 0.8 * actuelePrijs;
    }
}
