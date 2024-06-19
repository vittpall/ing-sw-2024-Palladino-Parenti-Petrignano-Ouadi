package it.polimi.ingsw.gui;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

public class ChatTab extends Tab {

    private int unreadMessages;
    private Label counterMessages;

    public ChatTab(String title) {
        super(title);
        this.unreadMessages = 0;

        counterMessages = new Label();
 //       counterMessages.setStyle("-fx-background-color: #198d19; -fx-text-fill: white; -fx-padding: 2px 5px; -fx-background-radius: 50%;");
        counterMessages.setVisible(false);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(this.getGraphic(), counterMessages);
   //     StackPane.setAlignment(counterMessages, javafx.geometry.Pos.TOP_RIGHT);

        this.setGraphic(stackPane);
        System.out.println("ChatTab created");
    }

    public void incrementUnreadMessages() {
        unreadMessages++;
        updateCounterVisibility();
    }

    public void resetUnreadMessages() {
        unreadMessages = 0;
        updateCounterVisibility();
    }

    private void updateCounterVisibility() {
        if (unreadMessages > 0) {
            counterMessages.setText(String.valueOf(unreadMessages));
            counterMessages.setVisible(true);
        } else {
            counterMessages.setVisible(false);
        }
    }
}
