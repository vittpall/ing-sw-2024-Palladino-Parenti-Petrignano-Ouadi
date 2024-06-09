package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.ColorSelectionGUI;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class WaitingForPlayersController implements FXMLController {
    public Button StartGameButton;
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


    public void initializeWaitingForPlayers() {

        try {
            ArrayList<Player> players = client.getPlayers(client.getIdGame());
            int size = players.size();
            switch (size) {
                case 0:
                    player1Info.setVisible(false);
                    player2Info.setVisible(false);
                    player3Info.setVisible(false);
                    player4Info.setVisible(false);
                    break;
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
            }
            if (!(client.getnPlayer(client.getIdGame()) > size)) {
                waitingForNPlayersLabel.setText("You can start the game");
            } else
                waitingForNPlayersLabel.setText("Waiting for " + (client.getnPlayer(client.getIdGame()) - size) + " players to join the game...");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleStartGame() {
        try {
            if (client.isGameStarted()) {
                client.setCurrentState(new ColorSelectionGUI(stage, client));
                client.showState();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleServerNotification(String msg) {
        try {
            int currentPlayers = client.getPlayers(client.getIdGame()).size();
            int totalNeededPlayers = client.getnPlayer(client.getIdGame());

            switch (currentPlayers) {
                case 2:
                    player2Info.setText(msg);
                    player2Info.setVisible(true);
                    break;
                case 3:
                    player3Info.setText(msg);
                    player3Info.setVisible(true);
                    break;
                case 4:
                    player4Info.setText(msg);
                    player4Info.setVisible(true);
                    break;
                default:
                    break;
            }

            waitingForNPlayersLabel.setText("Waiting for " + (totalNeededPlayers - currentPlayers) + " players to join the game...");

            if (currentPlayers == totalNeededPlayers) {
                waitingForNPlayersLabel.setText("You can start the game");
                StartGameButton.setVisible(true);
            } else {
                StartGameButton.setVisible(false);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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
