package ryzend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ryzend.exception.custom.VacationCalculateException;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class ControllerExHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationEx(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors().forEach(
                        error -> errors.put(error.getField(), error.getDefaultMessage())
                );

        return new ErrorResponse("Validation failed.", errors);
    }

    @ExceptionHandler(VacationCalculateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleVacationCalculateEx(VacationCalculateException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        log.error("Unexpected exception: {}", ex.getMessage(), ex);
        return new ErrorResponse("Unexpected exception " + ex.getMessage());
    }
}
