package it.polimi.ingsw.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for loading the objective cards from a JSON file using the Jackson library.
 * If an error occurs while loading the objective cards, a message will be printed.
 */
public class ObjectiveCardLoader {
    private static final Logger LOGGER = Logger.getLogger(ObjectiveCardLoader.class.getName());


    /**
     * Load the objective cards from the json file
     * The json file is located in the resources folder and is parsed using the Jackson library
     * The ObjectiveCardDeserializer class is used to deserialize the objective card
     * The objective cards are returned into a list of ObjectiveCard objects
     * If an error occurs while loading the objective cards, a log message will be printed
     *
     * @return the list of objective cards
     */
    public List<ObjectiveCard> loadObjectiveCards() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ObjectiveCard.class, new ObjectiveCardDeserializer());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(module);
        List<ObjectiveCard> cards = new ArrayList<>();
        try {
            cards = mapper.readValue(ObjectiveCardLoader.class.getResource("/Json/objectiveCard.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading objective cards", e);
        }
        return cards;
    }
}
