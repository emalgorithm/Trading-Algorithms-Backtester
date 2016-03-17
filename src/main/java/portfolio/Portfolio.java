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
    private Double currentMoney;
    private LocalDate currentDate;


    public Portfolio(List<Equity> portfolioEquities, Double currentMoney, LocalDate currentDate) {
        this.currentMoney = currentMoney;
        this.currentDate = currentDate;
        this.positions = new HashMap<>();

        for(Equity equity : portfolioEquities) {
            positions.put(equity, 0.0);
        }
    }


    public void enterLongPosition(Equity equity, Double position) throws
            NotEnoughMoneyException {

        Double price = equity.getClosingPrice(currentDate);
        Double positionCost = position * price;

        if (currentMoney.compareTo(positionCost) < 0) {
            throw new NotEnoughMoneyException("Not enough money to open this long position, " +
                    "money required: " + positionCost + ", actual money: " + currentMoney);
        }

        currentMoney -= positionCost;

        Double currentPosition = positions.get(equity);
        Double newPosition = currentPosition + position;

        addPosition(equity, newPosition);
    }


    public void enterShortPosition(Equity equity, Double position) {
        enterLongPosition(equity, -position);
    }


    public void closePosition(Equity equity) {
        Double price = equity.getClosingPrice(currentDate);
        Double position = positions.get(equity);

        currentMoney += position * price;

        addPosition(equity, 0.0);

    }


    public boolean isLongPositionOpen(Equity equity) {
        Double position = getCurrentPosition(equity);

        return Double.compare(position, 0.0) == 1;
    }


    public boolean isShortPositionOpen(Equity equity) {
        Double position = getCurrentPosition(equity);

        return Double.compare(position, 0.0) == -1;
    }


    public void advanceToNextTradingDate() {
        currentDate = getNextTradingDate();
    }


    public LocalDate getCurrentDate() {
        return currentDate;
    }


    public Double getCurrentPosition(Equity equity) {
        if (!containsEquity(equity)) {
            return 0.0;
        }

        return positions.get(equity);
    }


    public boolean containsEquity(Equity equity) {
        return positions.containsKey(equity);
    }


    public Double getTotalEquity() {
        Double positionsEquity = 0.0;

        for (Map.Entry<Equity, Double> equityPosition : entrySet()) {
            Double position = equityPosition.getValue();
            Double price = equityPosition.getKey().getClosingPrice(currentDate);
            Double positionEquity = position * price;
            positionsEquity += positionEquity;
        }

        return positionsEquity + getCurrentMoney();
    }


    public Set<Map.Entry<Equity, Double>> entrySet() {
        return positions.entrySet();
    }


    public Double getCurrentMoney() {
        return currentMoney;
    }


    private void addPosition(Equity equity, Double position) {
        positions.put(equity, position);
    }


    private LocalDate getNextTradingDate() throws EmptyPortfolioException, NotEnoughDataException {
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



}
