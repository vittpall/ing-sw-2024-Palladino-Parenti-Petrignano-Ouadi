package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class PlayStarterCard extends ClientToServerMsg{

    private int idGame;
    private int idClientIntoGame;
    private boolean playedFacedDown;

    public PlayStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.playedFacedDown = playedFacedDown;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject response = new ReturnableObject();
        controller.playStarterCard(idGame, idClientIntoGame, playedFacedDown);
        response.setIntResponse(-1);
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
