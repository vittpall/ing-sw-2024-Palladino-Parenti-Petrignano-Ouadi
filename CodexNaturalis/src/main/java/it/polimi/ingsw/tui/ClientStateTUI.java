package it.polimi.ingsw.tui;

import it.polimi.ingsw.core.ClientState;

import java.io.IOException;

/**
 * This interface is the model of the input handler and prompt for input of every state in TUI mode
 * It has methods that describes which prompt to print and how to handle the user's input
 */
public interface ClientStateTUI extends ClientState {
    /**
     * Defines how to handle the input of the user.
     * It calls different client's functions according to the user's input and the subclass that is running
     *
     * @param input The user's input
     * @throws IOException          when an I/O operation fails
     * @throws InterruptedException when the thread running is interrupted
     */
    void inputHandler(int input) throws IOException, InterruptedException;

    /**
     * Prints the possible input the user can type in every subclass and which methods it refers to
     *
     */
    void promptForInput();
}
