package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;


public class PlayerDesk {
    private HashMap<int[], GameCard> desk;
    private HashSet<int[]> availablePlaces;

    /**
     * costructor
     * it creates desk and availablePlaces and adds the array (0,0) to availablePlaces
     */
    public PlayerDesk() {
    }

    /**
     *
     * @return a copy of the attribute desk
     */
    public HashMap<int[], GameCard> getDesk(){
        return new HashMap<int[], GameCard>(desk);
    }

    /**
     *
     * @return the copy of the attribute availablePlaces
     */
    public HashSet<int[]> getAvailablePlaces(){
        return new HashSet<int[]>(availablePlaces);
    }

    /**
     * check if the user's desk has the requirements needed
     * @param numResourceNeeded represents the type of the resource required
     * @param resource represents how many resources are needed
     * @return true if the requirements are met; false is they are not
     */
    public boolean checkRequirements(int numResourceNeeded, Resource resource) {
    }

    /**
     * adds the couple <(x,y), card> into the desk
     * @param card
     * @param x
     * @param y
     * @return the points that the player gains after the move
     */
    public int addCard(GameCard card, Integer x, Integer y) {
    }

    /**
     * sets as hidden the adjacent cards' corners
     * @param card
     */
    public void coverCorner(GameCard card){
    }
}