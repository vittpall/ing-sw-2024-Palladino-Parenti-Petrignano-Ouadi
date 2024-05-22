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

public class ColorSelectionController implements FXMLController {
    private Stage stage;
    @FXML
    private ListView<TokenColor> colorListView;
    @FXML
    private Label messageLabel;
    @FXML
    private Button selectColorButton;

    private VirtualView client;


    @FXML
    private void initialize() {
        selectColorButton.setDisable(true);
        colorListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> selectColorButton.setDisable(newSelection == null));
    }

    public void updateColorList() {
        try {
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
        } catch (IOException | InterruptedException e) {
            messageLabel.setText("Error updating color list: " + e.getMessage());
        }
    }

    //TODO here the listener will recall the method display to refresh the list of colors.
    public void handleSelectColor() throws IOException, InterruptedException {
        TokenColor selectedColor = colorListView.getSelectionModel().getSelectedItem();
        if (selectedColor != null && client.getAvailableColors().contains(selectedColor)) {
            try {
                client.setTokenColor(selectedColor);
                client.setCurrentState(new ObjectiveCardSelectionStateGUI(stage, client));
                client.showState();
            } catch (RemoteException e) {
                messageLabel.setText("Error selecting color: " + e.getMessage());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            messageLabel.setText("Color not available, please select another color.");
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

    public void setClient(VirtualView client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
