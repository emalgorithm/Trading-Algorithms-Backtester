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
        Double adjustedClosingPrice = 3414.2124;
        Double adjustedOpeningPrice = 4264.346;
        Double adjustedHighPrice = 574.6542;
        Double adjustedLowPrice = 12.744;
        long adjustedVolume = 9523643;

        dailyTradingValues = new DailyTradingValues(adjustedClosingPrice, adjustedOpeningPrice,
                adjustedHighPrice, adjustedLowPrice, adjustedVolume);
    }

    @Test
    public void testGetAdjustedClosingPrice() throws Exception {
        assertEquals(3414.2124, dailyTradingValues.getClosingPrice(), Util.DELTA);
    }

    @Test
    public void testGetAdjustedOpeningPrice() throws Exception {
        assertEquals(4264.346, dailyTradingValues.getOpeningPrice(), Util.DELTA);
    }

    @Test
    public void testGetAdjustedHighPrice() throws Exception {
        assertEquals(574.6542, dailyTradingValues.getHighPrice(), Util.DELTA);
    }

    @Test
    public void testGetAdjustedLowPrice() throws Exception {
        assertEquals(12.744, dailyTradingValues.getLowPrice(), Util.DELTA);
    }

    @Test
    public void testGetAdjustedVolume() throws Exception {
        assertEquals(9523643, dailyTradingValues.getVolume(), Util.DELTA);
    }
}