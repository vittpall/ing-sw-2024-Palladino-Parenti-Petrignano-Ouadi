package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.CardView;
import it.polimi.ingsw.gui.GameState;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class InitializeStarterCardController implements FXMLController {
    public Button playFrontButton;
    public Button playBackButton;
    public VBox frontCardContainer;
    public VBox backCardContainer;
    private BaseClient client;
    private Stage stage;

    public void initializeStarterCard() {
        try {
            StarterCard starterCard = client.getStarterCard();
            CardView frontCardView = new CardView(starterCard, true);
            CardView backCardView = new CardView(starterCard, false);

            frontCardContainer.getChildren().add(1, frontCardView);
            backCardContainer.getChildren().add(1, backCardView);
        } catch (RemoteException ex) {
            System.out.println("Error loading card images: " + ex.getMessage());
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


    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
