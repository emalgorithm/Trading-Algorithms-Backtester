package portfolio;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.Util;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by ema on 24/02/16.
 */
public class EquityTest {
    private static Equity facebook;
    private static Equity visa;

    @BeforeClass
    public static void oneTimeSetUp() {
        facebook = new Equity("Facebook", "FB");
        visa = new Equity("Visa", "V");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        facebook = null;
        visa = null;
    }

    @Test
    public void testGetMovingAverage() throws Exception, NotEnoughDataException {
        double expectedMovingAverage = 105.07840999999999;
        int numberOfDays = 20;
        LocalDate date = Util.computeDate("2016-02-23");
        double actualMovingAverage = facebook.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

        expectedMovingAverage = 99.251;
        numberOfDays = 20;
        date = Util.computeDate("2016-01-29");
        actualMovingAverage = facebook.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

        expectedMovingAverage = 102.93262400000002;
        numberOfDays = 50;
        date = Util.computeDate("2016-02-23");
        actualMovingAverage = facebook.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

        expectedMovingAverage = 103.15363799999997;
        numberOfDays = 50;
        date = Util.computeDate("2016-02-11");
        actualMovingAverage = facebook.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);


        expectedMovingAverage = 72.76765822514862;
        numberOfDays = 200;
        date = Util.computeDate("2016-02-23");
        actualMovingAverage = visa.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

        expectedMovingAverage = 63.97488815472064;
        numberOfDays = 200;
        date = Util.computeDate("2015-07-12");
        actualMovingAverage = visa.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

    }


    @Test
    public void testToString() throws Exception {
        String name = "Visa";
        assertEquals(name, visa.toString());

        name = "Facebook";
        assertEquals(name, facebook.toString());
    }

    @Test
    public void testGetPreviousTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-23");
        LocalDate expectedPreviousTradingDate = Util.computeDate("2016-02-22");
        LocalDate actualPreviousTradingDate = visa.getPreviousTradingDate(date);
        assertEquals(expectedPreviousTradingDate, actualPreviousTradingDate);

        date = Util.computeDate("2016-02-08");
        expectedPreviousTradingDate = Util.computeDate("2016-02-05");
        actualPreviousTradingDate = visa.getPreviousTradingDate(date);
        assertEquals(expectedPreviousTradingDate, actualPreviousTradingDate);

        date = Util.computeDate("2008-03-24");
        expectedPreviousTradingDate = Util.computeDate("2008-03-20");
        actualPreviousTradingDate = visa.getPreviousTradingDate(date);
        assertEquals(expectedPreviousTradingDate, actualPreviousTradingDate);
    }

    @Test
    public void testGetNextTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-22");
        LocalDate expectedNextTradingDate = Util.computeDate("2016-02-23");
        LocalDate actualNextTradingDate = visa.getNextTradingDate(date);
        assertEquals(expectedNextTradingDate, actualNextTradingDate);

        date = Util.computeDate("2016-01-15");
        expectedNextTradingDate = Util.computeDate("2016-01-19");
        actualNextTradingDate = visa.getNextTradingDate(date);
        assertEquals(expectedNextTradingDate, actualNextTradingDate);

        date = Util.computeDate("2008-03-20");
        expectedNextTradingDate = Util.computeDate("2008-03-24");
        actualNextTradingDate = visa.getNextTradingDate(date);
        assertEquals(expectedNextTradingDate, actualNextTradingDate);
    }

    @Test
    public void testGetPreviousOrCurrentTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-23");
        LocalDate expectedPreviousOrCurrentTradingDate = Util.computeDate("2016-02-23");
        LocalDate actualPreviousOrCurrentTradingDate = visa.getPreviousOrCurrentTradingDate(date);
        assertEquals(expectedPreviousOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);

        date = Util.computeDate("2016-02-07");
        expectedPreviousOrCurrentTradingDate = Util.computeDate("2016-02-05");
        actualPreviousOrCurrentTradingDate = visa.getPreviousOrCurrentTradingDate(date);
        assertEquals(expectedPreviousOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);

        date = Util.computeDate("2008-03-23");
        expectedPreviousOrCurrentTradingDate = Util.computeDate("2008-03-20");
        actualPreviousOrCurrentTradingDate = visa.getPreviousOrCurrentTradingDate(date);
        assertEquals(expectedPreviousOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);
    }

    @Test
    public void testGetNextOrCurrentTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-23");
        LocalDate expectedNextOrCurrentTradingDate = Util.computeDate("2016-02-23");
        LocalDate actualPreviousOrCurrentTradingDate = visa.getNextOrCurrentTradingDate(date);
        assertEquals(expectedNextOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);

        date = Util.computeDate("2016-02-06");
        expectedNextOrCurrentTradingDate = Util.computeDate("2016-02-08");
        actualPreviousOrCurrentTradingDate = visa.getNextOrCurrentTradingDate(date);
        assertEquals(expectedNextOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);

        date = Util.computeDate("2008-03-21");
        expectedNextOrCurrentTradingDate = Util.computeDate("2008-03-24");
        actualPreviousOrCurrentTradingDate = visa.getNextOrCurrentTradingDate(date);
        assertEquals(expectedNextOrCurrentTradingDate, actualPreviousOrCurrentTradingDate);
    }

    @Test
    public void testGetClosingPrice() throws Exception {
        LocalDate date = Util.computeDate("2016-02-22");
        double expectedClosingPrice = 107.16;
        double actualClosingPrice = facebook.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, Util.DELTA);

        date = Util.computeDate("2013-10-16");
        expectedClosingPrice = 51.14;
        actualClosingPrice = facebook.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, Util.DELTA);

        date = Util.computeDate("2012-05-18");
        expectedClosingPrice = 38.23;
        actualClosingPrice = facebook.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, Util.DELTA);

    }

    @Test
    public void testIsTradingDate() throws Exception {
        LocalDate date = Util.computeDate("2016-02-23");
        boolean actualIsTradingDate = visa.isTradingDate(date);
        assertTrue(actualIsTradingDate);

        date = Util.computeDate("2016-01-10");
        actualIsTradingDate = visa.isTradingDate(date);
        assertFalse(actualIsTradingDate);

        date = Util.computeDate("2015-12-19");
        actualIsTradingDate = visa.isTradingDate(date);
        assertFalse(actualIsTradingDate);
    }
}