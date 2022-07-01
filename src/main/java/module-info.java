module it.polimi.ingsw {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports it.polimi.ingsw.network.client.gui;
    exports it.polimi.ingsw.network.client.gui.controllers;
    opens it.polimi.ingsw.network.client.gui.controllers to javafx.fxml;

    requires com.google.gson;

    exports it.polimi.ingsw.network.server.model;

    opens it.polimi.ingsw.network.client to com.google.gson;
    opens it.polimi.ingsw.network.messages to com.google.gson;
    opens it.polimi.ingsw.network.messages.clientMessages to com.google.gson;
    opens it.polimi.ingsw.network.messages.serverMessages to com.google.gson;
    opens it.polimi.ingsw.network.server to com.google.gson;
    opens it.polimi.ingsw.network.client.gui to com.google.gson, javafx.fxml;
    exports it.polimi.ingsw.network.client.clientModel;
    opens it.polimi.ingsw.network.client.clientModel to com.google.gson, javafx.fxml;
    exports it.polimi.ingsw.network.client.gui.controllers.characterCardsControllers;
    opens it.polimi.ingsw.network.client.gui.controllers.characterCardsControllers to javafx.fxml;
    exports it.polimi.ingsw.network.client.gui.GUICharacterCards;
    opens it.polimi.ingsw.network.client.gui.GUICharacterCards to com.google.gson, javafx.fxml;
}