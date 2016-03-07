package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by ema on 24/02/16.
 */
public final class Util {
    public static final Double DELTA = 1e-9;


    private Util() {}


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


    public static int compare(Double first, Double second) {
        if (first + DELTA < second) {
            return -1;
        }

        if (second + DELTA < first) {
            return 1;
        }

        return 0;
    }

}
