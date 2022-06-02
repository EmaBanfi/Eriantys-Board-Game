package it.polimi.ingsw.network.client.gui.controllers;


import it.polimi.ingsw.network.client.gui.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NicknameController {

    @FXML
    private Label message = new Label();
    @FXML
    private TextField nicknameLabel;
    private Stage stage;
    private GUI gui;

    public NicknameController() {
    }

    public NicknameController(Stage stage) {
        this.stage = stage;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void setStage(Stage stage) {
        System.out.println(stage);
        this.stage = stage;
    }

    @FXML
    public void initialize(String message){
        this.message.setText(message);
    }

    public void initScene(String message) {
        System.out.println("qua");
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(GUI.class.getResource("/fxml/nickname.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initialize(message);

        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }

    @FXML
    protected void sendNickname() {
        System.out.println(nicknameLabel.getText());
    }
}
