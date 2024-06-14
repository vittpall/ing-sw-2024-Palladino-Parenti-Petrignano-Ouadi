package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.CreateGameStateGUI;
import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.gui.WaitingForPlayersGUI;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JoinGameMenuController implements FXMLController {
    public Button createGameButton;
    @FXML
    private ListView<Integer> gamesListView;
    @FXML
    private Label messageLabel;
    @FXML
    private Button joinGameButton;

    private BaseClient client;
    private Stage stage;


    @FXML
    private void initialize() {
        gamesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> joinGameButton.setDisable(newSelection == null));
    }
    //alla posizione 0 c'è il numero totale di giocatori, alla posizione 1 c'è il numero di giocatori mancanti
    public void updateGamesList(HashMap<Integer, Integer[]> availableGames) {
            ArrayList<Integer> gameIds = new ArrayList<>(availableGames.keySet());
            if (availableGames.isEmpty()) {
                messageLabel.setText("No games available. Create a new game.");
                joinGameButton.setDisable(true);
                joinGameButton.setVisible(false);
                gamesListView.getItems().setAll(gameIds);
            } else {
                joinGameButton.setVisible(true);
                messageLabel.setText("Join a game");
                gamesListView.getItems().setAll(gameIds);
                gamesListView.setCellFactory(lv -> new ListCell<>() {
                    @Override
                    protected void updateItem(Integer gameId, boolean empty) {
                        super.updateItem(gameId, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            try {
                                setText(gameId + " - Has " + availableGames.get(gameId)[0] + " players and needs " + availableGames.get(gameId)[1] + " more players");
                            } catch (Exception e) {
                                setText("Error loading game data");
                            }
                        }
                    }
                });
            }
    }

    public void handleJoinGame() {
        Integer selectedGame = gamesListView.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            try {
                client.joinGame(selectedGame, client.getUsername());
                messageLabel.setText("Joined game: " + selectedGame);
                client.setCurrentState(new WaitingForPlayersGUI(stage, client));
                client.getClientCurrentState().display();
            } catch (Exception e) {
                messageLabel.setText("Error joining game: " + e.getMessage());
            }
        } else {
            messageLabel.setText("Select a game first!");
        }
    }

    public void handleCreateGame() {
        client.setCurrentState(new CreateGameStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    public void handleBack() {
        client.setCurrentState(new LobbyMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    public void handleException(Exception e) {
        messageLabel.setText("Error reaching the server: " + e.getMessage());
        joinGameButton.setDisable(false);
        joinGameButton.setVisible(false);
    }

    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}