package portfolio;

import java.time.LocalDate;

/**
 * Created by ema on 18/03/16.
 */
public final class Order {

    private final int size;
    private final LocalDate date;
    private final OrderType type;

    public Order (int size, LocalDate date, OrderType type) {
        this.size = size;
        this.date = date;
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public LocalDate getDate() {
        return date;
    }

    public OrderType getType() {
        return type;
    }
}
