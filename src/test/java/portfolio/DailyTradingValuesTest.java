package portfolio;

import org.junit.BeforeClass;
import org.junit.Test;
import util.Util;

import static org.junit.Assert.*;

/**
 * Created by ema on 24/02/16.
 */
public class DailyTradingValuesTest {
    private static DailyTradingValues dailyTradingValues;

    @BeforeClass
    public static void oneTimeSetUp() {
        double adjustedClosingPrice = 3414.2124;
        double adjustedOpeningPrice = 4264.346;
        double adjustedHighPrice = 574.6542;
        double adjustedLowPrice = 12.744;
        long adjustedVolume = 9523643;

        dailyTradingValues = new DailyTradingValues(adjustedClosingPrice, adjustedOpeningPrice,
                adjustedHighPrice, adjustedLowPrice, adjustedVolume);
    }

    @Test
    public void testGetAdjustedClosingPrice() throws Exception {
        assertEquals(3414.2124, dailyTradingValues.getAdjustedClosingPrice(), Util.DELTA);
    }

    @Test
    public void testGetAdjustedOpeningPrice() throws Exception {
        assertEquals(4264.346, dailyTradingValues.getAdjustedOpeningPrice(), Util.DELTA);
    }

    @Test
    public void testGetAdjustedHighPrice() throws Exception {
        assertEquals(574.6542, dailyTradingValues.getAdjustedHighPrice(), Util.DELTA);
    }

    @Test
    public void testGetAdjustedLowPrice() throws Exception {
        assertEquals(12.744, dailyTradingValues.getAdjustedLowPrice(), Util.DELTA);
    }

    @Test
    public void testGetAdjustedVolume() throws Exception {
        assertEquals(9523643, dailyTradingValues.getAdjustedVolume(), Util.DELTA);
    }
}