package util;

import portfolio.Asset;
import portfolio.AssetInterface;
import portfolio.Equity;
import portfolio.NotEnoughDataException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by ema on 06/03/16.
 */
public class TechnicalAnalysis {

    /**
     * Get the moving average (of the closing prices) of a certain length up to lastDate
     * @param lastDate the day up to which I'm computing the moving average
     * @param numberOfDays the length of the moving averages
     * @return a Double representing the requested moving average
     */
    public static Double getMovingAverage(AssetInterface asset, LocalDate lastDate,
                                          int numberOfDays) throws NotEnoughDataException {

        if (ChronoUnit.DAYS.between(asset.getStartDate(), lastDate) + 1 < numberOfDays) {
            throw new NotEnoughDataException("There aren't as many recorded days as needed " +
                    "to calculate the requested moving average");
        }

        if (asset.getEndDate().compareTo(lastDate) < 0) {
            throw new NotEnoughDataException("The last day of the requested moving average " +
                    "is older than the last day recorded");
        }

        lastDate = asset.getPreviousOrCurrentTradingDate(lastDate);

        Double pricesSum = 0.0;
        int i = numberOfDays;

        while (i > 0) {
            Double dailyClosingPrice = asset.getClosingPrice(lastDate);
            pricesSum += dailyClosingPrice;

            lastDate = asset.getPreviousTradingDate(lastDate);

            i--;
        }

        Double movingAverage = pricesSum / ((Integer) numberOfDays).doubleValue();

        return movingAverage;
    }

    /**
     * Return the average spread between two assets given by the two tickers in the period between
     * startDate and endDate, in percentage.
     * @param firstTicker
     * @param secondTicker
     * @param startDate
     * @param endDate
     * @return
     */
//    public static Double getAverageSpread(String firstTicker, String secondTicker, LocalDate
//            startDate, LocalDate endDate) {
//
//        AssetInterface firstAsset = Asset.createAsset(firstTicker);
//        AssetInterface secondAsset = Asset.createAsset(secondTicker);
//
//        return getAverageSpread(firstAsset, secondAsset, startDate, endDate);
//
//    }
//
//    public static Double getAverageSpread(AssetInterface firstAsset, AssetInterface secondAsset,
//                                          LocalDate startDate, LocalDate endDate) {
//      //TODO
//    }
}
