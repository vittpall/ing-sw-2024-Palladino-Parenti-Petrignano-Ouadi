package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

public class HeartBeatMsg extends ClientToServerMsg {


    public HeartBeatMsg() {
    }


    /**
     * @param controller
     * @param playerListener
     * @return
     * @throws InterruptedException
     * @throws PlaceNotAvailableException
     * @throws RequirementsNotMetException
     * @throws IOException
     */
    @Override
    public ReturnableObject functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException, IOException {
        //   ((ClientHandler) playerListener).sendHeartBeat(timestamp);
        return null;
    }

    /**
     * @return
     */
    @Override
    public TypeServerToClientMsg getType() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return 0;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
