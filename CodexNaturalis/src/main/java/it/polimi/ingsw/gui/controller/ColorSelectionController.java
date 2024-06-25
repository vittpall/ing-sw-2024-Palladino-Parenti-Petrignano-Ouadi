package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.gui.ObjectiveCardSelectionStateGUI;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is the controller for the ColorSelection.fxml file
 */
public class ColorSelectionController implements FXMLController {
    public HBox colorContainer;
    public Label messageLabel;
    public Button selectColorButton;

    public Button exit;
    private Stage stage;
    private BaseClient client;
    private TokenColor selectedColor;

    /**
     * This method initialize the stage and disables the selectColorButton
     */
    @FXML
    private void initialize() {
        selectColorButton.setDisable(true);
    }

    /**
     * This method updates the available token colors
     *
     * @param colorList is the ArrayList that contains the available token colors
     */
    public void updateColorList(ArrayList<TokenColor> colorList) {

        colorContainer.getChildren().clear();
        for (TokenColor color : colorList) {
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/" + color.getImageName()))));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            imageView.setOnMouseClicked(event -> {
                selectedColor = color;
                selectColorButton.setDisable(false);
            });

            colorContainer.getChildren().add(imageView);
        }
    }

    /**
     * This method connects the chosen color to the respective client.
     * If two client choose the same color there is an error message
     *
     * @throws IOException          if there is a problem with the I/O operations
     * @throws InterruptedException if the thread running is interrupted
     */
    @FXML
    public void handleSelectColor() throws IOException, InterruptedException {
        if (selectedColor != null && client.getAvailableColors().contains(selectedColor)) {
            try {
                client.setTokenColor(selectedColor);
                client.setCurrentState(new ObjectiveCardSelectionStateGUI(stage, client));
                client.getClientCurrentState().display();
            } catch (RemoteException e) {
                throw new RemoteException();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            messageLabel.setText("Color not available, please select another color.");
        }
    }

    /**
     * This method handles when the client decide to close the game and return to the Lobby Menu
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

    /**
     * This method sets the Error message
     *
     * @param e the Exception that needs to be shawn
     */
    public void handleException(Exception e) {
        messageLabel.setText("Error reaching the server: " + e.getMessage());
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
