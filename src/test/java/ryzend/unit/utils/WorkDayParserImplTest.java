package ryzend.unit.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ryzend.utils.parser.WorkDayParser;
import ryzend.utils.parser.WorkDayParserImpl;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

public class WorkDayParserImplTest {

    private WorkDayParser workDayParser;


    @MethodSource("getInvalidArgsForParser")
    @ParameterizedTest
    void constructor_invalidArgs_throwIllegalArgEx(String jsonObject, String jsonKey) {
        assertThatThrownBy(() -> new WorkDayParserImpl(jsonObject, jsonKey))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> getInvalidArgsForParser() {
        return Stream.of(
                //Scenario 1:blank jsonObject
                Arguments.of("    ", "test"),
                //Scenario 2: blank jsonKey
                Arguments.of("test", "      ")
        );
    }


    //TODO
    @Test
    void extractWorkDays_returnsExpectedDays() {
        fail("to do");
    }
}
