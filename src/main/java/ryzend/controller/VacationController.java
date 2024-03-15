package ryzend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ryzend.dto.request.VacationRequest;
import ryzend.dto.response.VacationResponse;
import ryzend.service.VacationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    @GetMapping("/calculate")
    public VacationResponse calculateVacationPay(@Valid @RequestBody VacationRequest vacationRequest) {
        return vacationService.calculateVacationPay(vacationRequest);
    }
}
