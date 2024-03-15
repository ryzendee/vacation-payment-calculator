package ryzend.unit.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ryzend.utils.calculator.VacationPayCalculator;
import ryzend.utils.calculator.VacationPayCalculatorImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class VacationCalculatormplTest {

    private VacationPayCalculator vacationPayCalculator;

    @BeforeEach
    void setUp() {
        vacationPayCalculator = new VacationPayCalculatorImpl();
    }

    @Test
    void calculateVacPay_validData_returnsExpectedPayment() {
        double avgSalary = 200;
        int vacationDays = 10;
        double expectedPayment = avgSalary * vacationDays;

        double actualPayment = vacationPayCalculator.calculateVacPay(avgSalary, vacationDays);

        assertThat(actualPayment).isEqualTo(expectedPayment);
    }
}
