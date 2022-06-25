package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;

public abstract class GenericController {

    private GUI gui;

    public void setGui(GUI gui){
        this.gui = gui;
    }

    void setInput(Integer value){}

    void setInput(String value){}

    Integer getOutputAsInteger(){
        return  null;
    };

    String getOutputAsString(){
        return null;
    }

    public  GUI getGui(){
        return gui;
    }
}
