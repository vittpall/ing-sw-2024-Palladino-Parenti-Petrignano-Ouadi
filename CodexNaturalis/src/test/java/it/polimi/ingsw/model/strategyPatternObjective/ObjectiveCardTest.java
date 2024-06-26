package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectiveCardTest {
    ObjectiveCard objectiveCard;

    @BeforeEach
    void setUp() {
        objectiveCard = new ObjectiveCard(new VerticalPatternStrategy(null, null, null), 5, "back", "front");
    }

    @Test
    void verifyObjective() {
        assertEquals(0, objectiveCard.verifyObjective(new PlayerDesk()));
    }

    @Test
    void getStrategy() {
        assertEquals(VerticalPatternStrategy.class, objectiveCard.getStrategy().getClass());
    }

    @Test
    void copyConstructor_createsCorrectCopy() {
        ObjectiveCard copy = new ObjectiveCard(objectiveCard);
        assertEquals(objectiveCard.getStrategy(), copy.getStrategy());
        assertEquals(objectiveCard.getPoints(), copy.getPoints());
        assertEquals(objectiveCard.getImageFrontPath(), copy.getImageFrontPath());
        assertEquals(objectiveCard.getImageBackPath(), copy.getImageBackPath());
    }
}