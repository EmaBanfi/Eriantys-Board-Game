package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;

import it.polimi.ingsw.network.messages.clientMessages.cmCCG6;
import it.polimi.ingsw.network.messages.clientMessages.cmTower;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public class TowersController extends GenericController{

    @FXML
    private Button gray;

    @FXML
    private Button white;

    @FXML
    private VBox vBoxGray;

    @FXML
    private Button black;

    @FXML
    private VBox vBoxWhite;

    @FXML
    private VBox vBoxBlack;

    /**
     * updates the "SetTower.fxml" scene, unblocking tower colors the already chosen tower colors.
     */
    @Override
    public void update(){
        if(getGui().getViewController().getNumOfPlayers() == 3 && getGui().getViewController().getAvailableTowers().contains("GRAY"))
            vBoxGray.setDisable(false);
        if (getGui().getViewController().getAvailableTowers().contains("WHITE"))
            vBoxWhite.setDisable(false);
        if (getGui().getViewController().getAvailableTowers().contains("BLACK"))
            vBoxBlack.setDisable(false);
    }

    /**
     * Sends a "cmTower" message to the server with the chosen tower (black).
     */
    @FXML
    void onBlackButton(ActionEvent event) {
        getGui().getViewController().getMainPlayer().setTower("BLACK");

        cmTower message = new cmTower("BLACK");
        getGui().getClient().send(getGui().getGson().toJson(message, cmTower.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }

    /**
     * Sends a "cmTower" message to the server with the chosen tower (white).
     */
    @FXML
    void onWhiteButton(ActionEvent event) {
        getGui().getViewController().getMainPlayer().setTower("WHITE");

        cmTower message = new cmTower("WHITE");
        getGui().getClient().send(getGui().getGson().toJson(message, cmTower.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }

    /**
     * Sends a "cmTower" message to the server with the chosen tower (gray).
     */
    @FXML
    void onGrayButton(ActionEvent event) {
        getGui().getViewController().getMainPlayer().setTower("GRAY");

        cmTower message = new cmTower("GRAY");
        getGui().getClient().send(getGui().getGson().toJson(message, cmTower.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }
}
