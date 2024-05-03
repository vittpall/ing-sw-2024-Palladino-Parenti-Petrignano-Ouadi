package it.polimi.ingsw.Controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.TokenColor;

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
            if (allGames.get(id).getPlayers().size() <= allGames.get(id).getnPlayer()) {
                visibleGames.put(id, allGames.get(id));
            }
        }
        return visibleGames;
    }

    public void joinGame(int id, String username) {
        Game game = model.getGame(id);
        ArrayList<TokenColor> usedColors = new ArrayList<>();
        TokenColor chosenColor = null;
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
        model.joinGame(id, player);
    }

    public void createGame(String username, int nPlayers) {
        Player player = new Player(TokenColor.BLUE, username);
        model.createNewGame(nPlayers, player);
    }
}
