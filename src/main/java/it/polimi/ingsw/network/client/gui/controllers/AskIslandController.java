package it.polimi.ingsw.network.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class AskIslandController extends GenericController{

    @FXML
    private ChoiceBox islands;

    private Integer range;
    private int chosenIsland = -1;

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
        getGui().getSceneManager("ShowIsland.fxml").getController().update();
        stage.setScene(getGui().getSceneManager("ShowIsland.fxml").getScene());
        stage.show();
    }

    @Override
    public Integer getOutputAsInteger(){
        while (chosenIsland==-1){
        }
        return chosenIsland;
    }
}
