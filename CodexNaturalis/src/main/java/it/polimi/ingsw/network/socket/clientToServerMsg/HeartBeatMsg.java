package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

public class HeartBeatMsg extends ClientToServerMsg {


    public HeartBeatMsg() {
    }


    @Override
    public ReturnableObject functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException, IOException {
        //   ((ClientHandler) playerListener).sendHeartBeat(timestamp);
        return null;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return null;
    }

    @Override
    public int getIdGame() {
        return 0;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
