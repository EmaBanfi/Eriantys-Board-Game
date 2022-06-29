package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.cmDisconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class EndGameController extends GenericController{

    @FXML
    private Label winnersLabel;
    @FXML
    private Label losersLabel;

    /**
     * Activation of the "Quit button" in the EndGame.fxml scene; closes the client sending a "cmDisconnect" message to the server.
     * @param event
     */
    @FXML
    public void quitButton(ActionEvent event){
        Gson gson = new Gson();
        cmDisconnect message = new cmDisconnect();
        String text = gson.toJson(message, cmDisconnect.class);
        getGui().getClient().send(text);
    }

    /**
     * Updates the "EndGame.fxml" scene, adding winners and losers to the labels.
     */
    @Override
    public void update(){
        winnersLabel.setText(makeString(getGui().getWinners()));
        losersLabel.setText(makeString(getGui().getLosers()));
    }

    private String makeString(ArrayList<String> input){
        ArrayList<String> array = new ArrayList<>();
        array.addAll(input);
        int i = 0;
        String str = "";
        while(!array.get(i).isEmpty()){
            if(i == 1){
                str = str + ", ";
            }
            str = str + array.get(i);
            i++;
        }
        return str;
    }
}
