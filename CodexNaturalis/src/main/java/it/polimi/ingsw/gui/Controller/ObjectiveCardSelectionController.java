package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.CardView;
import it.polimi.ingsw.gui.InitializeStarterCardStateGUI;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ObjectiveCardSelectionController implements FXMLController {
    public VBox card1Container;
    public VBox card2Container;
    private Stage stage;
    public Label messageLabel;

    private BaseClient client;
    private ArrayList<ObjectiveCard> playerObjectiveCards;


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

    public void handleSelectCard1() throws RemoteException {
        if (!playerObjectiveCards.isEmpty()) {
            selectCard(0);
        }
    }

    public void handleSelectCard2() throws RemoteException {
        if (playerObjectiveCards.size() > 1) {
            selectCard(1);
        }
    }

    private void selectCard(int cardIndex) throws RemoteException {
        try {
            client.setObjectiveCard(cardIndex);
            client.setCurrentState(new InitializeStarterCardStateGUI(stage, client));
            client.getClientCurrentState().display();
        } catch (RemoteException e) {
            throw new RemoteException();
        }
        catch (CardNotFoundException | IOException | InterruptedException ex) {
            messageLabel.setText("Error selecting card: " + ex.getMessage());
        }
    }

    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}