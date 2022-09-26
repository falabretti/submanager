package io.submanager.util;

import io.submanager.model.Periodicity;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public class DateUtils {

    public static LocalDate nowFromPeriodicity(Periodicity periodicity) {

        if (periodicity.equals(Periodicity.MONTHLY)) {
            return YearMonth.now().atDay(1);
        } else if (periodicity.equals(Periodicity.YEARLY)) {
            return Year.now().atMonth(Month.JANUARY).atDay(1);
        } else {
            return LocalDate.now();
        }
    }
}
