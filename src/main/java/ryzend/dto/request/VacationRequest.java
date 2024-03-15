package ryzend.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class VacationRequest {

    @Min(value = 1, message = "avgSalary must be greater than zero")
    private double avgSalary;

    @NotNull(message = "from date must not be null")
    private LocalDate from;

    @NotNull(message = "to date must not be null")
    private LocalDate to;

    @AssertTrue(message = "from date must be before to date")
    public boolean isFromDateBeforeToDate() {
        if (from == null || to == null) {
            return false;
        }
        return from.isBefore(to);
    }
}
