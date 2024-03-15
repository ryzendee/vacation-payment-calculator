package ryzend.unit.facade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ryzend.exception.custom.DateUrlFacadeException;
import ryzend.facade.impl.DateUrlFacadeImpl;
import ryzend.utils.formatter.DateFormatter;
import ryzend.utils.generator.UrlGenerator;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DateUrlFacadeTest {

    private static final LocalDate DATE_FROM = LocalDate.now();
    private static final LocalDate DATE_TO = LocalDate.now();
    private static final String PERIOD = "dummy";
    private static final String EX_MESSAGE = "test-message";

    @InjectMocks
    private DateUrlFacadeImpl dateUrlFacade;
    @Mock
    private DateFormatter dateFormatter;
    @Mock
    private UrlGenerator uriGenerator;

    @Test
    void formatAndGenerateUrl_validData_returnsUrl() {
        String expectedUrl = "test-url";

        when(dateFormatter.formatPeriod(DATE_FROM, DATE_TO))
                .thenReturn(PERIOD);
        when(uriGenerator.generateWithReplacement(PERIOD))
                .thenReturn(expectedUrl);

        String actualUrl = dateUrlFacade.formatAndGenerateUrl(DATE_FROM, DATE_TO);
        assertThat(actualUrl).isEqualTo(expectedUrl);

        verify(dateFormatter).formatPeriod(DATE_FROM, DATE_TO);
        verify(uriGenerator).generateWithReplacement(PERIOD);
    }

    @Test
    void formatAndGenerateUrl_urlGeneratorThrowEx_throwDateUrlFacadeEx() {
        when(dateFormatter.formatPeriod(DATE_FROM, DATE_TO))
                .thenReturn(PERIOD);
        when(uriGenerator.generateWithReplacement(PERIOD))
                .thenThrow(new IllegalArgumentException(EX_MESSAGE));

        assertThatThrownBy(() -> dateUrlFacade.formatAndGenerateUrl(DATE_FROM, DATE_TO))
                .isInstanceOf(DateUrlFacadeException.class)
                .hasMessageContaining(EX_MESSAGE);

        verify(dateFormatter).formatPeriod(DATE_FROM, DATE_TO);
        verify(uriGenerator).generateWithReplacement(PERIOD);
    }

    @Test
    void formatAndGenerateUrl_dateFormatterThrowEx_throwDateUrlFacadeEx() {
        when(dateFormatter.formatPeriod(DATE_FROM, DATE_TO))
                .thenThrow(new IllegalArgumentException(EX_MESSAGE));

        assertThatThrownBy(() -> dateUrlFacade.formatAndGenerateUrl(DATE_FROM, DATE_TO))
                .isInstanceOf(DateUrlFacadeException.class)
                .hasMessageContaining(EX_MESSAGE);

        verify(dateFormatter).formatPeriod(DATE_FROM, DATE_TO);
    }
}
