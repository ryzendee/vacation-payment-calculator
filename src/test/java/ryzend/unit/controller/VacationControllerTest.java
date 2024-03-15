package ryzend.unit.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ryzend.controller.VacationController;
import ryzend.dto.request.VacationRequest;
import ryzend.dto.response.VacationResponse;
import ryzend.service.VacationService;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@WebMvcTest(VacationController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class VacationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacationService vacationService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void calculateVacationPay_validDto_statusOk() {
        double avgSalary = 850;
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(30);

        VacationRequest validDto = VacationRequest.builder()
                .avgSalary(avgSalary)
                .from(from)
                .to(to)
                .build();

        VacationResponse expectedResponse = VacationResponse.builder()
                .vacationPayment(avgSalary)
                .build();

        when(vacationService.calculateVacationPay(validDto))
                .thenReturn(expectedResponse);

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(validDto)
                .when()
                    .get("/calculate")
                .then()
                    .status(HttpStatus.OK);

        verify(vacationService).calculateVacationPay(validDto);
    }

    @MethodSource("getInvalidArgsForRequest")
    @ParameterizedTest
    void calculateVacationPay_invalidDto_statusBadRequest(double avgSalary, LocalDate from, LocalDate to) {
        VacationRequest invalidDto = VacationRequest.builder()
                .avgSalary(avgSalary)
                .from(from)
                .to(to)
                .build();

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(invalidDto)
                    .when()
                .get("/calculate")
                .then()
                    .status(HttpStatus.BAD_REQUEST);

        verify(vacationService, never()).calculateVacationPay(invalidDto);
    }
    
    static Stream<Arguments> getInvalidArgsForRequest() {
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(30);

        return Stream.of(
                //Scenario 1: double less than one
                Arguments.of(-1, from, to),

                //Scenario 2: nullable dates
                Arguments.of(100, null, null),

                //Scenario 3: from date before to date
                Arguments.of(100, to, from)
        );
    }
}
