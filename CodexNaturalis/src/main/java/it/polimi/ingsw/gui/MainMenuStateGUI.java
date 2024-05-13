package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainMenuStateGUI extends JPanel  implements ClientState {
    private final VirtualView client;

    public MainMenuStateGUI(VirtualView client) {
        this.client = client;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Codex Naturalis!", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JButton playButton = new JButton("Play 🎮");
        playButton.addActionListener(e -> handlePlayButton());

        add(playButton, BorderLayout.CENTER);
    }

    private void handlePlayButton() {
        try {
            client.setCurrentState(new LobbyMenuStateGUI(client));
        } catch (IOException ex) {
            ex.printStackTrace();  // Handle exceptions properly
        }
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
