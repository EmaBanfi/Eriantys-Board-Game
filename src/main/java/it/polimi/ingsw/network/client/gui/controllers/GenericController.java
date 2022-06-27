package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.scene.control.ChoiceBox;

public abstract class GenericController {

    private GUI gui;

    public void setGui(GUI gui){
        this.gui = gui;
    }

    public void setInput(Integer value){}

    public void setInput(String value){}

    public Integer getOutputAsInteger(){
        return null;
    }

    public String getOutputAsString(){
        return null;
    }

    public void update(){}

    public  GUI getGui(){
        return gui;
    }

    protected void updateColors(ChoiceBox<String> box) {
        if (getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.BLUE))
            box.getItems().add("Blue");

        if (getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.PURPLE))
            box.getItems().add("Purple");

        if (getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.YELLOW))
            box.getItems().add("Yellow");

        if (getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.RED))
            box.getItems().add("Red");

        if (getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.GREEN))
            box.getItems().add("Green");
    }
}
