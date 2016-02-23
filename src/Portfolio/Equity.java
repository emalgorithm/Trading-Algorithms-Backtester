package Portfolio;

import QuandlAPIUser.DataFetcher;
import QuandlAPIUser.Dataset;
import com.google.common.collect.Lists;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ema on 21/02/16.
 */

public class Equity {
    private final String fullName;
    private final String ticker;
    private final Date startDate;
    private final Date endDate;
    private final Map<Date, DailyTradingValues> historicalData;


    public Equity(String fullName, String ticker) {
        Dataset dataset = DataFetcher.getDataset(ticker);

        this.fullName = fullName;
        this.ticker = ticker;
        this.startDate = computeDate(dataset.getStart_date());
        this.endDate = computeDate(dataset.getEnd_date());
        this.historicalData = createHistoricalData(dataset);
    }


    @Override
    public String toString() {
        return fullName;
    }


    private Date computeDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date;

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }

        return date;
    }


    private Map<Date, DailyTradingValues> createHistoricalData(Dataset dataset) {
        List<List<Object>> dataContainer = dataset.getData();
        Map<Date, DailyTradingValues> historicalData = new HashMap<>();

        for (List<Object> dailyData : Lists.reverse(dataContainer)) {
            double adjustedClosingPrice = getDailyValue(dataset, dailyData, "Adj. Close");
            double adjustedOpeningPrice = getDailyValue(dataset, dailyData, "Adj. Open");
            double adjustedHighPrice = getDailyValue(dataset, dailyData, "Adj. High");
            double adjustedLowPrice = getDailyValue(dataset, dailyData, "Adj. Low");
            long adjustedVolume = ((Double) getDailyValue(dataset, dailyData, "Adj. Volume")).longValue();

            String dateString = getDailyValue(dataset, dailyData, "Date");
            Date date = computeDate(dateString);

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
