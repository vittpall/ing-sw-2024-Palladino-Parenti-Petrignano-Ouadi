package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveStrategyTest {
    ObjectiveStrategy objectiveStrategy;

    @BeforeEach
    void setUp() {
        objectiveStrategy = new VerticalPatternStrategy(null, null, null);
    }

    @Test
    void isSatisfied() {
        assertEquals(0, objectiveStrategy.isSatisfied(new PlayerDesk()));
    }
}