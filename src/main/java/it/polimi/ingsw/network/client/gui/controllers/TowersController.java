package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmTower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class TowersController extends GenericController{

    private GUI gui;
    private ChoiceBox choiceBox = new ChoiceBox();
    @FXML
    private String chosenTower;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @FXML
    public void towerColorButton(ActionEvent event){
        chosenTower = choiceBox.getValue().toString();
        gui.getViewController().updateTowerColor(chosenTower);
        Gson gson = new Gson();
        cmTower message = new cmTower(chosenTower);
        String text = gson.toJson(message, cmTower.class);
        gui.getClient().send(text);
    }

    @Override
    public void update(){
        if(gui.getViewController().getAvailableTowers().contains("WHITE"))
            choiceBox.getItems().add("WHITE");
        if(gui.getViewController().getAvailableTowers().contains("BLACK"))
            choiceBox.getItems().add("BLACK");
        if(gui.getViewController().getNumOfPlayers() == 3 && gui.getViewController().getAvailableTowers().contains("GRAY"))
            choiceBox.getItems().add("GRAY");
    }

    public String getOutputString(){
        while(chosenTower == null){}
        return chosenTower;
    }
}
