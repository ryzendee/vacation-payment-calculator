package ryzend.unit.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import ryzend.utils.generator.UrlGenerator;
import ryzend.utils.generator.UrlGeneratorImpl;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UrlGeneratorImplTest {

    private UrlGenerator urlGenerator;

    @MethodSource("getInvalidArgsForGenerator")
    @ParameterizedTest
    void constructor_invalidArgs_throwIllegalArgEx(String baseUrl, String toReplace) {
        assertThatThrownBy(() -> new UrlGeneratorImpl(baseUrl, toReplace))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_validArgs_createSuccessfully() {
        urlGenerator = new UrlGeneratorImpl("baseUrl-toReplace", "toReplace");
    }

    @NullSource
    @EmptySource
    @ParameterizedTest
    void generateWithReplacement_invalidReplacementArg_throwIllegalArgEx(String replacement) {
        urlGenerator = new UrlGeneratorImpl("baseUrl-toReplace", "toReplace");
        assertThatThrownBy(() -> urlGenerator.generateWithReplacement(replacement))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void generateWithReplacement_validReplacementArg_returnExpectedUrl() {
        String baseUrl = "baseUrl-toReplace";
        String toReplace = "toReplace";
        String replacement = "test";
        String expectedUrl = baseUrl.replace(toReplace, replacement);

        urlGenerator = new UrlGeneratorImpl(baseUrl, toReplace);
        String actualUrl = urlGenerator.generateWithReplacement(replacement);

        assertThat(actualUrl).isEqualTo(expectedUrl);
    }
    static Stream<Arguments> getInvalidArgsForGenerator() {
        return Stream.of(
                //Scenario 1: blank baseUrl
                Arguments.of("     ", "test"),
                //Scenario 2: blank toReplace
                Arguments.of("test", "    "),
                //Scenario 3: baseUrl doesn't contains toReplace
                Arguments.of("baseUrl", "toReplace")
        );
    }

}
