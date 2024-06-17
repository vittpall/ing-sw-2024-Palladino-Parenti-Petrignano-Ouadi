package it.polimi.ingsw.model.enumeration;

public enum ErrorCodes {
    SUCCESS(0),
    PLACE_NOT_AVAILABLE(1),
    REQIORMENTS_NOT_MET(2),
    CARD_NOT_FOUND(3);


    private final int code;


    ErrorCodes(int code) {
        this.code = code;
    }

    public int get() {
        return code;
    }

    @Override
    public String toString() {
        return Integer.toString(code);
    }
}
