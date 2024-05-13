package it.polimi.ingsw.gui;


import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LobbyMenuStateGUI extends JPanel implements ClientState {
    private VirtualView client;

    public LobbyMenuStateGUI(VirtualView client) {
        this.client = client;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Lobby Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton createGameButton = new JButton("Create a new game 🆕");
        createGameButton.setFont(new Font("Arial", Font.PLAIN, 18));
        createGameButton.addActionListener(e -> handleCreateGame());

        JButton joinGameButton = new JButton("Join a game 🚪");
        joinGameButton.setFont(new Font("Arial", Font.PLAIN, 18));
        joinGameButton.addActionListener(e -> handleJoinGame());

        buttonPanel.add(createGameButton);
        buttonPanel.add(joinGameButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void handleCreateGame() {
       /* try {
            client.setCurrentState(new CreateGameState(client));
        } catch (RemoteException e) {
            e.printStackTrace();  // Handle exceptions properly
        }*/
    }

    private void handleJoinGame() {
      /*  try {
            client.setCurrentState(new JoinGameMenuState(client));
        } catch (RemoteException e) {
            e.printStackTrace();  // Handle exceptions properly
        }*/
    }

    @Override
    public void display() {
        this.setVisible(true);
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }
}