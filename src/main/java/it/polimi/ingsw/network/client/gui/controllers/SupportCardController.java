package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.gui.ValueToUpdate;
import it.polimi.ingsw.network.messages.clientMessages.cmStudentsMovementsHToI;
import it.polimi.ingsw.network.messages.clientMessages.cmSupportCard;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SupportCardController extends GenericController {

    @FXML
    private ImageView sc1;
    @FXML
    private ImageView sc3;
    @FXML
    private ImageView sc2;
    @FXML
    private ImageView sc5;
    @FXML
    private ImageView sc4;
    @FXML
    private ImageView sc7;
    @FXML
    private ImageView sc6;
    @FXML
    private ImageView sc9;
    @FXML
    private ImageView sc8;
    @FXML
    private ImageView sc10;
    private final ColorAdjust monochrome = new ColorAdjust();

    /**
     * Updates the "setSupportCard.fxml" scene, removing the support cards that are not available anymore.
     */
    @Override
    public void update(){
        monochrome.setSaturation(-1);

        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(1)))
            disablePlusGray(sc1);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(2)))
            disablePlusGray(sc2);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(3)))
            disablePlusGray(sc3);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(4)))
            disablePlusGray(sc4);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(5)))
            disablePlusGray(sc5);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(6)))
            disablePlusGray(sc6);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(7)))
            disablePlusGray(sc7);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(8)))
            disablePlusGray(sc8);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(9)))
            disablePlusGray(sc9);
        if(!getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(10)))
            disablePlusGray(sc10);
    }

    private void disablePlusGray(ImageView sc) {
        sc.setDisable(true);
        sc.setEffect(monochrome);
    }

    @FXML
    void onSC1(MouseEvent event) {
        disablePlusGray(sc1);

        sendSC(1);
    }

    @FXML
    void onSC2(MouseEvent event) {
        disablePlusGray(sc2);

        sendSC(2);
    }

    @FXML
    void onSC3(MouseEvent event) {
        disablePlusGray(sc3);

        sendSC(3);
    }

    @FXML
    void onSC4(MouseEvent event) {
        disablePlusGray(sc4);

        sendSC(4);
    }

    @FXML
    void onSC5(MouseEvent event) {
        disablePlusGray(sc5);

        sendSC(5);
    }

    @FXML
    void onSC6(MouseEvent event) {
        disablePlusGray(sc6);

        sendSC(6);
    }

    @FXML
    void onSC7(MouseEvent event) {
        disablePlusGray(sc7);

        sendSC(7);
    }

    @FXML
    void onSC8(MouseEvent event) {
        disablePlusGray(sc8);

        sendSC(8);
    }

    @FXML
    void onSC9(MouseEvent event) {
        disablePlusGray(sc9);

        sendSC(9);
    }

    @FXML
    void onSC10(MouseEvent event) {
        disablePlusGray(sc10);

        sendSC(10);
    }

    private void sendSC(int id) {
        cmSupportCard message = new cmSupportCard(id);
        getGui().getClient().send(getGui().getGson().toJson(message, cmSupportCard.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }

    private void reset() {
        monochrome.setSaturation(0);

        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(1)))
            enablePlusGray(sc1);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(2)))
            enablePlusGray(sc2);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(3)))
            enablePlusGray(sc3);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(4)))
            enablePlusGray(sc4);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(5)))
            enablePlusGray(sc5);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(6)))
            enablePlusGray(sc6);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(7)))
            enablePlusGray(sc7);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(8)))
            enablePlusGray(sc8);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(9)))
            enablePlusGray(sc9);
        if(getGui().getViewController().getMainPlayer().getSupportCards().contains(getGui().getViewController().getSupportCardByID(10)))
            enablePlusGray(sc10);
    }

    private void enablePlusGray(ImageView sc) {
        sc.setDisable(false);
        sc.setEffect(monochrome);
    }
}
