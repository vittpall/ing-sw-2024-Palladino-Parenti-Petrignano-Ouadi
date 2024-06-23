package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.gui.CardView;
import it.polimi.ingsw.gui.GameStateGUI;
import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.network.BaseClient;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class InitializeStarterCardController implements FXMLController {
    public Button playFrontButton;
    public Button playBackButton;

    public Button exit;
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

    public void handlePlayFront() throws RemoteException {
        playStarterCard(false);
    }

    public void handlePlayBack() throws RemoteException {
        playStarterCard(true);
    }
    public void handleExit() throws RemoteException {
        try {
            client.returnToLobby();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        client.setCurrentState(new LobbyMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    private void playStarterCard(boolean faceDown) throws RemoteException {
        try {
            client.playStarterCard(faceDown);
            client.setCurrentState(new GameStateGUI(stage, client));
            client.getClientCurrentState().display();
        } catch (RemoteException e) {
            throw new RemoteException();
        } catch (PlaceNotAvailableException | RequirementsNotMetException | IOException |
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
