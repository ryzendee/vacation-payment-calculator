package ryzend.unit.facade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import ryzend.exception.custom.ApiClientFacadeException;
import ryzend.facade.impl.ApiClientFacadeImpl;
import ryzend.utils.client.ApiClient;
import ryzend.utils.parser.WorkDayParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiClientImplFacadeTest {

    private static final String URL = "test-url";
    private static final String EX_MESSAGE = "test-message";
    private static final String JSON = "dummy";

    @InjectMocks
    private ApiClientFacadeImpl apiClientFacade;

    @Mock
    private ApiClient apiClient;
    @Mock
    private WorkDayParser workDayParser;

    @Test
    void sendRequestAndExtract_validData_returnsWorkDays() {
        int expectedWorkDays = 1;

        when(apiClient.sendRequest(URL))
                .thenReturn(JSON);
        when(workDayParser.extractWorkDays(JSON))
                .thenReturn(expectedWorkDays);

        int actualWorkDays = apiClientFacade.sendRequestAndExtract(URL);
        assertThat(actualWorkDays).isEqualTo(expectedWorkDays);

        verify(apiClient).sendRequest(URL);
        verify(workDayParser).extractWorkDays(JSON);
    }
    @Test
    void sendRequestAndExtract_apiClientThrowEx_throwsApiClientFacadeEx() {
        when(apiClient.sendRequest(URL))
                .thenThrow(new RestClientException(EX_MESSAGE));

        assertThatThrownBy(() -> apiClientFacade.sendRequestAndExtract(URL))
                .isInstanceOf(ApiClientFacadeException.class)
                .hasMessageContaining(EX_MESSAGE);

        verify(apiClient).sendRequest(URL);
    }

    @Test
    void sendRequestAndExtract_workDayParserThrowEx_throwsApiClientFacadeEx() {
        when(apiClient.sendRequest(URL))
                .thenReturn(JSON);
        when(workDayParser.extractWorkDays(JSON))
                .thenThrow(new IllegalArgumentException(EX_MESSAGE));

        assertThatThrownBy(() -> apiClientFacade.sendRequestAndExtract(URL))
                .isInstanceOf(ApiClientFacadeException.class)
                .hasMessageContaining(EX_MESSAGE);

        verify(apiClient).sendRequest(URL);
        verify(workDayParser).extractWorkDays(JSON);
    }
}
