package no.mesan.faghelg.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import org.joda.time.DateTime;

import java.io.IOException;

public class CustomDateTimeDeserializer extends StdScalarDeserializer<DateTime> {

    public CustomDateTimeDeserializer() {
        super(DateTime.class);
    }

    @Override
    public DateTime deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        String dateTimeAsString = jsonParser.getText().trim();
        return new DateTime(dateTimeAsString);
    }
}
