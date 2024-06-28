package it.polimi.ingsw.model.enumeration;

/**
 * This enums contains possible error codes
 */
public enum ErrorCodes {
    SUCCESS(0),
    REQUIREMENTS_NOT_MET(2), GAME_NOT_FOUND(3);

    private final int code;

    /**
     * Constructor
     * @param code represents the error code
     */
    ErrorCodes(int code) {
        this.code = code;
    }

    /**
     * @return the value of the error code
     */
    public int get() {
        return code;
    }

    @Override
    public String toString() {
        return Integer.toString(code);
    }
}
