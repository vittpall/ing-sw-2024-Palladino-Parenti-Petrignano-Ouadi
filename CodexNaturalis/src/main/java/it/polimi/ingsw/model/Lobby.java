package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Lobby {
    private final Map<Integer, Game> games;
    private int nextGameId;

    public Lobby() {
        games = new HashMap<>();
        nextGameId = 1;
    }


    public void removeGame(int gameId) {
        games.remove(gameId);
    }

    public Game getGame(int gameId) {
        return games.get(gameId);
    }

    public HashMap<Integer, Game> listAllGames() {
        return new HashMap<>(games);
    }

    public int createNewGame(int numberOfPlayers) {
        Game newGame = new Game(nextGameId, numberOfPlayers);
        games.put(nextGameId, newGame);
        nextGameId++;
        return nextGameId - 1;
    }

    public int joinGame(int gameId, Player player) {
        Game game = getGame(gameId);
        return game.addPlayer(player);
    }

}
