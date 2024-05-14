package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetVisibleCardsDeckMsg extends ClientToServerMsg{

    int deck;
    int idGame;

    public GetVisibleCardsDeckMsg(int idGame, int deck) {
        this.deck = deck;
        this.idGame = idGame;
    }

    /**
     * @param controller
     * @return ReturnableObject
     * @throws InterruptedException
     * @throws CardNotFoundException
     * @throws PlaceNotAvailableException
     * @throws RequirementsNotMetException
     */
    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject response = new ReturnableObject();
        response.setArrayListResponse(controller.getVisibleCardsDeck(idGame, deck));
        return response;
    }

    /**
     * @return ServerToClientMsg
     */
    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
