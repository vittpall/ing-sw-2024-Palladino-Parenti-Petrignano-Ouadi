package it.polimi.ingsw.network.socket.Client;

import java.io.Serializable;

public class ReturnableObject<T> implements Serializable {
    private T response;

    public void setResponseReturnable(T response) {
        this.response = response;
    }

    public T getResponseReturnable(){
        return this.response;
    }

}

