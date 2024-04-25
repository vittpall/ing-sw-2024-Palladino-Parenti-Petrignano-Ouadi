package it.polimi.ingsw.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;

import java.io.IOException;

public class CornerDeserializer extends JsonDeserializer<Corner> {
    @Override
    public Corner deserialize(JsonParser jp, DeserializationContext context) throws IOException{
        String value = jp.getValueAsString();
        if ("hidden".equalsIgnoreCase(value)) {
            return new Corner(true);
        } else if ("free".equalsIgnoreCase(value)) {
            return new Corner(false);
        } else {
            try {
                Resource resource = Resource.valueOf(value.toUpperCase());
                return new Corner(resource);
            } catch (IllegalArgumentException e) {
                try {
                    CornerObject object = CornerObject.valueOf(value.toUpperCase());
                    return new Corner(object);
                } catch (IllegalArgumentException ex) {
                    throw new RuntimeException("Unknown corner value: " + value);
                }
            }
        }
    }
}
