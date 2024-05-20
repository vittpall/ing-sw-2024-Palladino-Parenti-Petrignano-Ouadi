package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class JoinGameMenuController {
    @FXML
    private ListView<Integer> gamesListView;
    @FXML
    private Label messageLabel;
    @FXML
    private Button joinGameButton;
    @FXML
    private Button createGameButton;

    private final VirtualView client;
    private final Stage stage;

    public JoinGameMenuController(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        gamesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            joinGameButton.setDisable(newSelection == null);
        });

        try {
            updateGamesList();
        } catch (Exception e) {
            messageLabel.setText("Failed to load games: " + e.getMessage());
        }
    }

    private void updateGamesList() throws Exception {
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
    }

    public void handleJoinGame(ActionEvent actionEvent) {
        Integer selectedGame = gamesListView.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            try {
                client.joinGame(selectedGame, client.getUsername());
                messageLabel.setText("Joined game: " + selectedGame);
                // Update state or navigate
            } catch (Exception e) {
                messageLabel.setText("Error joining game: " + e.getMessage());
            }
        } else {
            messageLabel.setText("Select a game first!");
        }
    }

    public void handleCreateGame(ActionEvent actionEvent) {
        // Handle game creation logic
    }
}