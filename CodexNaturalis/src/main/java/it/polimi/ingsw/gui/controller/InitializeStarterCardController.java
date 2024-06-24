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

    /**
     * this method gets the front and the back of the initialise card to print. the client has to choose which side he will play
     */
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

    /**
     * this method handles the case where the client select the button "play front"
     * @throws RemoteException
     */
    public void handlePlayFront() throws RemoteException {
        playStarterCard(false);
    }

    /**
     * this method handles the case where the client select the button "play front"
     * @throws RemoteException
     */
    public void handlePlayBack() throws RemoteException {
        playStarterCard(true);
    }

    /**
     *this method handles when the client decide to close the game and return to the Lobby Menu
     * @throws RemoteException
     */
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

    /**
     * this method select the side of the card chosen by the client
     * @param faceDown it's true if the client choose "play front", else it is false
     * @throws RemoteException
     */
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
