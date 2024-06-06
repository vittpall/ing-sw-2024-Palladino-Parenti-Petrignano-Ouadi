package it.polimi.ingsw.model.enumeration;

public enum TypeServerToClientMsg {
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
    GET_USERNAME_PLAYER_THAT_STOPPED_THE_GAME("GetUsernamePlayerThatStoppedTheGame"),
    GET_VISIBLE_CARDS_DECK("GetVisibleCardsDeck"),
    GET_WINNER("GetWinner"),
    IS_LAST_ROUND_STARTED("IsLastRoundStarted"),
    JOIN_GAME("JoinGame"),
    PLAY_CARD("PlayCard"),
    PLAY_LAST_TURN("PlayLastTurn"),
    PLAY_STARTED_CARD("PlayStartedCard"),
    SELECTED_TOKEN_COLOR("SelectedTokenCard"),
    RECEIVED_MESSAGE("ReceivedMessage"),
    SET_OBJECTIVE_CARD("SetObjectiveCard"),
    WAIT_FOR_YOUR_TURN("WaitForYourTurn"),
    GET_CURRENT_STATE("GetCurrentState"),
    CHECK_STATE("CheckState"),
    TOKEN_COLOR_SELECTED("TokenColorSelected"),
    CLOSE_CONNECTION("CloseConnection");

    private String text;

    TypeServerToClientMsg(String text) {
        this.text = text;
    }

    public String get() {
        return text;
    }


}

