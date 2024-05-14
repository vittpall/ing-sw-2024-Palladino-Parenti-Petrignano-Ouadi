package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetCurrentPlayerMsg extends ClientToServerMsg{

        private int idGame;

        public GetCurrentPlayerMsg(int idGame) {
            this.idGame = idGame;
        }

        @Override
        public ReturnableObject<Integer> functionToCall(LobbyController controller) throws InterruptedException {
            ReturnableObject<Integer> response = new ReturnableObject<>();
            response.setResponseReturnable(controller.getCurrentPlayer(idGame));
            return response;
        }

        @Override
        public ServerToClientMsg getTypeofResponse() {
            return new ServerToClientMsg();
        }
}
