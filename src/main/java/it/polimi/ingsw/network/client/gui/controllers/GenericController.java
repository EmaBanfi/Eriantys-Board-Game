package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;

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
}
