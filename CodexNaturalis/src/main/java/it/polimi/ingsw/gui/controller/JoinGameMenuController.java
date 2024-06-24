package it.polimi.ingsw.gui.controller;

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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class JoinGameMenuController implements FXMLController {
    public Button createGameButton;
    public Button exit;
    @FXML
    private ListView<Integer> gamesListView;
    @FXML
    private Label messageLabel;
    @FXML
    private Button joinGameButton;

    private BaseClient client;
    private Stage stage;


    @FXML
    /**
     * disable the join game until the client doesn't select a game from the list
     */
    private void initialize() {
        gamesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> joinGameButton.setDisable(newSelection == null));
    }

    //alla posizione 0 c'è il numero totale di giocatori, alla posizione 1 c'è il numero di giocatori mancanti

    /**
     * this method get the list of available games and how many player need a game.
     * if there isn't any game the client has to create a new game or turn to the lobby menu and wait the creation of a new game.
     * @param availableGames list of available games: at position 0 there is the total number of players, at position 1 the number of players needed .
     */
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
            messageLabel.setText("Select A Game");
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

    /**
     * this method updates the state after the client press "Join selected game" button. If the client didn't select a game he can't go on.
      * @throws RemoteException
     */
    public void handleJoinGame() throws RemoteException {
        Integer selectedGame = gamesListView.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            try {
                client.joinGame(selectedGame, client.getUsername());
                messageLabel.setText("Joined game: " + selectedGame);
                client.setCurrentState(new WaitingForPlayersGUI(stage, client));
                client.getClientCurrentState().display();
            } catch (RemoteException e) {
                throw new RemoteException();
            } catch (Exception e) {
                messageLabel.setText("Error joining game: " + e.getMessage());
            }
        } else {
            messageLabel.setText("Select a game first!");
        }
    }

    /**
     * this method return to the CreateGame state if the client press the button "Create a new Game"
     * @throws RemoteException
     */
    public void handleCreateGame() throws RemoteException {
        client.setCurrentState(new CreateGameStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    /**
     * this method closes the game if the client press the button "EXIT"
     * @throws RemoteException
     */
    public void handleExit() throws RemoteException {
        client.close();
    }

    /**
     * this method return to the LobbyMenu state if the client press the button "back"
     * @throws RemoteException
     */
    public void handleBack() throws RemoteException {
        client.setCurrentState(new LobbyMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    /**
     *this method sets the Error message
     * @param e if there is a problem reaching the server
     */
    public void handleException(Exception e) {
        messageLabel.setText("Error reaching the server: " + e.getMessage());
        joinGameButton.setDisable(false);
        joinGameButton.setVisible(false);
    }

    /**
     * Constructor
     * @param client refers to the current client
     */
    public void setClient(BaseClient client) {
        this.client = client;
    }

    /**
     * Constructor
     * @param stage refers to the stage of the window
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}