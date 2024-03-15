package ryzend.facade;

import java.time.LocalDate;

public interface DateUrlFacade {
    String formatAndGenerateUrl(LocalDate from, LocalDate to);
}
