package Portfolio;

import QuandlAPIUser.DataFetcher;
import QuandlAPIUser.Dataset;
import com.google.common.collect.Lists;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ema on 21/02/16.
 */

public class Equity {
    private final String fullName;
    private final String ticker;
    private final Date startDate;
    private final Date endDate;
    private final List<Double> adjustedClosingPrices;
    private final List<Double> adjustedOpeningPrices;
    private final List<Double> adjustedHighPrices;
    private final List<Double> adjustedLowPrices;
    private final List<Long> adjustedVolume;
    private final List<Date> tradingDates;

    public Equity(String fullName, String ticker) {
        Dataset dataset = DataFetcher.getDataset(ticker);

        this.fullName = fullName;
        this.ticker = ticker;
        this.startDate = computeDate(dataset.getStart_date());
        this.endDate = computeDate(dataset.getEnd_date());
        this.adjustedClosingPrices = createValuesList(dataset, "Adj. Close");
        this.adjustedOpeningPrices = createValuesList(dataset, "Adj. Open");
        this.adjustedHighPrices = createValuesList(dataset, "Adj. High");
        this.adjustedLowPrices = createValuesList(dataset, "Adj. Low");
        this.adjustedVolume = createValuesList(dataset, "Adj. Volume");
        this.tradingDates = createTradingDatesList(dataset);
    }

    @Override
    public String toString() {
        return fullName;
    }

    private Date computeDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH);
        Date date;

        try {
            date = dateFormat.parse(dateString);
            //Todo Solve problem with dates not computing their value correctly
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }

        return date;
    }

    private <T> ArrayList<T> createValuesList(Dataset dataset, String valueDescription) {
        List<List<Object>> dataContainer = dataset.getData();
        ArrayList<T> values = new ArrayList<>();

        int valueIndex = dataset.getIndexInColumnNames(valueDescription);

        for (List<Object> dailyData : Lists.reverse(dataContainer)) {
            T value = (T)dailyData.get(valueIndex);
            values.add(value);
        }

        return values;
    }

    private List<Date> createTradingDatesList(Dataset dataset) {
        List<String> tradingDatesAsString = createValuesList(dataset, "Date");
        List<Date> tradingDates = new ArrayList<>();

        for (String tradingDateAsString : Lists.reverse(tradingDatesAsString)) {
            tradingDates.add(computeDate(tradingDateAsString));
        }

        return tradingDates;
    }

    private int dateToIndex(Date date) {
        return tradingDates.indexOf(date);
    }

    private Date indexToDate(int index) {
        return tradingDates.get(index);
    }

}
