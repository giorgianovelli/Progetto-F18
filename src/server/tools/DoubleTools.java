package server.tools;

public class DoubleTools {
    /**
     * Round off to the second decimal place
     * @param toRound the double value to be rounded
     * @return the rounded double value.
     */
    public static double round2Decimal(double toRound){
        return (double)Math.round(toRound*100)/100;
    }
}
