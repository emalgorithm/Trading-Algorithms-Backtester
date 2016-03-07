package portfolio;

/**
 * Created by ema on 23/02/16.
 */
public class DailyTradingValues {
    private final Double closingPrice;
    private final Double openingPrice;
    private final Double highPrice;
    private final Double lowPrice;
    private final long volume;


    public DailyTradingValues(Double closingPrice, Double openingPrice,
                              Double highPrice, Double lowPrice,
                              long volume) {

        this.closingPrice = closingPrice;
        this.openingPrice = openingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
    }

    public Double getClosingPrice() {
        return closingPrice;
    }

    public Double getOpeningPrice() {
        return openingPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public long getVolume() {
        return volume;
    }

}
