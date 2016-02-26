package portfolio;

import util.Util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ema on 25/02/16.
 */
//TODO think about internalizing the current date into portfolio
public class Portfolio {
    private final Map<Equity, Double> positions;
    private double currentMoney;


    public Portfolio(List<Equity> portfolioEquities, double currentMoney) {
        this.currentMoney = currentMoney;
        this.positions = new HashMap<>();

        for(Equity equity : portfolioEquities) {
            positions.put(equity, 0.0);
        }
    }


    public void enterLongPosition(Equity equity, double position, LocalDate date) throws
            NotEnoughMoneyException {

        double price = equity.getClosingPrice(date);
        double positionCost = position * price;

        if (Util.compare(currentMoney, positionCost) < 0) {
            throw new NotEnoughMoneyException("Not enough money to open this long position, " +
                    "money required: " + positionCost + ", actual money: " + currentMoney);
        }

        currentMoney -= positionCost;

        double currentPosition = positions.get(equity);
        double newPosition = currentPosition + position;

        addEquity(equity, newPosition);
    }


    public void enterShortPosition(Equity equity, double position, LocalDate date) {
        enterLongPosition(equity, -position, date);
    }


    public void closeLongPosition(Equity equity, LocalDate date) {
        closePositionHelper(equity, date, 1);
    }


    public void closeShortPosition(Equity equity, LocalDate date) {
        closePositionHelper(equity, date, -1);
    }


    //Close any kind of position
    public void closePosition(Equity equity, LocalDate date) {
        double currentPosition = positions.get(equity);

        if (currentPosition < 0.0) {
            closeShortPosition(equity, date);
        } else {
            closeLongPosition(equity, date);
        }

    }


    //To close a long position sign needs to be 1, and to close a short position -1
    private void closePositionHelper(Equity equity, LocalDate date, int sign) {
        double price = equity.getClosingPrice(date);
        double position = positions.get(equity);

        currentMoney += sign * position * price;

        addEquity(equity, 0.0);
    }


    public LocalDate getNextTradingDate(LocalDate date) throws EmptyPortfolioException {
        if (positions.isEmpty()) {
            throw new EmptyPortfolioException("It is not possible to get the next trading " +
                    "date of an empty portfolio");
        }

        LocalDate nextTradingDate = null;

        for (Map.Entry<Equity, Double> position : positions.entrySet()) {
            Equity equity = position.getKey();
            LocalDate equityNextTradingDate = equity.getNextTradingDate(date);

            if(nextTradingDate == null || equityNextTradingDate.compareTo(nextTradingDate) < 0) {
                nextTradingDate = equityNextTradingDate;
            }
        }

        return nextTradingDate;
    }


    public void addEquity(Equity equity, double position) {
        positions.put(equity, position);
    }


    public double getTotalEquity(LocalDate date) {
        double positionsEquity = 0.0;

        for (Map.Entry<Equity, Double> equityPosition : entrySet()) {
            double position = equityPosition.getValue();
            double price = equityPosition.getKey().getClosingPrice(date);
            double positionEquity = position * price;
            positionsEquity += positionEquity;
        }

        return positionsEquity + getCurrentMoney();
    }


    public Set<Map.Entry<Equity, Double>> entrySet() {
        return positions.entrySet();
    }


    public double getCurrentMoney() {
        return currentMoney;
    }
}
