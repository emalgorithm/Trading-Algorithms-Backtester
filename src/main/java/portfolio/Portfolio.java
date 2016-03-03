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
public class Portfolio {
    private final Map<Equity, Double> positions;
    private double currentMoney;
    private LocalDate currentDate;


    public Portfolio(List<Equity> portfolioEquities, double currentMoney, LocalDate currentDate) {
        this.currentMoney = currentMoney;
        this.currentDate = currentDate;
        this.positions = new HashMap<>();

        for(Equity equity : portfolioEquities) {
            positions.put(equity, 0.0);
        }
    }


    public void enterLongPosition(Equity equity, double position) throws
            NotEnoughMoneyException {

        double price = equity.getClosingPrice(currentDate);
        double positionCost = position * price;

        if (Util.compare(currentMoney, positionCost) < 0) {
            throw new NotEnoughMoneyException("Not enough money to open this long position, " +
                    "money required: " + positionCost + ", actual money: " + currentMoney);
        }

        currentMoney -= positionCost;

        double currentPosition = positions.get(equity);
        double newPosition = currentPosition + position;

        addPosition(equity, newPosition);
    }


    public void enterShortPosition(Equity equity, double position) {
        enterLongPosition(equity, -position);
    }


    //Close any kind of position
    public void closePosition(Equity equity) {
        double price = equity.getClosingPrice(currentDate);
        double position = positions.get(equity);

        currentMoney += position * price;

        addPosition(equity, 0.0);

    }


    public boolean isLongPositionOpen(Equity equity) {
        double position = getCurrentPosition(equity);

        return Double.compare(position, 0.0) == 1;
    }


    public boolean isShortPositionOpen(Equity equity) {
        double position = getCurrentPosition(equity);

        return Double.compare(position, 0.0) == -1;
    }


    private LocalDate getNextTradingDate() throws EmptyPortfolioException {
        if (positions.isEmpty()) {
            throw new EmptyPortfolioException("It is not possible to get the next trading " +
                    "date of an empty portfolio");
        }

        LocalDate nextTradingDate = null;

        for (Map.Entry<Equity, Double> position : positions.entrySet()) {
            Equity equity = position.getKey();
            LocalDate equityNextTradingDate = equity.getNextTradingDate(currentDate);

            if(nextTradingDate == null || equityNextTradingDate.compareTo(nextTradingDate) < 0) {
                nextTradingDate = equityNextTradingDate;
            }
        }

        return nextTradingDate;
    }


    public void advanceToNextTradingDate() {
        currentDate = getNextTradingDate();
    }


    public LocalDate getCurrentDate() {
        return currentDate;
    }


    public double getCurrentPosition(Equity equity) {
        if (!containsEquity(equity)) {
            return 0.0;
        }

        return positions.get(equity);
    }


    private void addPosition(Equity equity, double position) {
        positions.put(equity, position);
    }


    public boolean containsEquity(Equity equity) {
        return positions.containsKey(equity);
    }


    public double getTotalEquity() {
        double positionsEquity = 0.0;

        for (Map.Entry<Equity, Double> equityPosition : entrySet()) {
            double position = equityPosition.getValue();
            double price = equityPosition.getKey().getClosingPrice(currentDate);
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
