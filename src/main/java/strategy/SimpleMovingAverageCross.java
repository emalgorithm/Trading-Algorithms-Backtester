package strategy;

import portfolio.Equity;
import portfolio.Portfolio;
import util.TechnicalAnalysis;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by ema on 25/02/16.
 */
public class SimpleMovingAverageCross implements Strategy {
    private final int shortMovingAvgLength;
    private final int longMovingAvgLength;


    public SimpleMovingAverageCross(int shortMovingAvgLength, int longMovingAvgLength) {
        this.shortMovingAvgLength = shortMovingAvgLength;
        this.longMovingAvgLength = longMovingAvgLength;
    }


    @Override
    public void run(Portfolio portfolio) {

        for (Map.Entry<Equity, Integer> equityPosition : portfolio.entrySet()) {
            Equity equity = equityPosition.getKey();
            int position = equityPosition.getValue();
            Double currentMoney = portfolio.getCurrentMoney();
            LocalDate date = portfolio.getCurrentDate();


            Double adjustedClosingPrice = equity.getClosingPrice(date);
            LocalDate previousDate = equity.getPreviousTradingDate(date);

            boolean goldenCross = isGoldenCross(equity, date, previousDate);
            boolean deathCross = isDeathCross(equity, date, previousDate);

            if (goldenCross) {
                //Close short position if it was open
                portfolio.closePosition(equity);

                //Open new long position
                int newPositionSize = ((Double)(currentMoney / adjustedClosingPrice)).intValue();
                portfolio.enterLongPosition(equity, newPositionSize);

            } else if (deathCross) {
                //Close long position if it was open
                portfolio.closePosition(equity);

                //Open new short position
                int newPositionSize = ((Double)(currentMoney / adjustedClosingPrice)).intValue();
                portfolio.enterShortPosition(equity, newPositionSize);
            }
        }

    }


    private boolean isGoldenCross(Equity equity, LocalDate date, LocalDate previousDate) {
        return isGoldenOrDeathCross(equity, date, previousDate, true);
    }


    private boolean isDeathCross(Equity equity, LocalDate date, LocalDate previousDate) {
        return isGoldenOrDeathCross(equity, date, previousDate, false);
    }


    //When 'golden' equals true, the function returns whether it is a Golden Cross, otherwise it
    //returns whether it is a death cross
    private boolean isGoldenOrDeathCross(Equity equity, LocalDate date, LocalDate previousDate,
                                         boolean typeGoldenCross) {
        Double previous20MovingAverage = TechnicalAnalysis.getMovingAverage(equity, previousDate,
                shortMovingAvgLength);
        Double current20MovingAverage = TechnicalAnalysis.getMovingAverage(equity, date,
                shortMovingAvgLength);

        Double previous50MovingAverage = TechnicalAnalysis.getMovingAverage(equity, previousDate,
                longMovingAvgLength);
        Double current50MovingAverage = TechnicalAnalysis.getMovingAverage(equity, date,
                longMovingAvgLength);

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
