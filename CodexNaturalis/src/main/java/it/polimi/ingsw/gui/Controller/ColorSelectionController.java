package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.ObjectiveCardSelectionStateGUI;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ColorSelectionController {
    private final Stage stage;
    @FXML
    private ListView<TokenColor> colorListView;
    @FXML
    private Label messageLabel;
    @FXML
    private Button selectColorButton;

    private final VirtualView client;

    public ColorSelectionController(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        try {
            updateColorList();
        } catch (Exception e) {
            messageLabel.setText("Failed to load colors: " + e.getMessage());
        }

        selectColorButton.setDisable(true);
        colorListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectColorButton.setDisable(newSelection == null);
        });
    }

    private void updateColorList() throws IOException, InterruptedException {
        ArrayList<TokenColor> colorList = client.getAvailableColors();
        if (colorList.isEmpty()) {
            messageLabel.setText("No colors available.");
            selectColorButton.setDisable(true);
        } else {
            colorListView.setItems(FXCollections.observableArrayList(colorList));
            colorListView.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(TokenColor color, boolean empty) {
                    super.updateItem(color, empty);
                    if (empty || color == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Circle colorCircle = new Circle(10, colorMap(color));
                        setText(color.name());
                        setGraphic(colorCircle);
                    }
                }
            });
        }
    }

    public void handleSelectColor() {
        TokenColor selectedColor = colorListView.getSelectionModel().getSelectedItem();
        if (selectedColor != null) {
            try {
                client.setTokenColor(selectedColor);
                client.setCurrentState(new ObjectiveCardSelectionStateGUI(stage, client));
                client.showState();
            } catch (RemoteException e) {
                messageLabel.setText("Error selecting color: " + e.getMessage());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Paint colorMap(TokenColor color) {
        return switch (color) {
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
        };
    }

}
