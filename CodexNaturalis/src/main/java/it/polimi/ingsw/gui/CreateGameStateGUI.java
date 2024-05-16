package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.Objects;

public class CreateGameStateGUI implements ClientState {

    public VBox rootPane; // Assuming VBox as the root container
    public ChoiceBox<Integer> playerChoiceBox;
    public Label feedbackLabel; // Label to give feedback to users

    VirtualView client;
    Stage stage;

    public CreateGameStateGUI(VirtualView client, Stage stage) {
        this.client = client;
        this.stage = stage;
        setupUI();
    }

    private void setupUI() {
        // Assuming that the FXML is loaded somewhere else and these components are wired up
        playerChoiceBox.getItems().addAll(2, 3, 4); // Populate choice box with values
        feedbackLabel.setText("Select number of players (2-4):"); // Initial message
    }

    @Override
    public void display() {
        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show(); // Make sure the stage is visible
    }

    @Override
    public void inputHandler(int input) {
        // GUI doesn't typically use this method; interactions are handled by event handlers
    }

    @Override
    public void promptForInput() {
        // GUI interactions are direct, so this method might not be necessary
    }

    @FXML
    private void handleCreateGame() {
        Integer nPlayers = playerChoiceBox.getValue();
        if (nPlayers == null) {
            feedbackLabel.setText("Please select the number of players."); // Provide feedback directly in the UI
            return;
        }
        try {
            feedbackLabel.setText("Creating game and waiting for the players...");
            client.createGame(client.getUsername(), nPlayers);
            feedbackLabel.setText("The game " + client.getIdGame() + " has started.\nYou are player number " + client.getIdClientIntoGame());
            // Update the current state to the next stage
            //client.setCurrentState(new ColorSelection(client, stage));
        } catch (RemoteException e) {
            feedbackLabel.setText("Error creating game. Please try again.");
        } catch (Exception e) {
            feedbackLabel.setText("An unexpected error occurred. Please try again.");
            throw new RuntimeException(e);
        }
    }
}
