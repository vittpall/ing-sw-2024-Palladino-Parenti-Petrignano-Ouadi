package it.polimi.ingsw.model.enumeration;

/**
 * This enums contains all the possible types of messages that can be sent from the server to the client
 */
public enum TypeServerToClientMsg {
    CLOSE_GAME_WHEN_ENDED("CloseGameWhenEnded"),
    USER_ALREADY_TAKEN("UserAlreadyTaken"),
    AVAILABLE_COLORS("AvailableColors"),
    CONNECTION_CLOSED("ConnectionClosed"),
    CLOSED_GAME("ClosedGame"),
    CREATED_GAME("CreatedGame"),
    DRAWN_CARD("DrawnCard"),
    ALL_PLAYERS("AllPlayers"),
    AVAILABLE_PLACES("AvailablePlaces"),
    CURRENT_PLAYER("CurrentPlayer"),
    GET_MESSAGE("GetMessage"),
    NOT_STARTED_GAMES("NotStartedGames"),
    GET_CURRENT_GAME_STATE("GetCurrentGameState"),
    GET_OBJECTIVE_CARD("GetObjectiveCard"),
    GET_PLAYER_DESK("GetPlayerDesk"),
    GET_PLAYER_HAND("GetPlayerHand"),
    GET_PLAYER_OBJECTIVE_CARDS("GetPlayerObjectiveCard"),
    GET_POINTS("GetPoints"),
    GET_SHARED_OBJECTIVE_CARDS("GetSharedObjectiveCards"),
    GET_STARTED_CARD("GetStartedCard"),
    GET_LAST_FROM_USABLE_CARDS("GetLastFromUsableCards"),
    GET_VISIBLE_CARDS_DECK("GetVisibleCardsDeck"),
    GET_WINNER("GetWinner"),
    JOIN_GAME("JoinGame"),
    PLAY_CARD("PlayCard"),
    PLAY_STARTED_CARD("PlayStartedCard"),
    SELECTED_TOKEN_COLOR("SelectedTokenCard"),
    RECEIVED_MESSAGE("ReceivedMessage"),
    WAIT_FOR_YOUR_TURN("WaitForYourTurn"),
    GET_CURRENT_STATE("GetCurrentState"),
    CHECK_STATE("CheckState");

    private final String text;

    /**
     * Constructor
     *
     * @param text represents the type of message
     */
    TypeServerToClientMsg(String text) {
        this.text = text;
    }

    /**
     * @return the value of the string representing the type of message
     */
    public String get() {
        return text;
    }


}

