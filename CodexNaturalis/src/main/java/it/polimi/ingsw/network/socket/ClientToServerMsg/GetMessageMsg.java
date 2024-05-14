package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.chat.Message;
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
    public ReturnableObject<Message> functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject<Message> response = new ReturnableObject<>();
        response.setMessagesResponse((controller.getMessages(receiver, gameId, sender)));
        for(Message message : controller.getMessages(receiver, gameId, sender)){
            System.out.println(message.getSender() + ": " + message.getContent());
        }
        System.out.println("Messages received: " + response.getMessagesResponse());
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
