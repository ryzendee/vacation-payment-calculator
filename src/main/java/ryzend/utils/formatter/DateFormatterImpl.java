package ryzend.utils.formatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatterImpl implements DateFormatter {


    private final String pattern;
    private final String separator;

    public DateFormatterImpl(@Value("${format.date.pattern}") String pattern,
                             @Value("${format.date.separator}") String separator) {
        if (pattern.isBlank() || separator.isBlank()) {
            throw new IllegalArgumentException("Pattern and separator must not be blank.");
        }
        this.pattern = pattern;
        this.separator = separator;
    }

    @Override
    public String formatPeriod(LocalDate from, LocalDate to) throws IllegalArgumentException {
        return format(from) + separator + format(to);
    }
    @Override
    public String format(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
}
