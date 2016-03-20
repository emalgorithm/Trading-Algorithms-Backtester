package portfolio;

import java.time.LocalDate;

/**
 * Created by ema on 06/03/16.
 */
public interface AssetInterface {
    DailyTradingValues getDailyTradingValues(LocalDate date);

    Double getClosingPrice(LocalDate date);

    LocalDate getPreviousTradingDate(LocalDate date);

    LocalDate getPreviousOrCurrentTradingDate(LocalDate date);

    LocalDate getNextTradingDate(LocalDate date);

    LocalDate getNextOrCurrentTradingDate(LocalDate date);

    boolean isTradingDate(LocalDate date);

    LocalDate getStartDate();

    LocalDate getEndDate();
}
