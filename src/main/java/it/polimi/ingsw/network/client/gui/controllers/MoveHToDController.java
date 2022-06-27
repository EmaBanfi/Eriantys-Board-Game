package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmStudentsMovementsHToD;
import it.polimi.ingsw.network.server.model.StudentColor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class MoveHToDController extends GenericController {

    @FXML
    private Label studentsToMove;
    @FXML
    private HBox hBox0;
    @FXML
    private ChoiceBox<String> studentToMove0;
    @FXML
    private Button selectButton0;
    @FXML
    private HBox hBox1;
    @FXML
    private ChoiceBox<String> studentToMove1;
    @FXML
    private Button selectButton1;
    @FXML
    private HBox hBox2;
    @FXML
    private ChoiceBox<String> studentToMove2;
    @FXML
    private Button selectButton2;
    @FXML
    private HBox hBox3;
    @FXML
    private ChoiceBox<String> studentToMove3;
    @FXML
    private Button selectButton3;
    @FXML
    private Button sendButton;
    private int movements;
    private ArrayList<StudentColor> movementsHToD;

    @Override
    public void update() {
        movementsHToD = new ArrayList<>();

        studentsToMove.setText("You have to move " + getGui().getViewController().getAvailableStudentsMovements() + " students.");

        movements = getGui().getViewController().getAvailableStudentsMovements();

        updateColors(studentToMove0);
        studentToMove0.setValue(studentToMove0.getItems().get(0));
        hBox0.setVisible(true);

        if (movements == 1)
            sendButton.setVisible(true);
        else
            selectButton0.setVisible(true);
    }

    @FXML
    public void onSelectButton0() {
        applyChanges(StudentColor.getStudentFromString(studentToMove0.getValue()));

        hBox0.setVisible(false);

        if (movements > 1) {
            updateColors(studentToMove1);
            studentToMove1.setValue(studentToMove1.getItems().get(0));
            hBox1.setVisible(true);

            selectButton1.setVisible(true);
        }
        else
            sendButton.setVisible(true);
    }

    @FXML
    public void onSelectButton1() {
        applyChanges(StudentColor.getStudentFromString(studentToMove1.getValue()));

        hBox1.setVisible(false);

        if (movements > 2) {
            updateColors(studentToMove2);
            studentToMove2.setValue(studentToMove2.getItems().get(0));
            hBox2.setVisible(true);

            selectButton2.setVisible(true);
        }
        else
            sendButton.setVisible(true);
    }

    @FXML
    public void onSelectButton2() {
        applyChanges(StudentColor.getStudentFromString(studentToMove2.getValue()));

        hBox2.setVisible(false);

        if (movements > 3) {
            updateColors(studentToMove3);
            studentToMove3.setValue(studentToMove3.getItems().get(0));
            hBox3.setVisible(true);

            selectButton3.setVisible(true);
        }
        else
            sendButton.setVisible(true);
    }

    @FXML
    public void onSelectButton3() {
        hBox3.setVisible(false);

        sendButton.setVisible(true);
    }

    @FXML
    public void onSendButton() {
        if (movements == 4)
            applyChanges(StudentColor.getStudentFromString(studentToMove3.getValue()));

        getGui().getViewController().resetAvailableStudentsMovements();

        cmStudentsMovementsHToD message = new cmStudentsMovementsHToD(movementsHToD);
        getGui().getClient().send(getGui().getGson().toJson(message, cmStudentsMovementsHToD.class));
    }

    private void applyChanges(StudentColor color) {
        movementsHToD.add(color);

        getGui().getViewController().getMainPlayer().removeFromHall(color);
        getGui().getViewController().getMainPlayer().addToDiningHall(color);
    }
}
