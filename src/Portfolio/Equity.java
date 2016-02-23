package Portfolio;

import QuandlAPIUser.DataFetcher;
import QuandlAPIUser.Dataset;
import QuandlAPIUser.JsonWrapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @Override
    public String toString() {
        return fullName;
    }

//    public double getCurrentPrice() {
//        double price = 0.0;
//        String url = "http://download.finance.yahoo.com/d/quotes.csv?s=" + ticker + "&f=l1";
//
//        try {
//            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//            String line = in.readLine();
//
//            in.close();
//
//            price = Double.parseDouble(line);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return price;
//    }

}
