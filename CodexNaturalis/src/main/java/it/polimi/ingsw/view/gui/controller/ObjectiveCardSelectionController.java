package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.view.gui.InitializeStarterCardStateGUI;
import it.polimi.ingsw.view.gui.CardView;
import it.polimi.ingsw.view.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This class is the controller for the ObjectiveCardSelection.fxml file
 */
public class ObjectiveCardSelectionController implements FXMLController {
    public VBox card1Container;
    public VBox card2Container;
    public Button exit;
    private Stage stage;
    public Label messageLabel;

    private BaseClient client;
    private ArrayList<ObjectiveCard> playerObjectiveCards;


    /**
     * this method loads two objective cards. the client will choose one of them
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void loadCards() throws RemoteException {
        try {
            playerObjectiveCards = client.getPlayerObjectiveCards();
            if (playerObjectiveCards.size() >= 2) {
                CardView card1 = new CardView(playerObjectiveCards.get(0), true);
                CardView card2 = new CardView(playerObjectiveCards.get(1), true);

                card1Container.getChildren().addFirst(card1);
                card2Container.getChildren().addFirst(card2);
            } else {
                messageLabel.setText("Not enough cards loaded.");
            }
        } catch (RemoteException ex) {
            throw new RemoteException();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * it is called if the client select the first objective card
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void handleSelectCard1() throws RemoteException {
        if (!playerObjectiveCards.isEmpty()) {
            selectCard(0);
        }
    }

    /**
     * it is called if the client select the second objective card
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void handleSelectCard2() throws RemoteException {
        if (playerObjectiveCards.size() > 1) {
            selectCard(1);
        }
    }

    /**
     * save the selected card and move to the next stage
     *
     * @param cardIndex is the number of the card chosen
     * @throws RemoteException if there is a problem with the remote connection
     */
    private void selectCard(int cardIndex) throws RemoteException {
        try {
            client.setObjectiveCard(cardIndex);
            client.setCurrentState(new InitializeStarterCardStateGUI(stage, client));
            client.getClientCurrentState().display();
        } catch (RemoteException e) {
            throw new RemoteException();
        } catch (IOException | InterruptedException ex) {
            messageLabel.setText("Error selecting card: " + ex.getMessage());
        }
    }

    /**
     * this method handles when the client decide to close the game and return to the Lobby Menu
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void handleExit() throws RemoteException {
        try {
            client.returnToLobby();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        client.setCurrentState(new LobbyMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    @Override
    public void setClient(BaseClient client) {
        this.client = client;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}