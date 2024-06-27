package it.polimi.ingsw.network.socket.client;

import it.polimi.ingsw.model.enumeration.ErrorCodes;

import java.io.Serializable;

public class ReturnableObject<T> implements Serializable {
    private T response;
    private ErrorCodes errorCode;
    private String errorMessage;

    /**
     * Constructor
     */
    public ReturnableObject() {
        this.errorCode = ErrorCodes.SUCCESS;
    }

    /**
     * Constructor
     *
      * @param response is the response
     */
    public ReturnableObject(T response) {
        this.response = response;
        this.errorCode = ErrorCodes.SUCCESS;
    }

    /**
     * Constructor
     *
     * @param errorCode    is the code in case of error
     * @param errorMessage is the notification in case of error
     */
    public ReturnableObject(ErrorCodes errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Set the response
     * @param response
     */
    public void setResponseReturnable(T response) {
        this.response = response;
    }

    public T getResponseReturnable() {
        return this.response;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return errorCode == ErrorCodes.SUCCESS;
    }

}

