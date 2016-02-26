package portfolio;

/**
 * Created by ema on 25/02/16.
 */
public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(String message) {
        super(message);
    }


    public NotEnoughMoneyException() {
        super();
    }

}
