package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.clientModel.Phase;
import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmSetGameStatus;
import it.polimi.ingsw.network.messages.clientMessages.cmStudentsMovementsHToI;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveHToIController extends GenericController {

    @FXML
    private RadioButton yesButton;
    @FXML
    private ChoiceBox numOfStudents;
    @FXML
    private HBox hBox0;
    @FXML
    private ChoiceBox studentsToMove0;
    @FXML
    private ChoiceBox chosenIsland0;
    @FXML
    private Button selectButton0;
    @FXML
    private HBox hBox1;
    @FXML
    private ChoiceBox studentsToMove1;
    @FXML
    private ChoiceBox chosenIsland1;
    @FXML
    private Button selectButton1;
    @FXML
    private HBox hBox2;
    @FXML
    private ChoiceBox studentsToMove2;
    @FXML
    private ChoiceBox chosenIsland2;
    @FXML
    private Button selectButton2;
    @FXML
    private HBox hBox3;
    @FXML
    private ChoiceBox studentsToMove3;
    @FXML
    private ChoiceBox chosenIsland3;
    @FXML
    private Button sendButton;
    private int numOfChoice;
    private HashMap<Integer, ArrayList<StudentColor>> movementsHtoI;


    @FXML
    public void onAskButton() {
        if (yesButton.isSelected()) {
            if (getGui().getViewController().getAvailableStudentsMovements() == 3)
                numOfStudents.getItems().remove(3);
            movementsHtoI = new HashMap<>();
            updateIslands();
            getGui().updateSceneOnStage("MoveHToI.fxml");
        }
        else
            getGui().getClient().send(getGui().getGson().toJson(new cmStudentsMovementsHToI(null), cmStudentsMovementsHToI.class));
    }

    @FXML
    public void onSelectButton() {
        numOfChoice = Integer.parseInt(numOfStudents.getValue().toString());

        if (numOfChoice == getGui().getViewController().getNumOfAvailableIslands())
            getGui().getViewController().setResumeFrom(Phase.CHOOSE_MOTHER_MOVEMENTS);

        updateColors(studentsToMove0);
        hBox0.setVisible(true);

        if (numOfChoice == 1)
            sendButton.setVisible(true);
        else
            selectButton0.setVisible(true);
    }

    @FXML
    public void onSelectButton0() {
        applyChanges(Integer.parseInt(chosenIsland0.getItems().toString()), StudentColor.getStudentFromString(studentsToMove0.getItems().toString()), hBox0);

        if (numOfChoice > 1){
            updateColors(studentsToMove1);
            hBox1.setVisible(true);
        }

        if (numOfChoice == 2)
            sendButton.setVisible(true);
        else
            selectButton1.setVisible(true);
    }

    @FXML
    public void onSelectButton1() {
        applyChanges(Integer.parseInt(chosenIsland1.getItems().toString()), StudentColor.getStudentFromString(studentsToMove1.getItems().toString()), hBox1);

        if (numOfChoice > 2){
            updateColors(studentsToMove2);
            hBox2.setVisible(true);
        }

        if (numOfChoice == 2)
            sendButton.setVisible(true);
        else
            selectButton2.setVisible(true);
    }

    @FXML
    public void onSelectButton2() {
        applyChanges(Integer.parseInt(chosenIsland2.getItems().toString()), StudentColor.getStudentFromString(studentsToMove2.getItems().toString()), hBox2);

        if (numOfChoice > 3){
            updateColors(studentsToMove3);
            hBox3.setVisible(true);
        }

        applyChanges(Integer.parseInt(chosenIsland3.getItems().toString()), StudentColor.getStudentFromString(studentsToMove3.getItems().toString()), hBox3);

        sendButton.setVisible(true);
    }

    private void applyChanges(int island, StudentColor color, HBox hBox) {
        updateHashMap(island - 1, color);

        getGui().getViewController().getMainPlayer().removeFromHall(color);
        getGui().getViewController().getIsland(island - 1).addStudent(color);

        hBox.setVisible(false);
    }

    @FXML
    public void onSendButton() {
        cmStudentsMovementsHToI message = new cmStudentsMovementsHToI(movementsHtoI);
        getGui().getClient().send(getGui().getGson().toJson(message, cmStudentsMovementsHToI.class));
    }

    private void updateColors(ChoiceBox box) {
        if (!getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.BLUE))
            box.getItems().remove(0);

        if (!getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.PURPLE))
            box.getItems().remove(1);

        if (!getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.YELLOW))
            box.getItems().remove(2);

        if (!getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.RED))
            box.getItems().remove(3);

        if (!getGui().getViewController().getMainPlayer().getHall().contains(StudentColor.GREEN))
            box.getItems().remove(4);
    }

    private void updateIslands() {
        int numOfIslands = getGui().getViewController().getAvailableIslands().size();

        for (int i = numOfIslands; i < 12; i++) {
            chosenIsland0.getItems().remove(i);
            chosenIsland1.getItems().remove(i);
            chosenIsland2.getItems().remove(i);
            chosenIsland3.getItems().remove(i);
        }
    }

    private void updateHashMap(int island, StudentColor color) {
        movementsHtoI.get(island).add(color);
    }
}
