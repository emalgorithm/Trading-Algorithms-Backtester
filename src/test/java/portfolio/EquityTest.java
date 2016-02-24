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


        Equity apple = new Equity("Apple", "AAPL");

        expectedMovingAverage = 113.803568;
        numberOfDays = 200;
        delta = expectedMovingAverage / 1000;
        date = Util.computeDate("2016-02-23");
        actualMovingAverage = apple.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, delta);

        expectedMovingAverage = 116.522175;
        numberOfDays = 200;
        delta = expectedMovingAverage / 1000;
        date = Util.computeDate("2015-07-12");
        actualMovingAverage = apple.getMovingAverage(date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, delta);

    }


    @Test
    public void testToString() throws Exception {
        String name = "Apple";
        Equity apple = new Equity(name, "AAPL");
        assertEquals(name, apple.toString());

        name = "Netflix";
        Equity netflix = new Equity(name, "NFLX");
        assertEquals(name, netflix.toString());
    }
}