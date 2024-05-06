package it.polimi.ingsw.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;

import java.io.IOException;

public class CornerDeserializer extends JsonDeserializer<Corner[]> {
    @Override
    public Corner[] deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode node = mapper.readTree(jp);
        ArrayNode cornerNodes = (ArrayNode) node;

        Corner[] corners = new Corner[8]; // Assuming you need 8 corners for each card

        for (int i = 0; i < cornerNodes.size(); i++) {
            String value = cornerNodes.get(i).asText();
            if ("hidden".equalsIgnoreCase(value)) {
                corners[i] = new Corner(true);
            } else if ("free".equalsIgnoreCase(value)) {
                corners[i] = new Corner(false);
            } else {
                try {
                    Resource resource = Resource.valueOf(value.toUpperCase());
                    corners[i] = new Corner(resource);
                } catch (IllegalArgumentException e) {
                    try {
                        CornerObject object = CornerObject.valueOf(value.toUpperCase());
                        corners[i] = new Corner(object);
                    } catch (IllegalArgumentException ex) {
                        throw new RuntimeException("Unknown corner value: " + value);
                    }
                }
            }
        }

        // Fill the remaining corners with default values
        for (int i = cornerNodes.size(); i < corners.length; i++) {
            corners[i] = new Corner(false); // Assuming default value is false
        }

        return corners;
    }
}

