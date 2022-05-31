module it.polimi.ingsw {
    requires javafx.graphics;
    requires javafx.controls;

    exports it.polimi.ingsw.network.client.gui;

    requires com.google.gson;
    requires javafx.fxml;

    opens it.polimi.ingsw.network.client to com.google.gson;
    opens it.polimi.ingsw.network.messages to com.google.gson;
    opens it.polimi.ingsw.network.messages.clientMessages to com.google.gson;
    opens it.polimi.ingsw.network.messages.serverMessages to com.google.gson;
    opens it.polimi.ingsw.network.server to com.google.gson;
    exports it.polimi.ingsw.network.client.gui.controllers;
}