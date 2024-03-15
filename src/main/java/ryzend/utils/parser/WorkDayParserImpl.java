package ryzend.utils.parser;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WorkDayParserImpl implements WorkDayParser {
    private final String jsonObject;
    private final String jsonKey;

    public WorkDayParserImpl(@Value("${parse.json-object}") String jsonObject,
                             @Value("${parse.json-key}") String jsonKey) {
        if (jsonObject.isBlank() || jsonKey.isBlank()) {
            throw new IllegalArgumentException("Json object and key must not me blank. " +
                    "Actual values: jsonObject=" + jsonObject + " jsonKey=" + jsonKey);
        }
        this.jsonObject = jsonObject;
        this.jsonKey = jsonKey;
    }

    @Override
    public int extractWorkDays(String json) throws JSONException {
        JSONObject parsedJson = new JSONObject(json);

        return parsedJson.getJSONObject(jsonObject)
                .getInt(jsonKey);
    }
}
