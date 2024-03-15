package ryzend.utils.formatter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatterImpl implements DateFormatter {


    private final String pattern;
    private final String separator;

    public DateFormatterImpl(String pattern, String separator) {
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
    public String format(LocalDate date) throws IllegalArgumentException {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
}
