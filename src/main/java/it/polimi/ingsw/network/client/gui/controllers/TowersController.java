package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.gui.ValueToUpdate;
import it.polimi.ingsw.network.messages.clientMessages.cmTower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class TowersController extends GenericController{

    @FXML
    private ChoiceBox<String> setTowerColor;

    /**
     * Activation of the "towerColorButton" in "SetTower.fxml" scene; sends a "cmTower" message to the server with the chosen tower.
     */
    @FXML
    public void towerColorButton(ActionEvent event){
        String chosenTower = setTowerColor.getValue();
        getGui().getViewController().updateTowerColor(chosenTower);
        Gson gson = new Gson();
        cmTower message = new cmTower(chosenTower);
        String text = gson.toJson(message, cmTower.class);
        getGui().getClient().send(text);
    }

    /**
     * updates the "SetTower.fxml" scene, adding the available tower colors to the choice box.
     */
    @Override
    public void update(){
        if(getGui().getViewController().getNumOfPlayers() == 3 && getGui().getViewController().getAvailableTowers().contains("GRAY"))
            setTowerColor.getItems().add("Gray");
        if (getGui().getViewController().getAvailableTowers().contains("WHITE"))
            setTowerColor.getItems().add("White");
        if (getGui().getViewController().getAvailableTowers().contains("BLACK"))
            setTowerColor.getItems().add("Black");

        setTowerColor.setValue(setTowerColor.getItems().get(0));
    }
}
