package fact.it.restaurantapp.helpers;

public class DoubleHelper {
    public static double round(double value) {
        if (2 < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
