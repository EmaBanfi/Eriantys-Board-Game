package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;

public abstract class GenericController {

    private GUI gui;

    public void setGui(GUI gui){
        this.gui = gui;
    }

    void update(Integer value){}

    void update(String value){}

    public  GUI getGui(){
        return gui;
    }
}
