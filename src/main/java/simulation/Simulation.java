package simulation;

import portfolio.Equity;
import portfolio.Portfolio;
import strategy.Strategy;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ema on 24/02/16.
 */
public class Simulation {
    private final Portfolio portfolio;
    private final Strategy strategy;
    private final Double initialMoney;
    private Double gain;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Simulation(List<Equity> positions, Strategy strategy, Double initialMoney, LocalDate
            startDate, LocalDate endDate) {
        this.portfolio = new Portfolio(positions, initialMoney, startDate);
        this.strategy = strategy;
        this.initialMoney = initialMoney;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Double run() {

//        while (portfolio.getCurrentDate().compareTo(endDate) <= 0) {
//            strategy.run(portfolio);
//
//            portfolio.advanceToNextTradingDate();
//        }

        return portfolio.getTotalEquity();
    }

}
