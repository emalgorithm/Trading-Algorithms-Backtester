package portfolio;

import quandlAPIUser.DataFetcher;
import quandlAPIUser.Dataset;
import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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
        this.startDate = computeDate(dataset.getStart_date());
        this.endDate = computeDate(dataset.getEnd_date());
        this.historicalData = createHistoricalData(dataset);
    }


    /**
     * Get the moving average (of the closing prices) of a certain length up to lastDate
     * @param lastDate the day up to which I'm computing the moving average
     * @param numberOfDays the length of the moving averages
     * @return a double representing the requested moving average
     */
    public double getMovingAverage(LocalDate lastDate, int numberOfDays) throws NotEnoughDataException {
        //Todo consider non-trading days
        if (ChronoUnit.DAYS.between(startDate, lastDate) + 1 < numberOfDays) {
            throw new NotEnoughDataException("There aren't as many recorded days as needed " +
                    "to calculate the requested moving average");
        }

        if (endDate.compareTo(lastDate) < 0) {
            throw new NotEnoughDataException("The last day of the requested moving average " +
                    "is older than the last day recorded");
        }

        double pricesSum = 0.0;
        int i = numberOfDays;

        while (i > 0) {
            double dailyClosingPrice = historicalData.get(lastDate).getAdjustedClosingPrice();
            pricesSum += dailyClosingPrice;

            do {
                lastDate = lastDate.plusDays(1);
            } while (!historicalData.containsKey(lastDate));

            i--;
        }

        double movingAverage = pricesSum / ((double) numberOfDays);

        return movingAverage;
    }


    @Override
    public String toString() {
        return fullName;
    }


    private LocalDate computeDate(String dateString) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;

        try {
            date = LocalDate.parse(dateString, dateFormat);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            date  = LocalDate.now();
        }

        return date;
    }


    /**
     * Get the difference between two dates
     * @param oldestDate the oldest date
     * @param newestDate the newest date
     * @param timeUnit the unit in which you want the difference
     * @return the difference value, in the provided unit
     */
    private long getDatesDifference(LocalDate oldestDate, LocalDate newestDate,
                                    TemporalUnit timeUnit) {
        return oldestDate.until(newestDate, timeUnit);
    }


    private Map<LocalDate, DailyTradingValues> createHistoricalData(Dataset dataset) {
        List<List<Object>> dataContainer = dataset.getData();
        Map<LocalDate, DailyTradingValues> historicalData = new HashMap<>();

        for (List<Object> dailyData : Lists.reverse(dataContainer)) {
            double adjustedClosingPrice = getDailyValue(dataset, dailyData, "Adj. Close");
            double adjustedOpeningPrice = getDailyValue(dataset, dailyData, "Adj. Open");
            double adjustedHighPrice = getDailyValue(dataset, dailyData, "Adj. High");
            double adjustedLowPrice = getDailyValue(dataset, dailyData, "Adj. Low");
            long adjustedVolume = ((Double) getDailyValue(dataset, dailyData, "Adj. Volume")).longValue();

            String dateString = getDailyValue(dataset, dailyData, "Date");
            LocalDate date = computeDate(dateString);

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

}
