package ryzend.unit.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ryzend.utils.formatter.DateFormatter;
import ryzend.utils.formatter.DateFormatterImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DateFormatterImplTest {
    private DateFormatter dateFormatter;

    @Test
    void formatPeriod_validDates_returns() {
        String pattern = "ddMMyyyy";
        String separator = "-";
        LocalDate date = LocalDate.now();
        String expected =
                date.format(DateTimeFormatter.ofPattern(pattern))
                        + separator
                        + date.format(DateTimeFormatter.ofPattern(pattern));

        dateFormatter = new DateFormatterImpl(pattern, separator);

        String actual = dateFormatter.formatPeriod(date, date);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void format_nullableDate_throwIllegalArgEx() {
        dateFormatter = new DateFormatterImpl("test", "test");

        assertThatThrownBy(() -> dateFormatter.format(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void format_incorrectPattern_thrownInvalidArgException() {
        dateFormatter = new DateFormatterImpl("test", "test");

        assertThatThrownBy(() -> dateFormatter.format(LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @MethodSource("getInvalidArgsForFormatter")
    @ParameterizedTest
    void constructor_incorrectArgs_throwIllegalArgEx(String pattern, String separator) {
        assertThatThrownBy(() -> new DateFormatterImpl(pattern, separator));
    }

    static Stream<Arguments> getInvalidArgsForFormatter() {
        return Stream.of(
                //Scenario 1: blank pattern
                Arguments.of("  ", "test-separator"),
                //Scenario 2: blank separator
                Arguments.of("test-pattern", "   ")
        );
    }

}
