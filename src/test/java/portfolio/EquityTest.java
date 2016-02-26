package portfolio;

import org.junit.Test;
import util.Util;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by ema on 24/02/16.
 */
public class EquityTest {

    @Test
    public void testGetMovingAverage() throws Exception, NotEnoughDataException {
        Equity facebook = new Equity("Facebook", "FB");

        double expectedMovingAverage = 105.087;
        int numberOfDays = 20;
        double delta = expectedMovingAverage / 1000;
        LocalDate date = Util.computeDate("2016-02-23");
        double actualMovingAverage = facebook.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, delta);

        expectedMovingAverage = 99.266;
        numberOfDays = 20;
        delta = expectedMovingAverage / 1000;
        date = Util.computeDate("2016-01-29");
        actualMovingAverage = facebook.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, delta);

        expectedMovingAverage = 102.9418;
        numberOfDays = 50;
        delta = expectedMovingAverage / 1000;
        date = Util.computeDate("2016-02-23");
        actualMovingAverage = facebook.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, delta);

        expectedMovingAverage = 103.1612;
        numberOfDays = 50;
        delta = expectedMovingAverage / 1000;
        date = Util.computeDate("2016-02-11");
        actualMovingAverage = facebook.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, delta);


        Equity apple = new Equity("Visa", "V");

        expectedMovingAverage = 72.76765822514862;
        numberOfDays = 200;
        delta = expectedMovingAverage / 1000;
        date = Util.computeDate("2016-02-23");
        actualMovingAverage = apple.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, delta);

        expectedMovingAverage = 63.97488815472064;
        numberOfDays = 200;
        delta = expectedMovingAverage / 1000;
        date = Util.computeDate("2015-07-12");
        actualMovingAverage = apple.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, delta);

    }


    @Test
    public void testToString() throws Exception {
        String name = "Visa";
        Equity visa = new Equity(name, "V");
        assertEquals(name, visa.toString());

        name = "Facebook";
        Equity facebook = new Equity(name, "FB");
        assertEquals(name, facebook.toString());
    }

    @Test
    public void testGetPreviousTradingDate() throws Exception {
        Equity visa = new Equity("Visa", "V");

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
        Equity visa = new Equity("Visa", "V");

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
        Equity visa = new Equity("Visa", "V");

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
        Equity visa = new Equity("Visa", "V");

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
        Equity facebook = new Equity("Facebook", "FB");
        double delta = 0.000001;

        LocalDate date = Util.computeDate("2016-02-22");
        double expectedClosingPrice = 107.16;
        double actualClosingPrice = facebook.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, delta);

        date = Util.computeDate("2013-10-16");
        expectedClosingPrice = 51.14;
        actualClosingPrice = facebook.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, delta);

        date = Util.computeDate("2012-05-18");
        expectedClosingPrice = 38.23;
        actualClosingPrice = facebook.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, delta);

    }

    @Test
    public void testIsTradingDate() throws Exception {
        Equity visa = new Equity("Visa", "V");

        LocalDate date = Util.computeDate("2016-02-23");
        boolean expectedIsTradingdate = true;
        boolean actualIsTradingDate = visa.isTradingDate(date);
        assertEquals(expectedIsTradingdate, actualIsTradingDate);

        date = Util.computeDate("2016-01-10");
        expectedIsTradingdate = false;
        actualIsTradingDate = visa.isTradingDate(date);
        assertEquals(expectedIsTradingdate, actualIsTradingDate);

        date = Util.computeDate("2015-12-19");
        expectedIsTradingdate = false;
        actualIsTradingDate = visa.isTradingDate(date);
        assertEquals(expectedIsTradingdate, actualIsTradingDate);
    }
}