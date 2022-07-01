package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.client.gui.ValueToUpdate;
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

    public void update(String player, ValueToUpdate value){}

    public void initialise(){}

    protected void updateColorsFromHall(ChoiceBox<String> box) {
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

    protected void updateColorsFromDiningHall(ChoiceBox<String> box) {
        if (getGui().getViewController().getMainPlayer().getDiningHall().contains(StudentColor.BLUE))
            box.getItems().add("Blue");

        if (getGui().getViewController().getMainPlayer().getDiningHall().contains(StudentColor.PURPLE))
            box.getItems().add("Purple");

        if (getGui().getViewController().getMainPlayer().getDiningHall().contains(StudentColor.YELLOW))
            box.getItems().add("Yellow");

        if (getGui().getViewController().getMainPlayer().getDiningHall().contains(StudentColor.RED))
            box.getItems().add("Red");

        if (getGui().getViewController().getMainPlayer().getDiningHall().contains(StudentColor.GREEN))
            box.getItems().add("Green");
    }

    protected void updateColorsFromCC(int cardId, ChoiceBox<String> box) {
        if (getGui().getCharacterCardById(cardId).getStudentsOnCard().contains(StudentColor.BLUE))
            box.getItems().add("Blue");

        if (getGui().getCharacterCardById(cardId).getStudentsOnCard().contains(StudentColor.PURPLE))
            box.getItems().add("Purple");

        if (getGui().getCharacterCardById(cardId).getStudentsOnCard().contains(StudentColor.YELLOW))
            box.getItems().add("Yellow");

        if (getGui().getCharacterCardById(cardId).getStudentsOnCard().contains(StudentColor.RED))
            box.getItems().add("Red");

        if (getGui().getCharacterCardById(cardId).getStudentsOnCard().contains(StudentColor.GREEN))
            box.getItems().add("Green");
    }

    protected void updateControllerAfterCC() {
        switch (getGui().getViewController().getResumeFrom()) {
            case CHOOSE_STUDENTS_TO_DINING_HALL -> getGui().getSceneManager("MoveHToI.fxml").getController().update();
            case CHOOSE_MOTHER_MOVEMENTS -> getGui().getSceneManager("MoveHToD.fxml").getController().update();
            case CHOOSE_CLOUDS -> getGui().getSceneManager("MoveMother.fxml").getController().update();
            case CHOOSE_SUPPORT_CARD -> getGui().getSceneManager("ChooseCloud.fxml").getController().update();
        }
    }
}
