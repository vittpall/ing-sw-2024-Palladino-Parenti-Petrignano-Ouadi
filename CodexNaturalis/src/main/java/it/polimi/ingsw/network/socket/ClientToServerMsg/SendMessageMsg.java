package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class SendMessageMsg extends ClientToServerMsg{
    private Message msg;

    public SendMessageMsg(Message msg) {
        this.msg = msg;
    }

    @Override
    public ReturnableObject<Message> functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
       ReturnableObject<Message> response = new ReturnableObject<>();
       controller.sendMessage(msg);
       response.setResponseReturnable(msg);
       return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.RECEIVED_MESSAGE;
    }

}
