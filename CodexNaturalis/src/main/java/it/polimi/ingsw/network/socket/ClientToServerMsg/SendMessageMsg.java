package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.GameIdMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ReceivedMessage;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class SendMessageMsg extends ClientToServerMsg{
    private Message msg;

    public SendMessageMsg(Message msg) {
        this.msg = msg;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
       ReturnableObject response = new ReturnableObject();
       controller.sendMessage(msg);
       response.setIntResponse(-1);
       return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ReceivedMessage();
    }
}
