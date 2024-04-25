package it.polimi.ingsw.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.strategyPatternObjective.*;

import java.awt.*;
import java.io.IOException;
import java.util.EnumMap;

public class ObjectiveCardDeserializer extends StdDeserializer<ObjectiveCard> {

    protected ObjectiveCardDeserializer() {
        super(ObjectiveCard.class);
    }

    @Override
    public ObjectiveCard deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectiveStrategy strategy;
        String type = node.get("type").asText();
        int points = node.get("points").asInt();
        String frontImagePath = node.get("frontImagePath").asText();
        String backImagePath = node.get("backImagePath").asText();

        switch (type) {
            case "verticalStrategy":
                Resource primaryResource = Resource.valueOf(node.get("primaryResource").asText());
                Resource secondaryResource = Resource.valueOf(node.get("secondaryResource").asText());
                Point secondaryOffset = new Point(node.get("secondaryOffset").get(0).asInt(), node.get("secondaryOffset").get(1).asInt());
                strategy = new VerticalPatternStrategy(primaryResource, secondaryResource, points, secondaryOffset);
                break;
            case "diagonalStrategy":
                Resource diagonalResource = Resource.valueOf(node.get("resource").asText());
                Point diagonalOffset = new Point(node.get("diagonalOffset").get(0).asInt(), node.get("diagonalOffset").get(1).asInt());
                strategy = new DiagonalPatternStrategy(diagonalResource, points, diagonalOffset);
                break;
            case "resourceStrategy":
                Resource resource = Resource.valueOf(node.get("resource").asText());
                int number = node.get("number").asInt();
                strategy = new ResourceStrategy(resource, number);
                break;
            case "objectStrategy":
                EnumMap<CornerObject, Integer> objects = new EnumMap<>(CornerObject.class);
                JsonNode objectsNode = node.get("objects");
                if (objectsNode != null) {
                    for (CornerObject key : CornerObject.values()) {
                        String keyName = key.name();
                        if (objectsNode.has(keyName)) {
                            objects.put(key, objectsNode.get(keyName).asInt());
                        }
                    }
                }
                strategy = new ObjectStrategy( objects);
                break;
            default:
                throw new IllegalStateException("Unrecognized strategy type: " + type);
        }

        return new ObjectiveCard(strategy, points, backImagePath, frontImagePath);
    }
}