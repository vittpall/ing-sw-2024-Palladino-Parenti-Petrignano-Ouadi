package it.polimi.ingsw.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.GameCard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameCardLoader {
    public GameCardLoader() {
    }

    public List<GameCard> loadGameCards() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        module.addDeserializer(Corner.class, new CornerDeserializer());
        mapper.registerModule(module);
        List<GameCard> cards = new ArrayList<>();
        try {
            //load the game data
            cards = mapper.readValue(new File("src/main/resources/gameCard.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Qui puoi anche gestire l'eccezione in modo più sofisticato o lanciare una runtime exception personalizzata
        }
        return cards;
    }
}
