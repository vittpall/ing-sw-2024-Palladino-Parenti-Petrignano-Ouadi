package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to send a message
 */
public class SendMessageMsg extends ClientToServerMsg {
    private final Message msg;

    /**
     * Constructor
     *
     * @param msg Message to send
     */
    public SendMessageMsg(Message msg) {
        this.msg = msg;
    }

    @Override
    public ReturnableObject<Message> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<Message> response = new ReturnableObject<>();
        controller.sendMessage(msg.getGameId(), msg);
        response.setResponseReturnable(msg);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.RECEIVED_MESSAGE;
    }


    @Override
    public int getIdGame() {
        return this.msg.getGameId();
    }

    @Override
    public String getUsername() {
        return "";
    }

}
