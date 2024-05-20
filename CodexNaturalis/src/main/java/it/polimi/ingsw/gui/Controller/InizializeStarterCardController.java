package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Objects;

public class InizializeStarterCardController {
    @FXML
    private ImageView frontImageView;
    @FXML
    private ImageView backImageView;

    private final VirtualView client;
    private final Stage stage;

    public InizializeStarterCardController(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        try {
            StarterCard starterCard = client.getStarterCard();
            String frontImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + starterCard.getImageBackPath())).toExternalForm();
            String backImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + starterCard.getImageBackPath())).toExternalForm();

            frontImageView.setImage(new Image(frontImagePath));
            backImageView.setImage(new Image(backImagePath));
        } catch (RemoteException ex) {
            System.out.println("Error loading card images: " + ex.getMessage());
            // Optionally update a status label with this message if you have one in your FXML
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void handlePlayFront() {
        playStarterCard(false);
    }

    public void handlePlayBack() {
        playStarterCard(true);
    }

    private void playStarterCard(boolean faceDown) {
        try {
            client.playStarterCard(faceDown);
            String nextState = client.getNextState();
            // Transition to the next state based on the returned state from the client
            if (nextState.equals("PlayCardState")) {
                //  client.setCurrentState(new PlayCardStateGUI(client));
            } else if (nextState.equals("WaitForYourTurnState")) {
                //  client.setCurrentState(new WaitForYourTurnStateGUI(client));
            } else {
                System.out.println("Error transitioning to the next state");
            }
        } catch (PlaceNotAvailableException | CardNotFoundException | RequirementsNotMetException | IOException |
                 InterruptedException ex) {
            System.out.println("Error playing the card: " + ex.getMessage());
            // Optionally update a status label with this message if you have one in your FXML
        }
    }
}
