package ryzend.utils.formatter;

import java.time.LocalDate;

public interface DateFormatter {
    String format(LocalDate date);
    String formatPeriod(LocalDate from, LocalDate to);

}
