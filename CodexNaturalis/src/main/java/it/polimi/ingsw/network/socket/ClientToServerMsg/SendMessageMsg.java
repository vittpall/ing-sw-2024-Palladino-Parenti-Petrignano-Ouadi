package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class SendMessageMsg extends ClientToServerMsg{
    private final Message msg;

    public SendMessageMsg(Message msg) {
        this.msg = msg;
    }

    @Override
    public ReturnableObject<Message> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException {
       ReturnableObject<Message> response = new ReturnableObject<>();
       controller.sendMessage(msg.getGameId(), msg);
       response.setResponseReturnable(msg);
       return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.RECEIVED_MESSAGE;
    }

    /**
     * @return
     */
    @Override
    public boolean getDoItNeedToBeBroadcasted() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
       return this.msg.getGameId();
    }

}
