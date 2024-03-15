package ryzend.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ryzend.exception.custom.ApiClientFacadeException;
import ryzend.facade.ApiClientFacade;
import ryzend.utils.client.ApiClient;
import ryzend.utils.parser.WorkDayParser;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiClientFacadeImpl implements ApiClientFacade {

    private final ApiClient apiClient;
    private final WorkDayParser workDayParser;

    @Override
    public int sendRequestAndExtract(String url) {
        try {
            log.info("Started generating request");
            String response = apiClient.sendRequest(url);
            return workDayParser.extractWorkDays(response);
        } catch (IllegalArgumentException ex) {
            log.error("Error while sending request: " + ex.getMessage());
            throw new ApiClientFacadeException("Error while sending request: " + ex.getMessage());
        }
    }
}
