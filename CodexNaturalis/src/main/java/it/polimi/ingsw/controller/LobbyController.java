package it.polimi.ingsw.Controller;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LobbyController {

    private final Lobby model;
    private final Set<String> usernames = new HashSet<>();


    public LobbyController() {
        model = new Lobby();
    }

    public synchronized boolean checkUsername(String username) {
        return usernames.add(username);
    }

    public HashMap<Integer, Game> getVisibleGames() {
        HashMap<Integer, Game> allGames = model.listAllGames();
        HashMap<Integer, Game> visibleGames = new HashMap<>();
        for (int id : allGames.keySet()) {
            if (allGames.get(id).getPlayers().size() < allGames.get(id).getnPlayer()) {
                visibleGames.put(id, allGames.get(id));
            }
        }
        return visibleGames;
    }

    public synchronized int joinGame(int id, String username) throws InterruptedException {
        Game game = model.getGame(id);
        ArrayList<TokenColor> usedColors = new ArrayList<>();
        TokenColor chosenColor = TokenColor.BLUE;
        for (Player player : game.getPlayers()) {
            usedColors.add(player.getTokenColor());
        }
        for (TokenColor color : TokenColor.values()) {
            if (!usedColors.contains(color)) {
                chosenColor = color;
                break;
            }
        }
        Player player = new Player(chosenColor, username);
        int nPlayer = model.joinGame(id, player);
        if (model.getGame(id).getPlayers().size() < model.getGame(id).getnPlayer()) {
            while (model.getGame(id).getPlayers().size() < model.getGame(id).getnPlayer()) wait();
        } else {
            this.notifyAll();
            model.getGame(id).setUpGame();
        }
        return nPlayer;
    }

    public int createGame(String username, int nPlayers) throws InterruptedException {
        int newGameId = model.createNewGame(nPlayers);
        int nPlayer = this.joinGame(newGameId, username);
        if (nPlayer == 0) {
            return newGameId;
        }
        return -1;
    }

    public ArrayList<ObjectiveCard> getObjectiveCards(int idGame, int idPlayer) {
        return model.getGame(idGame).getPlayers().get(idPlayer).getDrawnObjectiveCards();
    }

    public void setObjectiveCard(int idGame, int idClientIntoGame, ObjectiveCard objCard) throws CardNotFoundException {
        model.getGame(idGame).getPlayers().get(idClientIntoGame).setObjectiveCard(objCard);
    }
}
