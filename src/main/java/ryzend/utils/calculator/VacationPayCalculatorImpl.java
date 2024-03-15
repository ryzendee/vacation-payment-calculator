package ryzend.utils.calculator;

import org.springframework.stereotype.Component;

@Component
public class VacationPayCalculatorImpl implements VacationPayCalculator {

    @Override
    public double calculateVacPay(double avgSalary, int vacationDays) {
        return avgSalary * vacationDays;
    }
}
