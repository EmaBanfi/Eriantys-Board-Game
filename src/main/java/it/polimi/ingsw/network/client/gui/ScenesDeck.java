package it.polimi.ingsw.network.client.gui;

import it.polimi.ingsw.network.client.gui.controllers.GenericController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScenesDeck {
    private final ArrayList<SceneManager> scenes;

    private final String setIp = "SetIp.fxml";
    private final String askNickname = "SetNickname.fxml";
    private final String setGameStatus = "SetGameStatus.fxml";
    private final String showIslands = "ShowIslands.fxml";

    public ScenesDeck(GUI gui){
        scenes = new ArrayList<>();
        ArrayList<String> fxmlFiles = new ArrayList<>(Arrays.asList(setIp, askNickname, setGameStatus, showIslands));
        GenericController controller;
        Scene scene = null;
        for (String file : fxmlFiles) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + file));
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            controller = loader.getController();
            controller.setGui(gui);
            scenes.add(new SceneManager(file, scene,controller));
        }
    }

    public  SceneManager getSceneManager(String sceneName){
        for(SceneManager manager: scenes){
            if(manager.getSceneName().equals(sceneName))
                return manager;
        }
        return  null;
    }

}
