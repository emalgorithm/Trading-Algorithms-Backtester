
import portfolio.Equity;
import portfolio.NotEnoughDataException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by ema on 21/02/16.
 */
public class Main {
    public static void main(String[] args) throws NotEnoughDataException {
        //Calendar calendar = new GregorianCalendar(2016, 02, 22);
        Equity facebook = new Equity("Facebook", "FB");

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2015-02-20", dateFormat);;
        System.out.println(facebook.getMovingAverage(date, 20));

    }

}
