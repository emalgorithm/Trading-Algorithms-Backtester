package strategy;

import portfolio.Portfolio;

import java.time.LocalDate;

/**
 * Created by ema on 25/02/16.
 */
public interface Strategy {

    void run(Portfolio portfolio, LocalDate date);

}
