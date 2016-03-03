
import portfolio.Equity;
import portfolio.NotEnoughDataException;
import simulation.Simulation;
import strategy.SimpleMovingAverageCross;
import strategy.Strategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ema on 21/02/16.
 */
public class Main {
    public static void main(String[] args) throws NotEnoughDataException {
        List<Equity> equities = new ArrayList<>();
        Equity apple = new Equity("Facebook", "AXP");
        equities.add(apple);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse("2010-02-22", dateFormat);
        LocalDate endDate = LocalDate.parse("2016-02-22", dateFormat);

        Strategy strategy = new SimpleMovingAverageCross(20, 50);

        Simulation simulation = new Simulation(equities, strategy, 100, startDate, endDate);

        System.out.println(simulation.run());

    }

}
