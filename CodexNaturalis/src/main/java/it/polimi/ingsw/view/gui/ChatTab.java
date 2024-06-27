package it.polimi.ingsw.view.gui;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

public class ChatTab extends Tab {

    private int unreadMessages;
    private final Label counterMessages;

    /**
     * Constructor for the ChatTab class. It creates a new chat tab with the given title.
     *
     * @param title refers to the name of the chat, if it is global or private. if it's private, it's the name of the other client
     */
    public ChatTab(String title) {
        super(title);
        this.unreadMessages = 0;

        counterMessages = new Label();
        counterMessages.setStyle("-fx-background-color: #369b78; -fx-text-fill: white; -fx-padding: 2px 5px; -fx-background-radius: 50%;");
        counterMessages.setVisible(false);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(counterMessages);
        StackPane.setAlignment(counterMessages, javafx.geometry.Pos.TOP_RIGHT);
        this.setGraphic(stackPane);

    }

    /**
     * this method increases the number of unread messages in the global or in the private chat
     */
    public void incrementUnreadMessages() {
        unreadMessages++;
        updateCounterVisibility();
    }

    /**
     * this method resets the number of unread messages to zero
     */
    public void resetUnreadMessages() {
        unreadMessages = 0;
        updateCounterVisibility();
    }

    /**
     * this method makes the number of unread messages visible only if it's more than zero
     */
    private void updateCounterVisibility() {
        if (unreadMessages > 0) {
            counterMessages.setText(String.valueOf(unreadMessages));
            counterMessages.setVisible(true);
        } else {
            counterMessages.setVisible(false);
        }
    }
}
