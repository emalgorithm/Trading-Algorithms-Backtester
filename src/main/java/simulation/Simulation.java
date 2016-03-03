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
    //portfolio map an equity to the current amount of that equity in our portfolio
    private final Portfolio portfolio;
    private final Strategy strategy;
    private final double initialMoney;
    private double gain;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Simulation(List<Equity> positions, Strategy strategy, double initialMoney, LocalDate
            startDate, LocalDate endDate) {
        this.portfolio = new Portfolio(positions, initialMoney, startDate);
        this.strategy = strategy;
        this.initialMoney = initialMoney;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double run() {
        //TODO fix the method
        LocalDate date = startDate;

        while (date.compareTo(endDate) <= 0) {
            strategy.run(portfolio);

            portfolio.advanceToNextTradingDate();
        }

        return portfolio.getTotalEquity();
    }

}
