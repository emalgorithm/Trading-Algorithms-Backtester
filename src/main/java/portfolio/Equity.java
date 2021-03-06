package portfolio;

import com.google.common.collect.Lists;
import quandlAPIUser.Dataset;
import util.Util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ema on 21/02/16.
 */

public class Equity extends Asset {
    public static String DATASET_NAME = "wiki";

    public Equity(String fullName, String ticker) {
        super(fullName, ticker, DATASET_NAME);
    }


    protected Map<LocalDate, DailyTradingValues> createHistoricalData(Dataset dataset) {
        List<List<Object>> dataContainer = dataset.getData();
        Map<LocalDate, DailyTradingValues> historicalData = new HashMap<>();

        for (List<Object> dailyData : Lists.reverse(dataContainer)) {
            Double closingPrice = getDailyValue(dataset, dailyData, "Adj. Close");
            Double openingPrice = getDailyValue(dataset, dailyData, "Adj. Open");
            Double highPrice = getDailyValue(dataset, dailyData, "Adj. High");
            Double lowPrice = getDailyValue(dataset, dailyData, "Adj. Low");
            long volume = ((Double) getDailyValue(dataset, dailyData, "Adj. Volume")).longValue();

            String dateString = getDailyValue(dataset, dailyData, "Date");
            LocalDate date = Util.computeDate(dateString);

            DailyTradingValues dailyTradingValues = new DailyTradingValues(closingPrice,
                    openingPrice, highPrice, lowPrice, volume);

            historicalData.put(date, dailyTradingValues);
        }

        return historicalData;
    }

}
