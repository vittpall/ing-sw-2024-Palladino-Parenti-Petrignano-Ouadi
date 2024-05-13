package it.polimi.ingsw.network.socket.Client;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ReturnableObject<T> implements Serializable {
    private HashMap<Integer, Game> games;
    private boolean booleanResponse;
    private String stringResponse;
    private int intResponse;
    private ArrayList<T> arrayListResponse;
    private HashSet<T> hashSetResponse;
    private ObjectiveCard objectiveCardResponse;
    private ObjectiveCard[] objectiveCardsResponse;
    private StarterCard starterCardResponse;
    private HashMap<Point, GameCard> gameCardHashMapResponse;
    //set -1 for the void response

    public HashMap<Point, GameCard> getGameCardHashMapResponse() {
        return gameCardHashMapResponse;
    }

    public void setGameCardHashMapResponse(HashMap<Point, GameCard> gameCardHashMapResponse) {
        this.gameCardHashMapResponse = gameCardHashMapResponse;
    }

    public StarterCard getStarterCardResponse() {
        return starterCardResponse;
    }

    public void setStarterCardResponse(StarterCard starterCardResponse) {
        this.starterCardResponse = starterCardResponse;
    }

    public ObjectiveCard[] getObjectiveCardsResponse() {
        return objectiveCardsResponse;
    }

    public void setObjectiveCardsResponse(ObjectiveCard[] objectiveCardsResponse) {
        this.objectiveCardsResponse = objectiveCardsResponse;
    }

    public void setObjectiveCardResponse(ObjectiveCard objectiveCardResponse) {
        this.objectiveCardResponse = objectiveCardResponse;
    }

    public ObjectiveCard getObjectiveCardResponse() {
        return objectiveCardResponse;
    }

    public void setGames(HashMap<Integer, Game> games) {
        this.games = games;
    }

    public HashMap<Integer, Game> getGames() {
        return games;
    }

    public void setBooleanResponse(boolean booleanResponse) {
        this.booleanResponse = booleanResponse;
    }

    public boolean getBooleanResponse() {
        return booleanResponse;
    }

    public void setStringResponse(String stringResponse) {
        this.stringResponse = stringResponse;
    }

    public String getStringResponse() {
        return this.stringResponse;
    }

    public int getIntResponse() {
        return intResponse;
    }

    public void setIntResponse(int intResponse) {
        this.intResponse = intResponse;
    }

    public void setArrayListResponse(ArrayList<T> arrayListResponse) {
        this.arrayListResponse = arrayListResponse;
    }

    public ArrayList<T> getArrayListResponse() {
        return arrayListResponse;
    }

    public void setHashSetResponse(HashSet<T> hashSetResponse) {
        this.hashSetResponse = hashSetResponse;
    }

    public HashSet<T> getHashSetResponse() {
        return hashSetResponse;
    }
}

