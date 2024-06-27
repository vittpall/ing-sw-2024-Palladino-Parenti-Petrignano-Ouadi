package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.view.gui.ColorSelectionGUI;
import it.polimi.ingsw.view.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This class is the controller for the WaitingForPlayers.fxml file
 */
public class WaitingForPlayersController implements FXMLController {
    public Button StartGameButton;
    public Button exit;
    private BaseClient client;
    private Stage stage;
    @FXML
    Label player1Info;
    @FXML
    Label player2Info;
    @FXML
    Label player3Info;
    @FXML
    Label player4Info;
    @FXML
    Label waitingForNPlayersLabel;


    /**
     * initialize the WaitingForPlayers stage. it prints how many player the game needs before it can start, and which player has join the game.
     * once every player joined the game, it will be possible start the game
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void initializeWaitingForPlayers() throws RemoteException {

        try {
            ArrayList<Player> players = client.getPlayers(client.getIdGame());
            int size = players.size();
            switch (size) {
                case 1:
                    player1Info.setText("Player 1 : " + players.getFirst().getUsername());
                    player2Info.setVisible(false);
                    player3Info.setVisible(false);
                    player4Info.setVisible(false);
                    break;
                case 2:
                    player1Info.setText("Player 1 : " + players.get(0).getUsername());
                    player2Info.setText("Player 2 : " + players.get(1).getUsername());
                    player3Info.setVisible(false);
                    player4Info.setVisible(false);
                    break;
                case 3:
                    player1Info.setText("Player 1 : " + players.get(0).getUsername());
                    player2Info.setText("Player 2 : " + players.get(1).getUsername());
                    player3Info.setText("Player 3 : " + players.get(2).getUsername());
                    player4Info.setVisible(false);
                    break;
                case 4:
                    player1Info.setText("Player 1 : " + players.get(0).getUsername());
                    player2Info.setText("Player 2 : " + players.get(1).getUsername());
                    player3Info.setText("Player 3 : " + players.get(2).getUsername());
                    player4Info.setText("Player 4 : " + players.get(3).getUsername());
                    break;
                default:
                    break;
            }
            if (!(client.getnPlayer(client.getIdGame()) > size)) {
                waitingForNPlayersLabel.setText("You can start the game");
                StartGameButton.setVisible(true);
            } else {
                StartGameButton.setVisible(false);
                waitingForNPlayersLabel.setText("Waiting for " + (client.getnPlayer(client.getIdGame()) - size) + " players to join the game...");
            }
        } catch (RemoteException e) {
            throw new RemoteException();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * if the client press the button "start game", he will go on to ColorSelection stage
     */
    public void handleStartGame() throws RemoteException {
        try {
            if (client.isGameStarted()) {
                client.setCurrentState(new ColorSelectionGUI(stage, client));
                client.getClientCurrentState().display();
            }
        } catch (IOException | InterruptedException e) {
            throw new RemoteException();
        }
    }

    /**
     * this method handles when the client decide to close the game and return to the Lobby Menu
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void handleExit() throws RemoteException {
        try {
            client.returnToLobby();
        } catch (IOException | InterruptedException e) {
            throw new RemoteException();
        }
        client.setCurrentState(new LobbyMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    /**
     * Notification method
     * It is called when a player joins the specific game
     * It updates how many players are needed to start the game and prints the name of the players that have joined the game
     *
     * @param players           ArrayList of players that have joined the game
     * @param nOfMissingPlayers number of player needed before it is possible start the game. it can be a number between 1 and 3
     */
    public void handleServerNotification(ArrayList<Player> players, int nOfMissingPlayers) {
        switch (players.size()) {
            case 2:
                player2Info.setText("Player 2 : " + players.get(1).getUsername());
                player2Info.setVisible(true);
                break;
            case 3:
                player3Info.setText("Player 3 : " + players.get(2).getUsername());
                player3Info.setVisible(true);
                break;
            case 4:
                player4Info.setText("Player 4 : " + players.get(3).getUsername());
                player4Info.setVisible(true);
                break;
            default:
                break;
        }

        waitingForNPlayersLabel.setText("Waiting for " + nOfMissingPlayers + " players to join the game...");

        if (nOfMissingPlayers == 0) {
            waitingForNPlayersLabel.setText("You can start the game");
            StartGameButton.setVisible(true);
        } else {
            StartGameButton.setVisible(false);
        }

    }

    @Override
    public void setClient(BaseClient client) {
        this.client = client;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
