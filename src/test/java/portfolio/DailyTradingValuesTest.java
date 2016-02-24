package portfolio;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ema on 24/02/16.
 */
public class DailyTradingValuesTest {

    @Test
    public void testGetAdjustedClosingPrice() throws Exception {
        double delta = 0.0000001;

        double adjustedClosingPrice = 3414.2124;
        DailyTradingValues dailyTradingValues = new DailyTradingValues(adjustedClosingPrice, 0,
                0, 0, 0);
        assertEquals(adjustedClosingPrice, dailyTradingValues.getAdjustedClosingPrice(), delta);

        adjustedClosingPrice = 5368358395.75;
        dailyTradingValues = new DailyTradingValues(adjustedClosingPrice, 0, 0, 0, 0);
        assertEquals(adjustedClosingPrice, dailyTradingValues.getAdjustedClosingPrice(), delta);
    }

    @Test
    public void testGetAdjustedOpeningPrice() throws Exception {
        double delta = 0.0000001;

        double adjustedOpeningPrice = 3414.2124;
        DailyTradingValues dailyTradingValues = new DailyTradingValues(0, adjustedOpeningPrice,
                0, 0, 0);
        assertEquals(adjustedOpeningPrice, dailyTradingValues.getAdjustedOpeningPrice(), delta);

        adjustedOpeningPrice = 5368358395.75;
        dailyTradingValues = new DailyTradingValues(0, adjustedOpeningPrice, 0, 0, 0);
        assertEquals(adjustedOpeningPrice, dailyTradingValues.getAdjustedOpeningPrice(), delta);
    }

    @Test
    public void testGetAdjustedHighPrice() throws Exception {
        double delta = 0.0000001;

        double adjustedHighPrice = 3414.2124;
        DailyTradingValues dailyTradingValues = new DailyTradingValues(0, 0, adjustedHighPrice,
                0, 0);
        assertEquals(adjustedHighPrice, dailyTradingValues.getAdjustedHighPrice(), delta);

        adjustedHighPrice = 5368358395.75;
        dailyTradingValues = new DailyTradingValues(0, 0, adjustedHighPrice, 0, 0);
        assertEquals(adjustedHighPrice, dailyTradingValues.getAdjustedHighPrice(), delta);
    }

    @Test
    public void testGetAdjustedLowPrice() throws Exception {
        double delta = 0.0000001;

        double adjustedLowPrice = 3414.2124;
        DailyTradingValues dailyTradingValues = new DailyTradingValues(0, 0, 0,
                adjustedLowPrice, 0);
        assertEquals(adjustedLowPrice, dailyTradingValues.getAdjustedLowPrice(), delta);

        adjustedLowPrice = 5368358395.75;
        dailyTradingValues = new DailyTradingValues(0, 0, 0, adjustedLowPrice, 0);
        assertEquals(adjustedLowPrice, dailyTradingValues.getAdjustedLowPrice(), delta);
    }

    @Test
    public void testGetAdjustedVolume() throws Exception {
        double delta = 0.0000001;

        long adjustedVolume = 34142124;
        DailyTradingValues dailyTradingValues = new DailyTradingValues(0, 0, 0, 0,
                adjustedVolume);
        assertEquals(adjustedVolume, dailyTradingValues.getAdjustedVolume(), delta);

        adjustedVolume = 5368358439575L;
        dailyTradingValues = new DailyTradingValues(0, 0, 0, 0, adjustedVolume);
        assertEquals(adjustedVolume, dailyTradingValues.getAdjustedVolume(), delta);
    }
}