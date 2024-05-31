package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.ColorSelectionGUI;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitingForPlayersController implements FXMLController {
    private BaseClient client;
    private Stage stage;
    @FXML
    Label textWaitingPlayers;
    @FXML
    Label player2Entered;
    @FXML
    Label player3Entered;
    @FXML
    Label player4Entered;
    @FXML
    Label waitingForNPlayersLabel;


    public void initializeWaitingForPlayers() {
        player2Entered.setVisible(false);
        player3Entered.setVisible(false);
        player4Entered.setVisible(false);
        try {
            if (!(client.getnPlayer(client.getIdGame()) > client.getPlayers(client.getIdGame()).size())) {
                textWaitingPlayers.setText("You can start the game");
            }
            waitingForNPlayersLabel.setText("Waiting for " + (client.getnPlayer(client.getIdGame()) - client.getPlayers(client.getIdGame()).size()) + " players to join the game...");
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
            if (client.getPlayers(client.getIdGame()).size() == 2) {
                player2Entered.setText(msg);
                player2Entered.setVisible(true);
                waitingForNPlayersLabel.setText("Waiting for " + (client.getnPlayer(client.getIdGame() - client.getPlayers(client.getIdGame()).size())) + " players to join the game...");
            } else if (client.getPlayers(client.getIdGame()).size() == 3) {
                player3Entered.setText(msg);
                player3Entered.setVisible(true);
                waitingForNPlayersLabel.setText("Waiting for " + (client.getnPlayer(client.getIdGame() - client.getPlayers(client.getIdGame()).size())) + " players to join the game...");
            } else if (client.getPlayers(client.getIdGame()).size() == 4) {
                player4Entered.setText(msg);
                player4Entered.setVisible(true);
                waitingForNPlayersLabel.setText("Waiting for " + (client.getnPlayer(client.getIdGame() - client.getPlayers(client.getIdGame()).size())) + " players to join the game...");
            }
            if (client.getnPlayer(client.getIdGame()) == client.getPlayers(client.getIdGame()).size()) {
                waitingForNPlayersLabel.setText("You can start the game");
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
