module it.polimi.ingsw {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens it.polimi.ingsw.network.client.gui to javafx.fxml;
    exports it.polimi.ingsw.network.client.gui;
    exports it.polimi.ingsw.network.client.gui.controllers;
    opens it.polimi.ingsw.network.client.gui.controllers to javafx.fxml;

    requires com.google.gson;

    opens it.polimi.ingsw.network.client to com.google.gson;
    opens it.polimi.ingsw.network.messages to com.google.gson;
    opens it.polimi.ingsw.network.messages.clientMessages to com.google.gson;
    opens it.polimi.ingsw.network.messages.serverMessages to com.google.gson;
    opens it.polimi.ingsw.network.server to com.google.gson;
    opens it.polimi.ingsw.network.client.clientModel to com.google.gson;
}