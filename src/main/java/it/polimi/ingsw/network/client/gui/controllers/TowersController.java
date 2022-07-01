package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmTower;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TowersController extends GenericController{

    @FXML
    private ImageView gray;
    @FXML
    private ImageView white;
    @FXML
    private ImageView black;
    private final ColorAdjust monochrome = new ColorAdjust();


    /**
     * updates the "SetTower.fxml" scene, unblocking tower colors the already chosen tower colors.
     */
    @Override
    public void update() {
        monochrome.setBrightness(1);

        if(!(getGui().getViewController().getNumOfPlayers() == 3 && getGui().getViewController().getAvailableTowers().contains("GRAY")))
            disablePlusGray(gray);
        if (!getGui().getViewController().getAvailableTowers().contains("WHITE"))
            disablePlusGray(white);
        if (!getGui().getViewController().getAvailableTowers().contains("BLACK"))
            disablePlusGray(black);
    }

    private void disablePlusGray(ImageView tower) {
        tower.setDisable(true);
        tower.setEffect(monochrome);
    }

    /**
     * Sends a "cmTower" message to the server with the chosen tower (black).
     */
    @FXML
    void onBlackImage(MouseEvent event) {
        sendMessage("BLACK");
    }

    /**
     * Sends a "cmTower" message to the server with the chosen tower (white).
     */
    @FXML
    void onWhiteImage(MouseEvent event) {
        sendMessage("WHITE");
    }

    /**
     * Sends a "cmTower" message to the server with the chosen tower (gray).
     */
    @FXML
    void onGrayImage(MouseEvent event) {
        sendMessage("GRAY");
    }

    private void sendMessage(String string) {
        getGui().getViewController().getMainPlayer().setTower(string);

        cmTower message = new cmTower(string);
        getGui().getClient().send(getGui().getGson().toJson(message, cmTower.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }
}
