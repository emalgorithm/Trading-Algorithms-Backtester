package portfolio;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import quandlAPIUser.DataFetcher;
import quandlAPIUser.Dataset;
import quandlAPIUser.Profile;
import util.Util;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ema on 06/03/16.
 */
public abstract class Asset implements AssetInterface{
    private final String fullName;
    private final String ticker;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Map<LocalDate, DailyTradingValues> historicalData;

    public Asset(String fullName, String ticker) {
        String datasetName = Profile.getDatasetName(ticker);
        Dataset dataset = DataFetcher.getDataset(ticker, datasetName);

        this.fullName = fullName;
        this.ticker = ticker;
        this.startDate = Util.computeDate(dataset.getStart_date());
        this.endDate = Util.computeDate(dataset.getEnd_date());
        this.historicalData = createHistoricalData(dataset);
    }

    public Asset(String fullName, String ticker, String datasetName) {
        Dataset dataset = DataFetcher.getDataset(ticker, datasetName);

        this.fullName = fullName;
        this.ticker = ticker;
        this.startDate = Util.computeDate(dataset.getStart_date());
        this.endDate = Util.computeDate(dataset.getEnd_date());
        this.historicalData = createHistoricalData(dataset);
    }


    @Override
    public Double getClosingPrice(LocalDate date) throws NotEnoughDataException {
        if (date.compareTo(startDate) < 0) {
            throw new NotEnoughDataException("The date requested is before the first day recorded");
        }

        if (date.compareTo(endDate) > 0) {
            throw new NotEnoughDataException("The date requested is after the last day recorded");
        }

        date = getPreviousOrCurrentTradingDate(date);

        return historicalData.get(date).getClosingPrice();
    }


    @Override
    public LocalDate getPreviousTradingDate(LocalDate date) throws NotEnoughDataException {
        do {
            if (date.compareTo(startDate) < 0) {
                throw new NotEnoughDataException("The requested date is before the first " +
                        "recorded date");
            }

            date = date.minusDays(1);
        } while (!historicalData.containsKey(date));

        return date;
    }


    @Override
    public LocalDate getNextTradingDate(LocalDate date) throws NotEnoughDataException {
        do {
            if (date.compareTo(endDate) >= 0) {
                throw new NotEnoughDataException("The requested date is after the last " +
                        "recorded date");
            }

            date = date.plusDays(1);
        } while (!historicalData.containsKey(date));

        return date;
    }


    @Override
    public boolean isTradingDate(LocalDate date) {
        return historicalData.containsKey(date);
    }


    @Override
    public LocalDate getStartDate() {
        return startDate;
    }


    @Override
    public LocalDate getEndDate() {
        return endDate;
    }


    @Override
    public LocalDate getPreviousOrCurrentTradingDate(LocalDate date) throws
            NotEnoughDataException {
        if (!historicalData.containsKey(date)) {
            date = getPreviousTradingDate(date);
        }

        return date;
    }


    @Override
    public LocalDate getNextOrCurrentTradingDate(LocalDate date) throws NotEnoughDataException {
        if (!historicalData.containsKey(date)) {
            date = getNextTradingDate(date);
        }

        return date;
    }


    @Override
    public String toString() {
        return fullName;
    }


    protected abstract Map<LocalDate, DailyTradingValues> createHistoricalData(Dataset dataset);


    protected  <T> T getDailyValue(Dataset dataset, List<Object> dailyData, String valueName) {
        int valueIndex = dataset.getIndexInColumnNames(valueName);
        T value = (T) dailyData.get(valueIndex);

        return value;
    }

    public static AssetInterface createAsset(String fullName, String ticker) {
        Class<?> assetType = Profile.getAssetType(ticker);
        Constructor<?> assetTypeConstructor = null;

        try {
            assetTypeConstructor = assetType.getConstructor(String.class, String.class);
        } catch (NoSuchMethodException e) {
            //Should never reach this point
            assert(false);
        }

        AssetInterface asset = null;

        try {
            asset = (AssetInterface) assetTypeConstructor.newInstance(fullName, ticker);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return asset;
    }

    public static AssetInterface createAsset(String ticker) {
        return createAsset("", ticker);
    }
}
