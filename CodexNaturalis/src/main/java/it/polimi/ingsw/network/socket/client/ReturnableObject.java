package it.polimi.ingsw.network.socket.client;

import it.polimi.ingsw.model.enumeration.ErrorCodes;

import java.io.Serializable;

/**
 * The class is used to pass an object over the network.
 * It's a generic class then it's not necessary to create a custom attribute for each object that needs to be passed (i.g. HashMap ...).
 *
 * @param <T>
 */
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
     *
     * @param response is the response to set
     */
    public void setResponseReturnable(T response) {
        this.response = response;
    }

    /**
     * Getter of the response
     *
     * @return the response
     */
    public T getResponseReturnable() {
        return this.response;
    }

    /**
     * Getter of the error code
     *
     * @return the error code
     */
    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    /**
     * This method sets the error code
     *
     * @param errorCode is the error code
     */
    public void setErrorCode(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * getter of the error message
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * This method sets the error message
     *
     * @param errorMessage is the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * This method checks if the operation was successful
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean isSuccess() {
        return errorCode == ErrorCodes.SUCCESS;
    }

}

