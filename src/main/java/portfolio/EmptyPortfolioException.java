package portfolio;

/**
 * Created by ema on 25/02/16.
 */
public class EmptyPortfolioException extends RuntimeException {

    public EmptyPortfolioException(String message) {
        super(message);
    }

    public EmptyPortfolioException() {
        super();
    }

}
