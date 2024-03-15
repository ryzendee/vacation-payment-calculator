package ryzend.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ryzend.exception.custom.DateUrlFacadeException;
import ryzend.facade.DateUrlFacade;
import ryzend.utils.formatter.DateFormatter;
import ryzend.utils.generator.UrlGenerator;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DateUrlFacadeImpl implements DateUrlFacade {

    private final DateFormatter dateFormatter;
    private final UrlGenerator uriGenerator;


    @Override
    public String formatAndGenerateUrl(LocalDate from, LocalDate to) {
        try {
            log.info("Started generating url");
            String period = dateFormatter.formatPeriod(from, to);
            return uriGenerator.generateWithReplacement(period);
        } catch (IllegalArgumentException ex) {
            log.info("Exception while generating url: {}", ex.getMessage());
            throw new DateUrlFacadeException("Error while generating url: " + ex.getMessage());
        }
    }
}
