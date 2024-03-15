package ryzend.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ryzend.dto.request.VacationRequest;
import ryzend.dto.response.VacationResponse;
import ryzend.exception.custom.ApiClientFacadeException;
import ryzend.exception.custom.DateUrlFacadeException;
import ryzend.exception.custom.VacationCalculateException;
import ryzend.facade.ApiClientFacade;
import ryzend.facade.DateUrlFacade;
import ryzend.service.VacationServiceImpl;
import ryzend.utils.calculator.VacationPayCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VacationServiceTest {

    private static final String EX_MESSAGE = "test-exception-message";
    @InjectMocks
    private VacationServiceImpl vacationService;
    @Mock
    private VacationRequest vacationRequest;
    @Mock
    private VacationPayCalculator vacPayCalculator;
    @Mock
    private DateUrlFacade dateUrlFacade;
    @Mock
    private ApiClientFacade apiClientFacade;

    @Test
    void calculateVacationPay_validRequest_returnsVacResponse() {
        double expectedVacationPayment = 100;

        String url = "dummy-url";
        int workDays = 1;

        when(dateUrlFacade.formatAndGenerateUrl(vacationRequest.getFrom(), vacationRequest.getTo()))
                .thenReturn(url);
        when(apiClientFacade.sendRequestAndExtract(url))
                .thenReturn(workDays);
        when(vacPayCalculator.calculateVacPay(vacationRequest.getAvgSalary(), workDays))
                .thenReturn(expectedVacationPayment);

        VacationResponse actual = vacationService.calculateVacationPay(vacationRequest);
        assertThat(actual.getVacationPayment()).isEqualTo(expectedVacationPayment);

        verify(dateUrlFacade).formatAndGenerateUrl(vacationRequest.getFrom(), vacationRequest.getTo());
        verify(apiClientFacade).sendRequestAndExtract(url);
        verify(vacPayCalculator).calculateVacPay(vacationRequest.getAvgSalary(), workDays);
    }

    @Test
    void calculateVacationPay_urlFacadeThrowEx_throwsVacationCalculateEx() {
        when(dateUrlFacade.formatAndGenerateUrl(vacationRequest.getFrom(), vacationRequest.getTo()))
                .thenThrow(new DateUrlFacadeException(EX_MESSAGE));

        assertThatThrownBy(() -> vacationService.calculateVacationPay(vacationRequest))
                .isInstanceOf(VacationCalculateException.class)
                .hasMessageContaining(EX_MESSAGE);

        verify(dateUrlFacade).formatAndGenerateUrl(vacationRequest.getFrom(), vacationRequest.getTo());
    }

    @Test
    void calculateVacationPay_apiFacadeThrowEx_throwsVacationCalculateEx() {
        String url = "dummy";

        when(dateUrlFacade.formatAndGenerateUrl(vacationRequest.getFrom(), vacationRequest.getTo()))
                .thenReturn(url);
        when(apiClientFacade.sendRequestAndExtract(url))
                .thenThrow(new ApiClientFacadeException(EX_MESSAGE));

        assertThatThrownBy(() -> vacationService.calculateVacationPay(vacationRequest))
                .isInstanceOf(VacationCalculateException.class)
                .hasMessageContaining(EX_MESSAGE);

        verify(dateUrlFacade).formatAndGenerateUrl(vacationRequest.getFrom(), vacationRequest.getTo());
        verify(apiClientFacade).sendRequestAndExtract(url);
    }
}
