package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.ValueToUpdate;
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
    private Button activateCC;
    @FXML
    private Button sendButton;
    private int movements;
    private ArrayList<StudentColor> movementsHToD;

    @Override
    public void update() {
        movementsHToD = new ArrayList<>();

        studentsToMove.setText("You have to move " + getGui().getViewController().getAvailableStudentsMovements() + " students.");

        movements = getGui().getViewController().getAvailableStudentsMovements();

        updateColorsFromHall(studentToMove0);
        studentToMove0.setValue(studentToMove0.getItems().get(0));

        if (movements > 1) {
            hBox1.setVisible(true);

            if (movements > 2) {
                hBox2.setVisible(true);

                if (movements > 3)
                    hBox3.setVisible(true);
            }
        }

        if (getGui().getViewController().getMode().equals("expert")) {
            activateCC.setVisible(true);

            activateCC.setDisable(getGui().getUsedCC());
        }
    }

    @FXML
    public void onActivateCCButton() {
        getGui().getSceneManager("AskActivateCC.fxml").getController().update();
        getGui().updateSceneOnStage("AskActivateCC.fxml");
    }

    @FXML
    public void onSelectButton0() {
        if (activateCC.isDisable())
            activateCC.setDisable(true);

        applyChanges(StudentColor.getStudentFromString(studentToMove0.getValue()));

        hBox0.setDisable(true);

        if (movements > 1) {
            updateColorsFromHall(studentToMove1);
            studentToMove1.setValue(studentToMove1.getItems().get(0));
        }

        if (movements == 1)
            sendButton.setDisable(false);
        else
            hBox1.setDisable(false);
    }

    @FXML
    public void onSelectButton1() {
        applyChanges(StudentColor.getStudentFromString(studentToMove1.getValue()));

        hBox1.setDisable(true);

        if (movements > 2) {
            updateColorsFromHall(studentToMove2);
            studentToMove2.setValue(studentToMove2.getItems().get(0));
        }

        if (movements == 2)
            sendButton.setDisable(false);
        else
            hBox2.setDisable(false);
    }

    @FXML
    public void onSelectButton2() {
        applyChanges(StudentColor.getStudentFromString(studentToMove2.getValue()));

        hBox2.setDisable(true);

        if (movements > 3) {
            updateColorsFromHall(studentToMove3);
            studentToMove3.setValue(studentToMove3.getItems().get(0));
        }

        if (movements == 3)
            sendButton.setDisable(false);
        else
            hBox3.setDisable(false);
    }

    @FXML
    public void onSelectButton3() {
        hBox3.setDisable(true);

        sendButton.setDisable(false);
    }

    @FXML
    public void onSendButton() {
        if (movements == 4)
            applyChanges(StudentColor.getStudentFromString(studentToMove3.getValue()));

        getGui().getViewController().resetAvailableStudentsMovements();

        cmStudentsMovementsHToD message = new cmStudentsMovementsHToD(movementsHToD);
        getGui().getClient().send(getGui().getGson().toJson(message, cmStudentsMovementsHToD.class));

        reset();
    }

    private void applyChanges(StudentColor color) {
        movementsHToD.add(color);

        getGui().getViewController().getMainPlayer().removeFromHall(color);
        getGui().getViewController().getMainPlayer().addToDiningHall(color);

        getGui().updateGameBoard(ValueToUpdate.HALL);
        getGui().updateGameBoard(ValueToUpdate.DINING);
    }

    private void reset() {
        hBox0.setDisable(false);

        sendButton.setDisable(true);

        hBox1.setVisible(false);
        hBox2.setVisible(false);
        hBox3.setVisible(false);

        studentToMove0.getItems().clear();
        studentToMove1.getItems().clear();
        studentToMove2.getItems().clear();
        studentToMove3.getItems().clear();
    }
}
