package it.polimi.ingsw.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.BoardCoordinate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoardConfig {
    private static final Logger LOGGER = Logger.getLogger(GameCardLoader.class.getName());

    public static List<BoardCoordinate> loadCoordinates() {
        ObjectMapper mapper = new ObjectMapper();
        List<BoardCoordinate> coordinates = new ArrayList<>();
        try {
            coordinates = mapper.readValue(new File("src/main/resources/Json/boardCoordinates.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading game cards", e);
        }
        return coordinates;
    }
}
