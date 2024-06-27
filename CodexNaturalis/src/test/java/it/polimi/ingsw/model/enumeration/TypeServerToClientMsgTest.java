package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeServerToClientMsgTest {

    @Test
    void get_returnsCorrectTextForCloseGameWhenEnded() {
        assertEquals("CloseGameWhenEnded", TypeServerToClientMsg.CLOSE_GAME_WHEN_ENDED.get());
    }

    @Test
    void get_returnsCorrectTextForUserAlreadyTaken() {
        assertEquals("UserAlreadyTaken", TypeServerToClientMsg.USER_ALREADY_TAKEN.get());
    }

    @Test
    void get_returnsCorrectTextForAvailableColors() {
        assertEquals("AvailableColors", TypeServerToClientMsg.AVAILABLE_COLORS.get());
    }

    @Test
    void get_returnsCorrectTextForConnectionClosed() {
        assertEquals("ConnectionClosed", TypeServerToClientMsg.CONNECTION_CLOSED.get());
    }

    @Test
    void get_returnsCorrectTextForClosedGame() {
        assertEquals("ClosedGame", TypeServerToClientMsg.CLOSED_GAME.get());
    }

    @Test
    void get_returnsCorrectTextForCreatedGame() {
        assertEquals("CreatedGame", TypeServerToClientMsg.CREATED_GAME.get());
    }

    @Test
    void get_returnsCorrectTextForDrawnCard() {
        assertEquals("DrawnCard", TypeServerToClientMsg.DRAWN_CARD.get());
    }

    @Test
    void get_returnsCorrectTextForAllPlayers() {
        assertEquals("AllPlayers", TypeServerToClientMsg.ALL_PLAYERS.get());
    }

    @Test
    void get_returnsCorrectTextForAvailablePlaces() {
        assertEquals("AvailablePlaces", TypeServerToClientMsg.AVAILABLE_PLACES.get());
    }

    @Test
    void get_returnsCorrectTextForCurrentPlayer() {
        assertEquals("CurrentPlayer", TypeServerToClientMsg.CURRENT_PLAYER.get());
    }

    @Test
    void get_returnsCorrectTextForGetMessage() {
        assertEquals("GetMessage", TypeServerToClientMsg.GET_MESSAGE.get());
    }

    @Test
    void get_returnsCorrectTextForNotStartedGames() {
        assertEquals("NotStartedGames", TypeServerToClientMsg.NOT_STARTED_GAMES.get());
    }

    @Test
    void get_returnsCorrectTextForGetCurrentGameState() {
        assertEquals("GetCurrentGameState", TypeServerToClientMsg.GET_CURRENT_GAME_STATE.get());
    }

    @Test
    void get_returnsCorrectTextForGetObjectiveCard() {
        assertEquals("GetObjectiveCard", TypeServerToClientMsg.GET_OBJECTIVE_CARD.get());
    }

    @Test
    void get_returnsCorrectTextForGetPlayerDesk() {
        assertEquals("GetPlayerDesk", TypeServerToClientMsg.GET_PLAYER_DESK.get());
    }

    @Test
    void get_returnsCorrectTextForGetPlayerHand() {
        assertEquals("GetPlayerHand", TypeServerToClientMsg.GET_PLAYER_HAND.get());
    }

    @Test
    void get_returnsCorrectTextForGetPlayerObjectiveCards() {
        assertEquals("GetPlayerObjectiveCard", TypeServerToClientMsg.GET_PLAYER_OBJECTIVE_CARDS.get());
    }

    @Test
    void get_returnsCorrectTextForGetPoints() {
        assertEquals("GetPoints", TypeServerToClientMsg.GET_POINTS.get());
    }

    @Test
    void get_returnsCorrectTextForGetSharedObjectiveCards() {
        assertEquals("GetSharedObjectiveCards", TypeServerToClientMsg.GET_SHARED_OBJECTIVE_CARDS.get());
    }

    @Test
    void get_returnsCorrectTextForGetStartedCard() {
        assertEquals("GetStartedCard", TypeServerToClientMsg.GET_STARTED_CARD.get());
    }

    @Test
    void get_returnsCorrectTextForGetLastFromUsableCards() {
        assertEquals("GetLastFromUsableCards", TypeServerToClientMsg.GET_LAST_FROM_USABLE_CARDS.get());
    }

    @Test
    void get_returnsCorrectTextForGetVisibleCardsDeck() {
        assertEquals("GetVisibleCardsDeck", TypeServerToClientMsg.GET_VISIBLE_CARDS_DECK.get());
    }

    @Test
    void get_returnsCorrectTextForGetWinner() {
        assertEquals("GetWinner", TypeServerToClientMsg.GET_WINNER.get());
    }


    @Test
    void get_returnsCorrectTextForJoinGame() {
        assertEquals("JoinGame", TypeServerToClientMsg.JOIN_GAME.get());
    }

    @Test
    void get_returnsCorrectTextForPlayCard() {
        assertEquals("PlayCard", TypeServerToClientMsg.PLAY_CARD.get());
    }

    @Test
    void get_returnsCorrectTextForPlayStartedCard() {
        assertEquals("PlayStartedCard", TypeServerToClientMsg.PLAY_STARTED_CARD.get());
    }

    @Test
    void get_returnsCorrectTextForSelectedTokenColor() {
        assertEquals("SelectedTokenCard", TypeServerToClientMsg.SELECTED_TOKEN_COLOR.get());
    }

    @Test
    void get_returnsCorrectTextForReceivedMessage() {
        assertEquals("ReceivedMessage", TypeServerToClientMsg.RECEIVED_MESSAGE.get());
    }

    @Test
    void get_returnsCorrectTextForWaitForYourTurn() {
        assertEquals("WaitForYourTurn", TypeServerToClientMsg.WAIT_FOR_YOUR_TURN.get());
    }

    @Test
    void get_returnsCorrectTextForGetCurrentState() {
        assertEquals("GetCurrentState", TypeServerToClientMsg.GET_CURRENT_STATE.get());
    }

    @Test
    void get_returnsCorrectTextForCheckState() {
        assertEquals("CheckState", TypeServerToClientMsg.CHECK_STATE.get());
    }
}