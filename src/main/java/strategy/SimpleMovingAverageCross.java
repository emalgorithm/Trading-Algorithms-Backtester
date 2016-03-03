package strategy;

import portfolio.Equity;
import portfolio.Portfolio;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by ema on 25/02/16.
 */
public class SimpleMovingAverageCross implements Strategy {
    private final int shortMovingAverageLength;
    private final int longMovingAverageLength;


    public SimpleMovingAverageCross(int shortMovingAverageLength, int longMovingAverageLength) {
        this.shortMovingAverageLength = shortMovingAverageLength;
        this.longMovingAverageLength = longMovingAverageLength;
    }


    @Override
    public void run(Portfolio portfolio) {

        for (Map.Entry<Equity, Double> equityPosition : portfolio.entrySet()) {
            Equity equity = equityPosition.getKey();
            double position = equityPosition.getValue();
            double currentMoney = portfolio.getCurrentMoney();
            LocalDate date = portfolio.getCurrentDate();


            double adjustedClosingPrice = equity.getClosingPrice(date);
            LocalDate previousDate = equity.getPreviousTradingDate(date);

            boolean goldenCross = isGoldenCross(equity, date, previousDate);
            boolean deathCross = isDeathCross(equity, date, previousDate);

            if (goldenCross) {
                //Close short position if it was open
                portfolio.closePosition(equity);

                //Open new long position
                double newPositionSize = currentMoney / adjustedClosingPrice;
                portfolio.enterLongPosition(equity, newPositionSize);

            } else if (deathCross) {
                //Close long position if it was open
                portfolio.closePosition(equity);

                //Open new short position
                double newPositionSize = currentMoney / adjustedClosingPrice;
                portfolio.enterShortPosition(equity, newPositionSize);
            }
        }

    }


    private boolean isGoldenCross(Equity equity, LocalDate date, LocalDate previousDate) {
        boolean typeGoldenCross = true;
        return isGoldenOrDeathCross(equity, date, previousDate, typeGoldenCross);
    }


    private boolean isDeathCross(Equity equity, LocalDate date, LocalDate previousDate) {
        boolean typeGoldenCross = false;
        return isGoldenOrDeathCross(equity, date, previousDate, typeGoldenCross);
    }


    //When 'golden' equals true, the function returns whether it is a Golden Cross, otherwise it
    //returns whether it is a death cross
    private boolean isGoldenOrDeathCross(Equity equity, LocalDate date, LocalDate previousDate,
                                         boolean typeGoldenCross) {
        int shortMovingAvgLength = 20;
        double previous20MovingAverage = equity.getMovingAverage(previousDate,
                shortMovingAvgLength);
        double current20MovingAverage = equity.getMovingAverage(date, shortMovingAvgLength);

        int longMovingAvgLength = 50;
        double previous50MovingAverage = equity.getMovingAverage(previousDate,
                longMovingAvgLength);
        double current50MovingAverage = equity.getMovingAverage(date, longMovingAvgLength);

        if (typeGoldenCross) {
            boolean isGoldenCross = previous20MovingAverage < previous50MovingAverage &&
                    current20MovingAverage > current50MovingAverage;

            return isGoldenCross;
        } else {
            boolean isDeathCross = previous20MovingAverage > previous50MovingAverage &&
                    current20MovingAverage < current50MovingAverage;

            return isDeathCross;
        }

    }

}
