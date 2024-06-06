package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.Server.ClientHandler;

import java.io.IOException;

public class HeartBeatMsg extends ClientToServerMsg{

    private long timestamp;

    public HeartBeatMsg(long timestamp) {
         this.timestamp = timestamp;
    }


    /**
     * @param controller
     * @param playerListener
     * @return
     * @throws InterruptedException
     * @throws CardNotFoundException
     * @throws PlaceNotAvailableException
     * @throws RequirementsNotMetException
     * @throws IOException
     */
    @Override
    public ReturnableObject functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException, IOException {
        ((ClientHandler) playerListener).sendHeartBeat(timestamp);
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
}
