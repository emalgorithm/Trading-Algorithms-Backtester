package portfolio;

/**
 * Created by ema on 24/02/16.
 */
public class NotEnoughDataException extends Throwable {

    public NotEnoughDataException(String message) {
        super(message);
    }

    public NotEnoughDataException() {
        super();
    }
}
