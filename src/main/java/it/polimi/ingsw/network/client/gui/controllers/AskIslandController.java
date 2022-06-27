package it.polimi.ingsw.network.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class AskIslandController extends GenericController{

    @FXML
    ChoiceBox islands;

    Integer range;
    int chosenIsland = -1;

    @Override
    public void setInput(Integer value) {
        range = value;

    }

    @FXML
    public void confirmIslandButtonClick(){
        chosenIsland = Integer.parseInt(islands.getValue().toString());
    }

    @FXML
    public void showIslandButtonClick(){
        Stage stage = new Stage();
        stage.setMinWidth(1200);
        stage.setMinHeight(800);
        getGui().getSceneManager("ShowIslands.fxml").getController().setInput(range);
        stage.setScene(getGui().getSceneManager("ShowIslands.fxml").getScene());
        stage.show();
    }

    @Override
    public Integer getOutputAsInteger(){
        while (chosenIsland==-1){
        }
        return chosenIsland;
    }
}
