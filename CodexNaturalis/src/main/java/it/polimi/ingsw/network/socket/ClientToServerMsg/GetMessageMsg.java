package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ReceivedMessage;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetMessageMsg extends ClientToServerMsg{
    private String receiver;
    private int gameId;
    private String sender;

    public GetMessageMsg(String receiver, int gameId, String sender) {
        this.receiver = receiver;
        this.gameId = gameId;
        this.sender = sender;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject response = new ReturnableObject();
        response.setArrayListResponse(controller.getMessages(receiver, gameId, sender));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ReceivedMessage();
    }
}
