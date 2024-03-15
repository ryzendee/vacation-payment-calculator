package ryzend.utils.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ApiClient implements ApiRequestSender {

    private final RestTemplate restTemplate;

    public String sendRequest(String uri) throws RestClientException {
        return restTemplate.getForObject(uri, String.class);
    }
}
