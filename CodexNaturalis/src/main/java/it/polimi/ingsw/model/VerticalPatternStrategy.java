package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public class VerticalPatternStrategy implements ObjectiveStrategy{
    private final ArrayList<Resource> resourceObjectiveCard;

    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires two vertical cards and another one over one corner.
     * @param desk
     */
    public void isSatisfied (PlayerDesk desk){

    }
}
