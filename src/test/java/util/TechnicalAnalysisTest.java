package util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import portfolio.Equity;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by ema on 07/03/16.
 */
public class TechnicalAnalysisTest {
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
    public void testGetMovingAverage() throws Exception {
        Double expectedMovingAverage = 105.07840999999999;
        int numberOfDays = 20;
        LocalDate date = Util.computeDate("2016-02-23");
        Double actualMovingAverage = TechnicalAnalysis.getMovingAverage(facebook, date,
                numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

        expectedMovingAverage = 99.251;
        numberOfDays = 20;
        date = Util.computeDate("2016-01-29");
        actualMovingAverage = TechnicalAnalysis.getMovingAverage(facebook, date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

        expectedMovingAverage = 102.93262400000002;
        numberOfDays = 50;
        date = Util.computeDate("2016-02-23");
        actualMovingAverage = TechnicalAnalysis.getMovingAverage(facebook, date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

        expectedMovingAverage = 103.15363799999997;
        numberOfDays = 50;
        date = Util.computeDate("2016-02-11");
        actualMovingAverage = TechnicalAnalysis.getMovingAverage(facebook, date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);


        expectedMovingAverage = 72.76765822514862;
        numberOfDays = 200;
        date = Util.computeDate("2016-02-23");
        actualMovingAverage = TechnicalAnalysis.getMovingAverage(visa, date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);

        expectedMovingAverage = 63.97488815472064;
        numberOfDays = 200;
        date = Util.computeDate("2015-07-12");
        actualMovingAverage = TechnicalAnalysis.getMovingAverage(visa, date, numberOfDays);
        assertEquals(expectedMovingAverage, actualMovingAverage, Util.DELTA);
    }
}