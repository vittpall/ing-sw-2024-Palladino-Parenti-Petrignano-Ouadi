package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.util.ArrayList;

public class GetMessageMsg extends ClientToServerMsg{
    private final String receiver;
    private final int gameId;
    private final String sender;

    public GetMessageMsg(String receiver, int gameId, String sender) {
        this.receiver = receiver;
        this.gameId = gameId;
        this.sender = sender;
    }

    @Override
    public ReturnableObject<ArrayList<Message>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<ArrayList<Message>> response = new ReturnableObject<>();
        response.setResponseReturnable((controller.getMessages(receiver, gameId, sender)));
        for(Message message : controller.getMessages(receiver, gameId, sender)){
            System.out.println(message.getSender() + ": " + message.getContent());
        }
        System.out.println("Messages received: " + response.getResponseReturnable());
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_MESSAGE;
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
        return "";
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return gameId;
    }
}
