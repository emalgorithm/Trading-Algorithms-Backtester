package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by ema on 24/02/16.
 */
public class Util {

    public static LocalDate computeDate(String dateString) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;

        try {
            date = LocalDate.parse(dateString, dateFormat);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            date  = LocalDate.now();
        }

        return date;
    }

}
