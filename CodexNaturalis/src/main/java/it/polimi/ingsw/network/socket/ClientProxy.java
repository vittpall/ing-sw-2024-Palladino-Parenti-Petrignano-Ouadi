package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.rmi.VirtualView;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.Buffer;

public class ClientProxy implements VirtualView {
    final PrintWriter output;

    public ClientProxy(BufferedWriter output)
    {
        //print stuff to the user
        this.output = new PrintWriter(output);
    }
}
