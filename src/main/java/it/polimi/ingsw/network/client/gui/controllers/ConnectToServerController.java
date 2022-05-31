
package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectToServerController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private GUI gui;

    @FXML
    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ConnectToServer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onPlayButtonClick() {
        //gui.createClient();
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
