package it.polimi.ingsw.gui.Controller;

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

public class ColorSelectionController implements FXMLController {
    public HBox colorContainer;
    public Label messageLabel;
    public Button selectColorButton;
    private Stage stage;

    private BaseClient client;
    private TokenColor selectedColor;

    @FXML
    private void initialize() {
        selectColorButton.setDisable(true);
    }

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

    public void handleException(Exception e) {
        messageLabel.setText("Error reaching the server: " + e.getMessage());
    }
    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
