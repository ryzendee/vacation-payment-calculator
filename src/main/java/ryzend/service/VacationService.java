package ryzend.service;

import ryzend.dto.request.VacationRequest;
import ryzend.dto.response.VacationResponse;

public interface VacationService {

    VacationResponse calculateVacationPay(VacationRequest vacationRequest);
}
