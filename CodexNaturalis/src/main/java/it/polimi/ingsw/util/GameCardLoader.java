package it.polimi.ingsw.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.GameCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for loading the game cards from a JSON file using the Jackson library.
 * The CornerDeserializer class is used to deserialize the corner attribute of the game card.
 * The game cards are returned into a list of GameCard objects.
 * If an error occurs while loading the game cards, a message will be printed.
 */
public class GameCardLoader {
    private static final Logger LOGGER = Logger.getLogger(GameCardLoader.class.getName());

    public GameCardLoader() {
    }

    /**
     * Load the game cards from the json file
     * The json file is located in the resources folder and is parsed using the Jackson library
     * The CornerDeserializer class is used to deserialize the corner attribute of the game card
     * The game cards are returned into a list of GameCard objects
     * If an error occurs while loading the game cards, a log message will be printed
     *
     * @return the list of game cards
     */
    public List<GameCard> loadGameCards() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        module.addDeserializer(Corner[].class, new CornerDeserializer());
        mapper.registerModule(module);
        List<GameCard> cards = new ArrayList<>();
        try {
            cards = mapper.readValue(GameCardLoader.class.getResource("/Json/gameCard.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading game cards", e);
        }
        return cards;
    }
}
