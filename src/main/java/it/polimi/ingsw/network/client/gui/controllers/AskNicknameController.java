package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AskNicknameController {

    public void initScene(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(GUI.class.getResource("/fxml/AskNickname.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
