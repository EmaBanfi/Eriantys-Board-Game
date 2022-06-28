package it.polimi.ingsw.network.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.util.ArrayList;

public class NotifyController extends GenericController {

    @FXML
    private Label message;
    @FXML
    private Button next;
    @FXML
    private Button close;

    private ArrayList<String> notifies = new ArrayList<>();

    @Override
    public void initialise() {
        message.setText(getGui().getNotifies().get(0));

        if (notifies.size() > 1)
            next.setVisible(true);
        else
            close.setVisible(true);
    }

    @FXML
    public void onNextButton() {
        next.setVisible(false);

        message.setText(getGui().getNotifies().get(1));

        update();
    }

    @FXML
    public void onCloseButton() {
        close.setVisible(false);

        getGui().closeNotifyStage();
    }
}
