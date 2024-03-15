package ryzend.unit.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import ryzend.utils.client.ApiClientImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiClientImplTest {

    @InjectMocks
    private ApiClientImpl apiClient;
    @Mock
    private RestTemplate restTemplate;

    @Test
    void sendRequest_validUrl_returnExceptedResponse() {
        String expectedResponse = "test-response";
        String url = "test-url";

        when(restTemplate.getForObject(url, String.class))
                .thenReturn(expectedResponse);

        String actualResponse = apiClient.sendRequest(url);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(restTemplate).getForObject(url, String.class);
    }
}
