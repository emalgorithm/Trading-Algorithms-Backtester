package portfolio;

import org.junit.Test;
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
    private Equity visa;
    private Equity facebook;
    private double initialMoney;
    LocalDate date;

    public PortfolioTest() {
        visa = new Equity("Visa", "V");
        facebook = new Equity("Facebook", "FB");
        date = Util.computeDate("2016-02-23");
        initialMoney = 10000;

        List<Equity> portfolioEquities = Arrays.asList(visa, facebook);
        portfolio = new Portfolio(portfolioEquities, initialMoney, date);
    }

    @Test
    public void testEnterLongPosition() throws Exception {
        double currentMoney = initialMoney;

        double positionValue = initialMoney * 0.3;                 // 30% of initialMoney
        double expectedPosition = positionValue / visa.getClosingPrice(date);
        portfolio.enterLongPosition(visa, expectedPosition);
        double actualPosition = portfolio.getCurrentPosition(visa);
        double expectedNewCurrentMoney = currentMoney - positionValue;
        double actualNewCurrentMoney = portfolio.getCurrentMoney();
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


        double additionalPositionValue = initialMoney * 0.1;       // 10% of initial money
        double newPosition = additionalPositionValue / facebook.getClosingPrice(date);
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
        double currentMoney = initialMoney;

        double positionValue = initialMoney * 0.3;                 // 30% of initialMoney
        double position = positionValue / visa.getClosingPrice(date);
        double expectedPosition = -position;
        portfolio.enterShortPosition(visa, position);
        double actualPosition = portfolio.getCurrentPosition(visa);
        double expectedNewCurrentMoney = currentMoney + positionValue;
        double actualNewCurrentMoney = portfolio.getCurrentMoney();
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


        double additionalPositionValue = initialMoney * 0.1;       // 10% of initial money
        double newPosition = additionalPositionValue / facebook.getClosingPrice(date);
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

        double visaPositionValue = initialMoney * 0.3;                 // 30% of initialMoney
        double position = visaPositionValue / visa.getClosingPrice(date);
        portfolio.enterLongPosition(visa, position);

        double facebookPositionValue = initialMoney * 0.4;             // 40% of initial money
        position = facebookPositionValue / facebook.getClosingPrice(date);
        portfolio.enterShortPosition(facebook, position);

        double currentMoney = portfolio.getCurrentMoney();
        portfolio.closePosition(visa);
        double expectedPosition = 0.0;
        double actualPosition = portfolio.getCurrentPosition(visa);
        double expectedCurrentMoney = currentMoney + visaPositionValue;
        double actualCurrentMoney = portfolio.getCurrentMoney();

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
        double position = 3.0;
        double firstDayPrice = 105.46;
        double firstDayEquityValue = position * firstDayPrice;
        double secondDayPrice = 106.88;
        double secondDayEquityValue = position * secondDayPrice;
        double expectedTotalEquity = initialMoney + (secondDayEquityValue - firstDayEquityValue);
        portfolio.enterLongPosition(facebook, position);
        portfolio.advanceToNextTradingDate();
        double actualTotalEquity = portfolio.getTotalEquity();

        assertEquals(expectedTotalEquity, actualTotalEquity, Util.DELTA);
    }

    @Test
    public void testGetCurrentMoney() throws Exception {
        double expectedCurrentMoney = initialMoney;
        double actualCurrentMoney = portfolio.getCurrentMoney();

        assertEquals(expectedCurrentMoney, actualCurrentMoney, Util.DELTA);
    }

    @Test
    public void testIsLongPositionOpen() throws Exception {
        boolean expectedIsLongPositionOpen = false;
        boolean actualIsLongPositionOpen = portfolio.isLongPositionOpen(facebook);

        assertEquals(expectedIsLongPositionOpen, actualIsLongPositionOpen);


        double position = 0.000002;
        portfolio.enterLongPosition(facebook, position);
        expectedIsLongPositionOpen = true;
        actualIsLongPositionOpen = portfolio.isLongPositionOpen(facebook);

        assertEquals(expectedIsLongPositionOpen, actualIsLongPositionOpen);


        portfolio.enterShortPosition(facebook, position);
        expectedIsLongPositionOpen = false;
        actualIsLongPositionOpen = portfolio.isLongPositionOpen(facebook);

        assertEquals(expectedIsLongPositionOpen, actualIsLongPositionOpen);

    }

    @Test
    public void testIsShortPositionOpen() throws Exception {
        boolean expectedIsShortPositionOpen = false;
        boolean actualIsShortPositionOpen = portfolio.isShortPositionOpen(facebook);

        assertEquals(expectedIsShortPositionOpen, actualIsShortPositionOpen);


        double position = 0.000002;
        portfolio.enterShortPosition(facebook, position);
        expectedIsShortPositionOpen = true;
        actualIsShortPositionOpen = portfolio.isShortPositionOpen(facebook);

        assertEquals(expectedIsShortPositionOpen, actualIsShortPositionOpen);


        portfolio.enterLongPosition(facebook, position);
        expectedIsShortPositionOpen = false;
        actualIsShortPositionOpen = portfolio.isShortPositionOpen(facebook);

        assertEquals(expectedIsShortPositionOpen, actualIsShortPositionOpen);
    }

    @Test
    public void testGetCurrentDate() throws Exception {
        LocalDate expectedDate = Util.computeDate("2016-02-23");
        LocalDate actualDate = portfolio.getCurrentDate();

        assertEquals(expectedDate, actualDate);


        portfolio.advanceToNextTradingDate();
        expectedDate = Util.computeDate("2016-02-24");
        actualDate = portfolio.getCurrentDate();

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testGetCurrentPosition() throws Exception {
        double expectedPosition = 3;
        portfolio.enterLongPosition(facebook, expectedPosition);
        double actualPosition = portfolio.getCurrentPosition(facebook);

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
        boolean expectedContainsEquity = true;
        boolean actualContainsEquity = portfolio.containsEquity(facebook);

        assertEquals(expectedContainsEquity, actualContainsEquity);


        Equity linkedin = new Equity("linkedin", "LNKD");
        expectedContainsEquity = false;
        actualContainsEquity = portfolio.containsEquity(linkedin);

        assertEquals(expectedContainsEquity, actualContainsEquity);
    }
}