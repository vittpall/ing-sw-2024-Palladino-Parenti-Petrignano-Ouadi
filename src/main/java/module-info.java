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

    exports it.polimi.ingsw.controller;
    exports it.polimi.ingsw.main;
    exports it.polimi.ingsw.model;
    exports it.polimi.ingsw.model.enumeration;
    exports it.polimi.ingsw.network.remoteInterfaces;
    exports it.polimi.ingsw.network.rmi.client;
    exports it.polimi.ingsw.network.rmi.server;
    exports it.polimi.ingsw.network.socket.client;
    exports it.polimi.ingsw.network.socket.server;
    exports it.polimi.ingsw.network.socket.clientToServerMsg;
    exports it.polimi.ingsw.network.socket.serverToClientMsg;
    exports it.polimi.ingsw.model.exceptions;
    exports it.polimi.ingsw.model.chat;
    exports it.polimi.ingsw.model.strategyPatternObjective;
    exports it.polimi.ingsw.view.gui;
    exports it.polimi.ingsw.view.tui;
    exports it.polimi.ingsw.util;
    exports it.polimi.ingsw;
    exports it.polimi.ingsw.controller.observer;
    exports it.polimi.ingsw.network;
    exports it.polimi.ingsw.network.notifications;
    opens it.polimi.ingsw.view.gui.controller to javafx.fxml;
    opens it.polimi.ingsw.model to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.view;
    opens it.polimi.ingsw.view.gui to com.fasterxml.jackson.databind, javafx.fxml;

}