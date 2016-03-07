package portfolio;

import org.junit.Test;
import util.Util;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by ema on 07/03/16.
 */
public abstract class AssetTest implements AssetInterfaceTest {
    protected static AssetInterface firstAsset;
    protected static AssetInterface secondAsset;

    @Test
    @Override
    public void testGetPreviousTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-23");
        LocalDate expectedPreviousTradingDate = Util.computeDate("2016-02-22");
        LocalDate actualPreviousTradingDate = secondAsset.getPreviousTradingDate(date);
        assertEquals(expectedPreviousTradingDate, actualPreviousTradingDate);

        date = Util.computeDate("2016-02-08");
        expectedPreviousTradingDate = Util.computeDate("2016-02-05");
        actualPreviousTradingDate = secondAsset.getPreviousTradingDate(date);
        assertEquals(expectedPreviousTradingDate, actualPreviousTradingDate);

        date = Util.computeDate("2008-03-24");
        expectedPreviousTradingDate = Util.computeDate("2008-03-20");
        actualPreviousTradingDate = secondAsset.getPreviousTradingDate(date);
        assertEquals(expectedPreviousTradingDate, actualPreviousTradingDate);
    }


    @Test
    @Override
    public void testGetNextTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-22");
        LocalDate expectedNextTradingDate = Util.computeDate("2016-02-23");
        LocalDate actualNextTradingDate = secondAsset.getNextTradingDate(date);
        assertEquals(expectedNextTradingDate, actualNextTradingDate);

        date = Util.computeDate("2016-01-15");
        expectedNextTradingDate = Util.computeDate("2016-01-19");
        actualNextTradingDate = secondAsset.getNextTradingDate(date);
        assertEquals(expectedNextTradingDate, actualNextTradingDate);

        date = Util.computeDate("2008-03-20");
        expectedNextTradingDate = Util.computeDate("2008-03-24");
        actualNextTradingDate = secondAsset.getNextTradingDate(date);
        assertEquals(expectedNextTradingDate, actualNextTradingDate);
    }


    @Test
    @Override
    public void testGetPreviousOrCurrentTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-23");
        LocalDate expectedPreviousOrCurrentTradingDate = Util.computeDate("2016-02-23");
        LocalDate actualPreviousOrCurrentTradingDate = secondAsset.getPreviousOrCurrentTradingDate(date);
        assertEquals(expectedPreviousOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);

        date = Util.computeDate("2016-02-07");
        expectedPreviousOrCurrentTradingDate = Util.computeDate("2016-02-05");
        actualPreviousOrCurrentTradingDate = secondAsset.getPreviousOrCurrentTradingDate(date);
        assertEquals(expectedPreviousOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);

        date = Util.computeDate("2008-03-23");
        expectedPreviousOrCurrentTradingDate = Util.computeDate("2008-03-20");
        actualPreviousOrCurrentTradingDate = secondAsset.getPreviousOrCurrentTradingDate(date);
        assertEquals(expectedPreviousOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);
    }


    @Test
    @Override
    public void testGetNextOrCurrentTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-23");
        LocalDate expectedNextOrCurrentTradingDate = Util.computeDate("2016-02-23");
        LocalDate actualPreviousOrCurrentTradingDate = secondAsset.getNextOrCurrentTradingDate(date);
        assertEquals(expectedNextOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);

        date = Util.computeDate("2016-02-06");
        expectedNextOrCurrentTradingDate = Util.computeDate("2016-02-08");
        actualPreviousOrCurrentTradingDate = secondAsset.getNextOrCurrentTradingDate(date);
        assertEquals(expectedNextOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);

        date = Util.computeDate("2008-03-21");
        expectedNextOrCurrentTradingDate = Util.computeDate("2008-03-24");
        actualPreviousOrCurrentTradingDate = secondAsset.getNextOrCurrentTradingDate(date);
        assertEquals(expectedNextOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);
    }


    @Test
    @Override
    public void testIsTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-23");
        boolean actualIsTradingDate = secondAsset.isTradingDate(date);
        assertTrue(actualIsTradingDate);

        date = Util.computeDate("2016-01-10");
        actualIsTradingDate = secondAsset.isTradingDate(date);
        assertFalse(actualIsTradingDate);

        date = Util.computeDate("2015-12-19");
        actualIsTradingDate = secondAsset.isTradingDate(date);
        assertFalse(actualIsTradingDate);
    }
}