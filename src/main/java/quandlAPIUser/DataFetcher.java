package quandlAPIUser;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ema on 23/02/16.
 */
public class DataFetcher {

    private static String getJsonData(String ticker, String datasetName) {
        String contents = new String();
        String url = "https://www.quandl.com/api/v3/datasets/" + datasetName + "/" + ticker + "" +
                ".json?auth_token=" + Profile.AUTH_TOKEN;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = in.readLine()) != null) {
                contents += line;
            }

            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents;
    }

    public static Dataset getDataset(String ticker, String datasetName) {
        String jsonString = getJsonData(ticker, datasetName);

        JsonWrapper jsonWrapper = new Gson().fromJson(jsonString, JsonWrapper.class);

        return jsonWrapper.getDataset();
    }
}
