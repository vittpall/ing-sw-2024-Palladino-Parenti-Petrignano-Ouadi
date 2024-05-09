package it.polimi.ingsw.model;

import it.polimi.ingsw.model.chat.Message;

import java.util.ArrayList;
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

    public ArrayList<Message> getMessages(String receiver, int gameId, String sender) {
        if (receiver == null)
            return games.get(gameId).getChats().getGlobalChat();
        else
            return games.get(gameId).getChats().getPrivateChat(receiver, sender);
    }

    public int joinGame(int gameId, Player player) {
        Game game = getGame(gameId);
        return game.addPlayer(player);
    }

    public void sendMessage(Message msg) {
        Game game = getGame(msg.getGameId());
        game.getChats().addMessage(msg);

    }

    public String getUsernamePlayerThatStoppedTheGame(int idGame) {
        return games.get(idGame).getUsernamePlayerThatStoppedTheGame();
    }
}
