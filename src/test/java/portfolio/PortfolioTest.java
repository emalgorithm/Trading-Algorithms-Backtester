package portfolio;

import org.junit.*;
import util.Util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ema on 26/02/16.
 */
public class PortfolioTest {
    private Portfolio portfolio;
    private static Equity visa;
    private static Equity facebook;
    private static Double initialMoney;
    private static LocalDate date;

    @BeforeClass
    public static void oneTimeSetUp() {
        visa = new Equity("Visa", "V");
        facebook = new Equity("Facebook", "FB");
        date = Util.computeDate("2016-02-23");
        initialMoney = 10000.0;
    }

    @AfterClass
    public static void oneTimeTearDown() {
        visa = null;
        facebook = null;
        date = null;
    }

    @Before
    public void setUp() {
        List<Equity> portfolioEquities = Arrays.asList(visa, facebook);
        portfolio = new Portfolio(portfolioEquities, initialMoney, date);
    }

    @After
    public void tearDown() {
        portfolio = null;
    }

    @Test
    public void testEnterLongPosition() throws Exception {
        Double currentMoney = initialMoney;

        Double positionValue = initialMoney * 0.3;                 // 30% of initialMoney
        Double expectedPosition = positionValue / visa.getClosingPrice(date);
        portfolio.enterLongPosition(visa, expectedPosition);
        Integer actualPosition = portfolio.getCurrentPosition(visa);
        Double expectedNewCurrentMoney = currentMoney - positionValue;
        Double actualNewCurrentMoney = portfolio.getCurrentMoney();
        currentMoney = actualNewCurrentMoney;

        assertEquals(expectedPosition, actualPosition, Util.DELTA);
        assertEquals(expectedNewCurrentMoney, actualNewCurrentMoney, Util.DELTA);


        positionValue = initialMoney * 0.4;                         // 40% of initial money
        expectedPosition = positionValue / facebook.getClosingPrice(date);
        portfolio.enterLongPosition(facebook, expectedPosition);
        actualPosition = portfolio.getCurrentPosition(facebook);
        expectedNewCurrentMoney = currentMoney - positionValue;
        actualNewCurrentMoney = portfolio.getCurrentMoney();
        currentMoney = actualNewCurrentMoney;

        assertEquals(expectedPosition, actualPosition, Util.DELTA);
        assertEquals(expectedNewCurrentMoney, actualNewCurrentMoney, Util.DELTA);


        Double additionalPositionValue = initialMoney * 0.1;       // 10% of initial money
        Double newPosition = additionalPositionValue / facebook.getClosingPrice(date);
        portfolio.enterLongPosition(facebook, newPosition);
        positionValue += additionalPositionValue;
        expectedPosition = positionValue / facebook.getClosingPrice(date);
        actualPosition = portfolio.getCurrentPosition(facebook);
        expectedNewCurrentMoney = currentMoney - additionalPositionValue;
        actualNewCurrentMoney = portfolio.getCurrentMoney();

        assertEquals(expectedPosition, actualPosition, Util.DELTA);
        assertEquals(expectedNewCurrentMoney, actualNewCurrentMoney, Util.DELTA);

    }

    @Test
    public void testEnterShortPosition() throws Exception {
        Double currentMoney = initialMoney;

        Double positionValue = initialMoney * 0.3;                 // 30% of initialMoney
        Double position = positionValue / visa.getClosingPrice(date);
        Double expectedPosition = -position;
        portfolio.enterShortPosition(visa, position);
        Integer actualPosition = portfolio.getCurrentPosition(visa);
        Double expectedNewCurrentMoney = currentMoney + positionValue;
        Double actualNewCurrentMoney = portfolio.getCurrentMoney();
        currentMoney = actualNewCurrentMoney;

        assertEquals(expectedPosition, actualPosition, Util.DELTA);
        assertEquals(expectedNewCurrentMoney, actualNewCurrentMoney, Util.DELTA);


        positionValue = initialMoney * 0.4;                         // 40% of initial money
        position = positionValue / facebook.getClosingPrice(date);
        expectedPosition = -position;
        portfolio.enterShortPosition(facebook, position);
        actualPosition = portfolio.getCurrentPosition(facebook);
        expectedNewCurrentMoney = currentMoney + positionValue;
        actualNewCurrentMoney = portfolio.getCurrentMoney();
        currentMoney = actualNewCurrentMoney;

        assertEquals(expectedPosition, actualPosition, Util.DELTA);
        assertEquals(expectedNewCurrentMoney, actualNewCurrentMoney, Util.DELTA);


        Double additionalPositionValue = initialMoney * 0.1;       // 10% of initial money
        Double newPosition = additionalPositionValue / facebook.getClosingPrice(date);
        portfolio.enterShortPosition(facebook, newPosition);
        positionValue += additionalPositionValue;
        expectedPosition = -(positionValue / facebook.getClosingPrice(date));
        actualPosition = portfolio.getCurrentPosition(facebook);
        expectedNewCurrentMoney = currentMoney + additionalPositionValue;
        actualNewCurrentMoney = portfolio.getCurrentMoney();

        assertEquals(expectedPosition, actualPosition, Util.DELTA);
        assertEquals(expectedNewCurrentMoney, actualNewCurrentMoney, Util.DELTA);

    }

    @Test
    public void testClosePosition() throws Exception {

        Double visaPositionValue = initialMoney * 0.3;                 // 30% of initialMoney
        Double position = visaPositionValue / visa.getClosingPrice(date);
        portfolio.enterLongPosition(visa, position);

        Double facebookPositionValue = initialMoney * 0.4;             // 40% of initial money
        position = facebookPositionValue / facebook.getClosingPrice(date);
        portfolio.enterShortPosition(facebook, position);

        Double currentMoney = portfolio.getCurrentMoney();
        portfolio.closePosition(visa);
        Double expectedPosition = 0.0;
        Integer actualPosition = portfolio.getCurrentPosition(visa);
        Double expectedCurrentMoney = currentMoney + visaPositionValue;
        Double actualCurrentMoney = portfolio.getCurrentMoney();

        assertEquals(expectedPosition, actualPosition, Util.DELTA);
        assertEquals(expectedCurrentMoney, actualCurrentMoney, Util.DELTA);


        currentMoney = portfolio.getCurrentMoney();
        portfolio.closePosition(facebook);
        expectedPosition = 0.0;
        actualPosition = portfolio.getCurrentPosition(facebook);
        expectedCurrentMoney = currentMoney - facebookPositionValue;
        actualCurrentMoney = portfolio.getCurrentMoney();

        assertEquals(expectedPosition, actualPosition, Util.DELTA);
        assertEquals(expectedCurrentMoney, actualCurrentMoney, Util.DELTA);

    }

    @Test
    public void testGetTotalEquity() throws Exception {
        Double position = 3.0;
        Double firstDayPrice = 105.46;
        Double firstDayEquityValue = position * firstDayPrice;
        Double secondDayPrice = 106.88;
        Double secondDayEquityValue = position * secondDayPrice;
        Double expectedTotalEquity = initialMoney + (secondDayEquityValue - firstDayEquityValue);
        portfolio.enterLongPosition(facebook, position);
        portfolio.advanceToNextTradingDate();
        Double actualTotalEquity = portfolio.getTotalEquity();

        assertEquals(expectedTotalEquity, actualTotalEquity, Util.DELTA);
    }

    @Test
    public void testGetCurrentMoney() throws Exception {
        Double expectedCurrentMoney = initialMoney;
        Double actualCurrentMoney = portfolio.getCurrentMoney();
        assertEquals(expectedCurrentMoney, actualCurrentMoney, Util.DELTA);
    }

    @Test
    public void testIsLongPositionOpen() throws Exception {
        boolean actualIsLongPositionOpen = portfolio.isLongPositionOpen(facebook);
        assertFalse(actualIsLongPositionOpen);

        Double position = 0.000002;
        portfolio.enterLongPosition(facebook, position);
        actualIsLongPositionOpen = portfolio.isLongPositionOpen(facebook);
        assertTrue(actualIsLongPositionOpen);

        portfolio.enterShortPosition(facebook, position);
        actualIsLongPositionOpen = portfolio.isLongPositionOpen(facebook);
        assertFalse(actualIsLongPositionOpen);

    }

    @Test
    public void testIsShortPositionOpen() throws Exception {
        boolean actualIsShortPositionOpen = portfolio.isShortPositionOpen(facebook);
        assertFalse(actualIsShortPositionOpen);

        Double position = 0.000002;
        portfolio.enterShortPosition(facebook, position);
        actualIsShortPositionOpen = portfolio.isShortPositionOpen(facebook);
        assertTrue(actualIsShortPositionOpen);

        portfolio.enterLongPosition(facebook, position);
        actualIsShortPositionOpen = portfolio.isShortPositionOpen(facebook);
        assertFalse(actualIsShortPositionOpen);
    }

    @Test
    public void testGetCurrentDate() throws Exception {
        LocalDate expectedDate = Util.computeDate("2016-02-23");
        LocalDate actualDate = portfolio.getCurrentDate();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testGetCurrentPosition() throws Exception {
        Double expectedPosition = 3.0;
        portfolio.enterLongPosition(facebook, expectedPosition);
        Integer actualPosition = portfolio.getCurrentPosition(facebook);
        assertEquals(expectedPosition, actualPosition, Util.DELTA);

        expectedPosition = 0.0;
        actualPosition = portfolio.getCurrentPosition(visa);
        assertEquals(expectedPosition, actualPosition, Util.DELTA);
    }

    @Test
    public void testAdvanceToNextTradingDate() throws Exception {
        portfolio.advanceToNextTradingDate();
        LocalDate expectedCurrentDate = Util.computeDate("2016-02-24");
        LocalDate actualCurrentDate = portfolio.getCurrentDate();
        assertEquals(expectedCurrentDate, actualCurrentDate);

        portfolio.advanceToNextTradingDate();
        portfolio.advanceToNextTradingDate();
        portfolio.advanceToNextTradingDate();
        expectedCurrentDate = Util.computeDate("2016-02-29");
        actualCurrentDate = portfolio.getCurrentDate();
        assertEquals(expectedCurrentDate, actualCurrentDate);

    }

    @Test
    public void testIsEquityInPortfolio() throws Exception {
        boolean actualContainsEquity = portfolio.containsEquity(facebook);
        assertTrue(actualContainsEquity);

        Equity linkedin = new Equity("linkedin", "LNKD");
        actualContainsEquity = portfolio.containsEquity(linkedin);
        assertFalse(actualContainsEquity);
    }
}