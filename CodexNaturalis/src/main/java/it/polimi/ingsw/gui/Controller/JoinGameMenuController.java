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

public class JoinGameMenuController implements FXMLController {
    public Button createGameButton;
    @FXML
    private ListView<Integer> gamesListView;
    @FXML
    private Label messageLabel;
    @FXML
    private Button joinGameButton;
    @FXML

    private BaseClient client;
    private Stage stage;


    @FXML
    private void initialize() {
        gamesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> joinGameButton.setDisable(newSelection == null));
    }

    public void updateGamesList() {
        try {
            ArrayList<Integer> gameIds = client.getNotStartedGames();
            if (gameIds.isEmpty()) {
                messageLabel.setText("No games available. Create a new game.");
                joinGameButton.setDisable(true);
            } else {
                gamesListView.getItems().setAll(gameIds);
                gamesListView.setCellFactory(lv -> new ListCell<>() {
                    @Override
                    protected void updateItem(Integer gameId, boolean empty) {
                        super.updateItem(gameId, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            try {
                                setText(gameId + " - Needs " + (client.getnPlayer(gameId) - client.getPlayers(gameId).size()) + " more players");
                            } catch (Exception e) {
                                setText("Error loading game data");
                            }
                        }
                    }
                });
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
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

    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}