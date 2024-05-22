package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.GameState;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Objects;

public class InitializeStarterCardController implements FXMLController {
    public Button playFrontButton;
    public Button playBackButton;
    @FXML
    private ImageView frontImageView;
    @FXML
    private ImageView backImageView;

    private VirtualView client;
    private Stage stage;


    public void initializeStarterCard() {
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
            client.setCurrentState(new GameState(stage, client));
            client.showState();
        } catch (PlaceNotAvailableException | CardNotFoundException | RequirementsNotMetException | IOException |
                 InterruptedException ex) {
            System.out.println("Error playing the card: " + ex.getMessage());
        }
    }


    public void setClient(VirtualView client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
