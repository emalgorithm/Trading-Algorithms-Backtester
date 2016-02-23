package Portfolio;

import java.util.Date;

/**
 * Created by ema on 23/02/16.
 */
public class DailyTradingValues {
    private final double adjustedClosingPrice;
    private final double adjustedOpeningPrice;
    private final double adjustedHighPrice;
    private final double adjustedLowPrice;
    private final long adjustedVolume;


    public DailyTradingValues(double adjustedClosingPrice, double adjustedOpeningPrice,
                              double adjustedHighPrice, double adjustedLowPrice,
                              long adjustedVolume) {

        this.adjustedClosingPrice = adjustedClosingPrice;
        this.adjustedOpeningPrice = adjustedOpeningPrice;
        this.adjustedHighPrice = adjustedHighPrice;
        this.adjustedLowPrice = adjustedLowPrice;
        this.adjustedVolume = adjustedVolume;
    }

    public double getAdjustedClosingPrice() {
        return adjustedClosingPrice;
    }

    public double getAdjustedOpeningPrice() {
        return adjustedOpeningPrice;
    }

    public double getAdjustedHighPrice() {
        return adjustedHighPrice;
    }

    public double getAdjustedLowPrice() {
        return adjustedLowPrice;
    }

    public long getAdjustedVolume() {
        return adjustedVolume;
    }

}
