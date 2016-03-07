package portfolio;

import com.google.common.collect.Lists;
import quandlAPIUser.Dataset;
import util.Util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ema on 07/03/16.
 */
public class Futures extends Asset {
    public static String DATASET_NAME = "CME";

    public Futures(String fullName, String ticker) {
        super(fullName, ticker, DATASET_NAME);
    }


    protected Map<LocalDate, DailyTradingValues> createHistoricalData(Dataset dataset) {
        List<List<Object>> dataContainer = dataset.getData();
        Map<LocalDate, DailyTradingValues> historicalData = new HashMap<>();

        for (List<Object> dailyData : Lists.reverse(dataContainer)) {
            Double closingPrice = getDailyValue(dataset, dailyData, "Last");
            Double openingPrice = getDailyValue(dataset, dailyData, "Open");
            Double highPrice = getDailyValue(dataset, dailyData, "High");
            Double lowPrice = getDailyValue(dataset, dailyData, "Low");
            long volume = ((Double) getDailyValue(dataset, dailyData, "Volume")).longValue();

            String dateString = getDailyValue(dataset, dailyData, "Date");
            LocalDate date = Util.computeDate(dateString);

            DailyTradingValues dailyTradingValues = new DailyTradingValues(closingPrice,
                    openingPrice, highPrice, lowPrice, volume);

            historicalData.put(date, dailyTradingValues);
        }

        return historicalData;
    }
}
