package portfolio;

import quandlAPIUser.DataFetcher;
import quandlAPIUser.Dataset;
import util.Util;
import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ema on 21/02/16.
 */

public class Equity {
    private final String fullName;
    private final String ticker;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Map<LocalDate, DailyTradingValues> historicalData;


    public Equity(String fullName, String ticker) {
        Dataset dataset = DataFetcher.getDataset(ticker);

        this.fullName = fullName;
        this.ticker = ticker;
        this.startDate = Util.computeDate(dataset.getStart_date());
        this.endDate = Util.computeDate(dataset.getEnd_date());
        this.historicalData = createHistoricalData(dataset);
    }


    /**
     * Get the moving average (of the closing prices) of a certain length up to lastDate
     * @param lastDate the day up to which I'm computing the moving average
     * @param numberOfDays the length of the moving averages
     * @return a double representing the requested moving average
     */
    public double getMovingAverage(LocalDate lastDate, int numberOfDays) throws NotEnoughDataException {
        if (ChronoUnit.DAYS.between(startDate, lastDate) + 1 < numberOfDays) {
            throw new NotEnoughDataException("There aren't as many recorded days as needed " +
                    "to calculate the requested moving average");
        }

        if (endDate.compareTo(lastDate) < 0) {
            throw new NotEnoughDataException("The last day of the requested moving average " +
                    "is older than the last day recorded");
        }

        //Set date to last trading day before that date
        if (!historicalData.containsKey(lastDate)) {
            lastDate = getPreviousTradingDate(lastDate);
        }

        double pricesSum = 0.0;
        int i = numberOfDays;

        while (i > 0) {
            double dailyClosingPrice = historicalData.get(lastDate).getAdjustedClosingPrice();
            pricesSum += dailyClosingPrice;

            lastDate = getPreviousTradingDate(lastDate);

            i--;
        }

        double movingAverage = pricesSum / ((double) numberOfDays);

        return movingAverage;
    }


    @Override
    public String toString() {
        return fullName;
    }


    private Map<LocalDate, DailyTradingValues> createHistoricalData(Dataset dataset) {
        List<List<Object>> dataContainer = dataset.getData();
        Map<LocalDate, DailyTradingValues> historicalData = new HashMap<>();

        for (List<Object> dailyData : Lists.reverse(dataContainer)) {
            double adjustedClosingPrice = getDailyValue(dataset, dailyData, "Adj. Close");
            double adjustedOpeningPrice = getDailyValue(dataset, dailyData, "Adj. Open");
            double adjustedHighPrice = getDailyValue(dataset, dailyData, "Adj. High");
            double adjustedLowPrice = getDailyValue(dataset, dailyData, "Adj. Low");
            long adjustedVolume = ((Double) getDailyValue(dataset, dailyData,
                    "Adj. Volume")).longValue();

            String dateString = getDailyValue(dataset, dailyData, "Date");
            LocalDate date = Util.computeDate(dateString);

            DailyTradingValues dailyTradingValues = new DailyTradingValues(adjustedClosingPrice,
                    adjustedOpeningPrice, adjustedHighPrice, adjustedLowPrice, adjustedVolume);

            historicalData.put(date, dailyTradingValues);
        }

        return historicalData;
    }


    private <T> T getDailyValue(Dataset dataset, List<Object> dailyData, String valueName) {
        int valueIndex = dataset.getIndexInColumnNames(valueName);
        T value = (T) dailyData.get(valueIndex);
        return value;
    }

    private LocalDate getPreviousTradingDate(LocalDate date) throws NotEnoughDataException {
        do {
            if (date.compareTo(startDate) < 0) {
                throw new NotEnoughDataException("There aren't as many recorded days as needed " +
                        "to calculate the requested moving average");
            }

            date = date.minusDays(1);
        } while (!historicalData.containsKey(date));

        return date;
    }

}
