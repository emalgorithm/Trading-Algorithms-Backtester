package portfolio;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.Util;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by ema on 07/03/16.
 */
public class EquityTest extends AssetTest {

    @BeforeClass
    public static void oneTimeSetUp() {
        AssetTest.firstAsset = new Equity("Facebook", "FB");
        AssetTest.secondAsset = new Equity("Visa", "V");
    }


    @Test
    @Override
    public void testToString() throws Exception {
        String name = "Visa";
        assertEquals(name, secondAsset.toString());

        name = "Facebook";
        assertEquals(name, firstAsset.toString());
    }


    @Test
    @Override
    public void testGetClosingPrice() throws Exception {
        LocalDate date = Util.computeDate("2016-02-22");
        Double expectedClosingPrice = 107.16;
        Double actualClosingPrice = firstAsset.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, Util.DELTA);

        date = Util.computeDate("2013-10-16");
        expectedClosingPrice = 51.14;
        actualClosingPrice = firstAsset.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, Util.DELTA);

        date = Util.computeDate("2012-05-18");
        expectedClosingPrice = 38.23;
        actualClosingPrice = firstAsset.getClosingPrice(date);
        assertEquals(expectedClosingPrice, actualClosingPrice, Util.DELTA);

    }

}