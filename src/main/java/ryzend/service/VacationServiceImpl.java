package ryzend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ryzend.dto.request.VacationRequest;
import ryzend.dto.response.VacationResponse;
import ryzend.exception.custom.ApiClientFacadeException;
import ryzend.exception.custom.DateUrlFacadeException;
import ryzend.exception.custom.VacationCalculateException;
import ryzend.facade.ApiClientFacade;
import ryzend.facade.DateUrlFacade;
import ryzend.utils.calculator.VacationPayCalculator;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {

    private final VacationPayCalculator vacPayCalculator;
    private final DateUrlFacade dateUrlFacade;
    private final ApiClientFacade apiClientFacade;

    @Override
    public VacationResponse calculateVacationPay(VacationRequest request)  {
        try {
            String url = dateUrlFacade.formatAndGenerateUrl(request.getFrom(), request.getTo());
            int workDays = apiClientFacade.sendRequestAndExtract(url);
            double vacationPayment = vacPayCalculator.calculateVacPay(request.getAvgSalary(), workDays);

            return VacationResponse.builder()
                    .vacationPayment(vacationPayment)
                    .build();

        } catch (DateUrlFacadeException | ApiClientFacadeException ex) {
            log.error("Exception while using facades: {}", ex.getMessage());
            throw new VacationCalculateException("Error while calculating vacation payment: " + ex.getMessage());
        }
    }
}
