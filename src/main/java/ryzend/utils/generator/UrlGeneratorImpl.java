package ryzend.utils.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlGeneratorImpl implements UrlGenerator {
    private final String baseUrl;
    private final String toReplace;

    public UrlGeneratorImpl(@Value("${calendar-api.base-url}") String baseUrl,
                        @Value("${calendar-api.to-replace}") String toReplace) {

        if (baseUrl.isBlank() || toReplace.isBlank()) {
            throw new IllegalArgumentException("Base URL and toReplace must not be null or blank. "
                    + "Actual values: baseUri=" + baseUrl + ", toReplace=" + toReplace);
        }

        if (!baseUrl.contains(toReplace)) {
            throw new IllegalArgumentException("Base URL must contains value to replace. "
                    + "Actual values: baseUri=" + baseUrl + ", toReplace=" + toReplace);
        }

        this.baseUrl = baseUrl;
        this.toReplace = toReplace;
    }

    @Override
    public String generateWithReplacement(String replacement) {
        if (replacement == null || replacement.isBlank()) {
            throw new IllegalArgumentException("Replacement must not be blank or empty. Actual value: " + replacement);
        }
        return baseUrl.replace(toReplace, replacement);
    }
}
