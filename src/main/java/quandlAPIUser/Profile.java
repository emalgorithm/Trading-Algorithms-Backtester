package quandlAPIUser;

import portfolio.Equity;
import portfolio.Futures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ema on 25/02/16.
 */
public class Profile {
    public static final String AUTH_TOKEN = "GtWBx-vGGgzCisDVaeTn";

    private static final Map<String, Class<?>> datasetDictionary;

    static {
        datasetDictionary = new HashMap<>();

        List<String> equitiesList = Arrays.asList(
                "GOOG",
                "GOOGL",
                "FB",
                "AAPL",
                "LNKD"
        );

        List<String> futuresList = Arrays.asList(
                "CLF2013"
        );


        for (String equity : equitiesList) {
            datasetDictionary.put(equity, Equity.class);
        }

        for (String futures : futuresList) {
            datasetDictionary.put(futures, Futures.class);
        }
    }

    public static Class<?> getAssetType(String ticker) {
        return datasetDictionary.get(ticker);
    }

    public static String getDatasetName(String ticker) {
        String datasetName = "";

        try {
            datasetName = (String)getAssetType(ticker).getField("DATASET_NAME").get(null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return datasetName;
    }
}
