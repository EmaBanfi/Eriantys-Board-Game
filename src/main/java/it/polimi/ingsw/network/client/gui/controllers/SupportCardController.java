package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmSupportCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class SupportCardController extends GenericController{

    private GUI gui;
    @FXML
    private RadioButton SupportCard1;
    @FXML
    private RadioButton SupportCard2;
    @FXML
    private RadioButton SupportCard3;
    @FXML
    private RadioButton SupportCard4;
    @FXML
    private RadioButton SupportCard5;
    @FXML
    private RadioButton SupportCard6;
    @FXML
    private RadioButton SupportCard7;
    @FXML
    private RadioButton SupportCard8;
    @FXML
    private RadioButton SupportCard9;
    @FXML
    private RadioButton SupportCard10;
    private int chosenSupportCard;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void chooseSupportCardButton(ActionEvent actionEvent) {
        if(SupportCard1.isSelected())
            chosenSupportCard = 1;
        else if(SupportCard2.isSelected())
            chosenSupportCard = 2;
        else if(SupportCard3.isSelected())
            chosenSupportCard = 3;
        else if(SupportCard4.isSelected())
            chosenSupportCard = 4;
        else if(SupportCard5.isSelected())
            chosenSupportCard = 5;
        else if(SupportCard6.isSelected())
            chosenSupportCard = 6;
        else if(SupportCard7.isSelected())
            chosenSupportCard = 7;
        else if(SupportCard8.isSelected())
            chosenSupportCard = 8;
        else if(SupportCard9.isSelected())
            chosenSupportCard = 9;
        else
            chosenSupportCard = 10;
        Gson gson = new Gson();
        cmSupportCard message = new cmSupportCard(chosenSupportCard);
        String text = gson.toJson(message, cmSupportCard.class);
        gui.getClient().send(text);
    }

    @Override
    public void update(){
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(1)))
            SupportCard1.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(2)))
            SupportCard2.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(3)))
            SupportCard3.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(4)))
            SupportCard4.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(5)))
            SupportCard5.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(6)))
            SupportCard6.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(7)))
            SupportCard7.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(8)))
            SupportCard8.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(9)))
            SupportCard9.setVisible(false);
        if(!gui.getViewController().getMainPlayer().getSupportCards().contains(gui.getViewController().getSupportCardByID(10)))
            SupportCard10.setVisible(false);
    }
}
