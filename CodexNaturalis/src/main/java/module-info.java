module PSP44 {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires java.management;
    requires java.rmi;

    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.logging;


    exports it.polimi.ingsw.model;
    exports it.polimi.ingsw.model.enumeration;
    exports it.polimi.ingsw.network.RemoteInterfaces;
    exports it.polimi.ingsw.network.rmi.Client;
    exports it.polimi.ingsw.network.rmi.Server;
    exports it.polimi.ingsw.network.socket.Client;
    exports it.polimi.ingsw.network.socket.Server;
    exports it.polimi.ingsw.network.socket.ClientToServerMsg;
    exports it.polimi.ingsw.network.socket.ServerToClientMsg;
    exports it.polimi.ingsw.model.Exceptions;
    exports it.polimi.ingsw.model.chat;
    exports it.polimi.ingsw.model.strategyPatternObjective;
    exports it.polimi.ingsw.gui;
    exports it.polimi.ingsw.tui;
    exports it.polimi.ingsw.util;
    opens it.polimi.ingsw.gui to javafx.fxml;
    opens it.polimi.ingsw.model to com.fasterxml.jackson.databind;

}