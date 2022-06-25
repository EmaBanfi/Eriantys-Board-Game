package it.polimi.ingsw.network.client.gui;

import it.polimi.ingsw.network.client.gui.controllers.GenericController;
import javafx.scene.Scene;

public record SceneManager(String sceneName, Scene scene,
                           GenericController controller) {


    public String getSceneName() {
        return sceneName;
    }

    public Scene getScene() {
        return scene;
    }

    public GenericController getController() {
        return controller;
    }
}
