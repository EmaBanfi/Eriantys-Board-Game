package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.clientModel.Phase;
import it.polimi.ingsw.network.client.gui.ValueToUpdate;
import it.polimi.ingsw.network.messages.clientMessages.cmStudentsMovementsHToI;
import it.polimi.ingsw.network.server.model.StudentColor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveHToIController extends GenericController {

    @FXML
    private HBox hBoxMain;
    @FXML
    private ChoiceBox<Integer> numOfStudents;
    @FXML
    private HBox hBox0;
    @FXML
    private ChoiceBox<String> studentToMove0;
    @FXML
    private ChoiceBox<Integer> chosenIsland0;
    @FXML
    private Button selectButton0;
    @FXML
    private HBox hBox1;
    @FXML
    private ChoiceBox<String> studentToMove1;
    @FXML
    private ChoiceBox<Integer> chosenIsland1;
    @FXML
    private Button selectButton1;
    @FXML
    private HBox hBox2;
    @FXML
    private ChoiceBox<String> studentToMove2;
    @FXML
    private ChoiceBox<Integer> chosenIsland2;
    @FXML
    private Button selectButton2;
    @FXML
    private HBox hBox3;
    @FXML
    private ChoiceBox<String> studentToMove3;
    @FXML
    private ChoiceBox<Integer> chosenIsland3;
    @FXML
    private Button selectButton3;
    @FXML
    private Button sendButton;
    private HashMap<Integer, ArrayList<StudentColor>> movementsHtoI;
    @FXML
    private Button activateCC;

    @Override
    public void update() {
        numOfStudents.getItems().add(1);
        numOfStudents.getItems().add(2);
        numOfStudents.getItems().add(3);
        if (getGui().getViewController().getAvailableStudentsMovements() == 4)
            numOfStudents.getItems().add(4);
        movementsHtoI = new HashMap<>();
        updateIslands();

        if (getGui().getViewController().getMode().equals("expert")) {
            activateCC.setVisible(true);

            activateCC.setDisable(getGui().getUsedCC());
        }
    }

    /**
     * Activation of "onSHowIslandButton" in the "MoveHToI.fxml" scene; shows the islands available on the GameBoard.
     */
    @FXML
    public void onShowIslandsButton() {
        Stage stage = new Stage();

        stage.setMinWidth(1200);
        stage.setMinHeight(800);

        getGui().getSceneManager("ShowIsland.fxml").getController().update();
        stage.setScene(getGui().getSceneManager("ShowIsland.fxml").getScene());

        stage.show();
    }

    @FXML
    public void onActivateCCButton() {
        getGui().getSceneManager("AskActivateCC.fxml").getController().update();
        getGui().updateSceneOnStage("AskActivateCC.fxml");
    }

    /**
     * Activation of "onSelectButton" in the "MoveHToI.fxml" scene; selects the number of students that the player wants to move and then shows the available students.
     */
    @FXML
    public void onSelectButton() {
        if (activateCC.isDisable())
            activateCC.setDisable(true);

        if (getGui().getViewController().getAvailableStudentsMovements() == numOfStudents.getValue())
            getGui().getViewController().setResumeFrom(Phase.CHOOSE_MOTHER_MOVEMENTS);

        getGui().getViewController().setAvailableStudentsMovements(getGui().getViewController().getAvailableStudentsMovements() - numOfStudents.getValue());

        if (getGui().getViewController().getAvailableStudentsMovements() == 0)
            getGui().getViewController().setResumeFrom(Phase.CHOOSE_MOTHER_MOVEMENTS);

        updateColorsFromHall(studentToMove0);
        studentToMove0.setValue(studentToMove0.getItems().get(0));
        chosenIsland0.setValue(chosenIsland0.getItems().get(0));
        hBoxMain.setVisible(false);
        hBox0.setVisible(true);

        if (numOfStudents.getValue() > 1) {
            hBox1.setVisible(true);

            if (numOfStudents.getValue() > 2) {
                hBox2.setVisible(true);

                if (numOfStudents.getValue() > 3)
                    hBox3.setVisible(true);
            }
        }

        hBox0.setDisable(false);
    }


    @FXML
    public void onSelectButton0() {
        applyChanges(chosenIsland0.getValue(), StudentColor.getStudentFromString(studentToMove0.getValue()));

        hBox0.setDisable(true);

        if (numOfStudents.getValue() > 1) {
            updateColorsFromHall(studentToMove1);
            studentToMove1.setValue(studentToMove1.getItems().get(0));
            chosenIsland1.setValue(chosenIsland1.getItems().get(0));
        }

        if (numOfStudents.getValue() == 1)
            sendButton.setDisable(false);
        else
            hBox1.setDisable(false);
    }

    @FXML
    public void onSelectButton1() {
        applyChanges(chosenIsland1.getValue(), StudentColor.getStudentFromString(studentToMove1.getValue()));

        hBox1.setDisable(true);

        if (numOfStudents.getValue() > 2) {
            updateColorsFromHall(studentToMove2);
            studentToMove2.setValue(studentToMove2.getItems().get(0));
            chosenIsland2.setValue(chosenIsland2.getItems().get(0));
        }

        if (numOfStudents.getValue() == 2)
            sendButton.setDisable(false);
        else
            hBox2.setDisable(false);
    }

    @FXML
    public void onSelectButton2() {
        applyChanges(chosenIsland2.getValue(), StudentColor.getStudentFromString(studentToMove2.getValue()));

        hBox2.setDisable(true);

        if (numOfStudents.getValue() > 3) {
            updateColorsFromHall(studentToMove3);
            studentToMove3.setValue(studentToMove3.getItems().get(0));
            chosenIsland3.setValue(chosenIsland3.getItems().get(0));
        }

        if (numOfStudents.getValue() == 3)
            sendButton.setDisable(false);
        else
            hBox3.setDisable(false);
    }

    @FXML
    public void onSelectButton3() {
        hBox3.setDisable(true);

        sendButton.setDisable(false);
    }

    private void applyChanges(int island, StudentColor color) {
        if (!movementsHtoI.containsKey(island))
            movementsHtoI.put(island, new ArrayList<>());
        movementsHtoI.get(island).add(color);

        getGui().getViewController().getMainPlayer().removeFromHall(color);
        getGui().getViewController().getIsland(island - 1).addStudent(color);

        getGui().updateGameBoard(ValueToUpdate.HALL);
    }

    @FXML
    public void onSendButton() {
        if (numOfStudents.getValue() == 4)
            applyChanges(chosenIsland3.getValue(), StudentColor.getStudentFromString(studentToMove3.getValue()));

        cmStudentsMovementsHToI message = new cmStudentsMovementsHToI(movementsHtoI);
        getGui().getClient().send(getGui().getGson().toJson(message, cmStudentsMovementsHToI.class));

        reset();
    }

    private void updateIslands() {
        int maxNumIslands = getGui().getViewController().getAvailableIslands().size();

        for (int i = 1; i <= maxNumIslands; i++) {
            chosenIsland0.getItems().add(i);
            chosenIsland1.getItems().add(i);
            chosenIsland2.getItems().add(i);
            chosenIsland3.getItems().add(i);
        }
    }

    private void reset() {
        numOfStudents.getItems().clear();

        studentToMove0.getItems().clear();
        studentToMove1.getItems().clear();
        studentToMove2.getItems().clear();
        studentToMove3.getItems().clear();

        chosenIsland0.getItems().clear();
        chosenIsland1.getItems().clear();
        chosenIsland2.getItems().clear();
        chosenIsland3.getItems().clear();

        sendButton.setDisable(true);

        hBox0.setVisible(false);
        hBox1.setVisible(false);
        hBox2.setVisible(false);
        hBox3.setVisible(false);

        hBoxMain.setVisible(true);
    }
}
