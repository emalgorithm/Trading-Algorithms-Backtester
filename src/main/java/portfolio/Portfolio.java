package portfolio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ema on 25/02/16.
 */
public class Portfolio {
    private final Map<Equity, Integer> currentPositions;
    private final Map<Equity, List<Order>> allOrders;
    private Double currentMoney;
    private LocalDate currentDate;


    public Portfolio(List<Equity> portfolioEquities, Double currentMoney, LocalDate currentDate) {
        this.currentMoney = currentMoney;
        this.currentDate = currentDate;
        this.currentPositions = new HashMap<>();

        for(Equity equity : portfolioEquities) {
            currentPositions.put(equity, 0);
        }

        this.allOrders = new HashMap<>();
    }


    /**
     * Enter a given long position for a given equity.
     * If the portfolio does not have enough money to fill the position, a NotEnoughMoneyException
     * is thrown.
     * @param equity
     * @param position
     * @throws NotEnoughMoneyException
     */
    public void enterLongPosition(Equity equity, int position) throws
            NotEnoughMoneyException {
        //TODO update test with allPositions
        //Add order to order list
        Order order = new Order(position, currentDate, OrderType.LONG);
        allOrders.get(equity).add(order);

        //Update position
        Double price = equity.getClosingPrice(currentDate);
        Double positionCost = position * price;

        if (currentMoney.compareTo(positionCost) < 0) {
            throw new NotEnoughMoneyException("Not enough money to open this long position, " +
                    "money required: " + positionCost + ", actual money: " + currentMoney);
        }

        currentMoney -= positionCost;

        int currentPosition = currentPositions.get(equity);
        int newPosition = currentPosition + position;

        currentPositions.put(equity, newPosition);
    }

    /**
     * Enter a long position computed using the given amount of money, for the given equity
     * If the portfolio does not have enough money to fill the position, a NotEnoughMoneyException
     * is thrown.
     * @param equity
     * @param moneyValue
     * @throws NotEnoughMoneyException
     */
    public void enterLongPosition(Equity equity, Double moneyValue) throws
            NotEnoughMoneyException {

        Double price = equity.getClosingPrice(currentDate);
        int position = ((Double)(moneyValue / price)).intValue();

        enterLongPosition(equity, position);
    }


    /**
     * Enter a given short position for a given equity
     * @param equity
     * @param position
     */
    public void enterShortPosition(Equity equity, int position) {
        enterLongPosition(equity, -position);
    }


    /**
     * Enter a short position computed using the given amount of money, for the given equity
     * @param equity
     * @param moneyValue
     */
    public void enterShortPosition(Equity equity, Double moneyValue) {
        Double price = equity.getClosingPrice(currentDate);
        int position = ((Double)(moneyValue / price)).intValue();

        enterShortPosition(equity, position);
    }


    public void closePosition(Equity equity) {
        Double price = equity.getClosingPrice(currentDate);
        Integer position = currentPositions.get(equity);

        currentMoney += position * price;

        currentPositions.put(equity, 0);
    }


    public boolean isLongPositionOpen(Equity equity) {
        Integer position = getCurrentPosition(equity);

        return position > 0;
    }


    public boolean isShortPositionOpen(Equity equity) {
        Integer position = getCurrentPosition(equity);

        return position < 0;
    }


    public void advanceToNextTradingDate() {
        currentDate = getNextTradingDate();
    }


    public LocalDate getCurrentDate() {
        return currentDate;
    }


    public Integer getCurrentPosition(Equity equity) {
        if (!containsEquity(equity)) {
            return 0;
        }

        return currentPositions.get(equity);
    }


    public boolean containsEquity(Equity equity) {
        return currentPositions.containsKey(equity);
    }


    public Double getTotalEquity() {
        Double positionsEquity = 0.0;

        for (Map.Entry<Equity, Integer> equityPosition : entrySet()) {
            Integer position = equityPosition.getValue();
            Double price = equityPosition.getKey().getClosingPrice(currentDate);
            Integer positionEquity = ((Double)(position * price)).intValue();
            positionsEquity += positionEquity;
        }

        return positionsEquity + getCurrentMoney();
    }


    public Set<Map.Entry<Equity, Integer>> entrySet() {
        return currentPositions.entrySet();
    }


    public Double getCurrentMoney() {
        return currentMoney;
    }


    private LocalDate getNextTradingDate() throws EmptyPortfolioException, NotEnoughDataException {
        if (currentPositions.isEmpty()) {
            throw new EmptyPortfolioException("It is not possible to get the next trading " +
                    "date of an empty portfolio");
        }

        LocalDate nextTradingDate = null;

        for (Map.Entry<Equity, Integer> position : currentPositions.entrySet()) {
            Equity equity = position.getKey();
            LocalDate equityNextTradingDate = equity.getNextTradingDate(currentDate);

            if(nextTradingDate == null || equityNextTradingDate.compareTo(nextTradingDate) < 0) {
                nextTradingDate = equityNextTradingDate;
            }
        }

        return nextTradingDate;
    }

}
