package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;

import it.polimi.ingsw.network.client.clientModel.Phase;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG1;
import it.polimi.ingsw.network.server.model.DiningHall;
import it.polimi.ingsw.network.server.model.StudentColor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CCG1Controller extends GenericController{

    @FXML
    private HBox studentHBox;

    @FXML
    private ChoiceBox<String> students;

    @FXML
    private HBox islandHBox;

    @FXML
    private ChoiceBox<Integer> islands;

    @FXML
    private Button sendButton;

    /**
     * Updates the "CCG1.fxml" scene, adding the students on the card and the available islands to the choice boxes.
     */
    @Override
    public void update() {
        updateColorsFromCC(1, students);

        int maxNumIslands = getGui().getViewController().getAvailableIslands().size();

        for (int i = 1; i <= maxNumIslands; i++) {
            islands.getItems().add(i);
        }

        islands.setValue(islands.getItems().get(0));
    }

    /**
     * Activation of "onSHowIslandButton" in the "MoveHToI.fxml" scene; shows the islands available on the GameBoard.
     */
    @FXML
    public void onShowIslandsButton() {
        Stage stage = new Stage();

        stage.setMinWidth(1200);
        stage.setMinHeight(800);

        getGui().getSceneManager("ShowIslands.fxml").getController().setInput((Integer) null);
        stage.setScene(getGui().getSceneManager("ShowIslands.fxml").getScene());

        stage.show();
    }

    /**
     * Activation of "onStudentButton" in the "CCG1.fxml" scene; sets the student chosen by the player.
     */
    @FXML
    void onStudentButton(ActionEvent event) {
        studentHBox.setDisable(true);
        islandHBox.setDisable(false);
    }

    /**
     * Activation of "onIslandButton" in the "CCG1.fxml" scene; sets the island chosen by the player.
     */
    @FXML
    void onIslandButton(ActionEvent event) {
        islandHBox.setDisable(true);
        sendButton.setDisable(false);
    }

    /**
     * Activation of "onSendButton" in the "CCG1.fxml" scene; sends a "cmCCG1" message to the server.
     */
    @FXML
    void onSendButton(ActionEvent event) {
        StudentColor student = StudentColor.getStudentFromString(students.getValue());
        int island = islands.getValue() - 1;

        cmCCG1 message = new cmCCG1(island, student);
        getGui().getClient().send(new Gson().toJson(message, cmCCG1.class));

        getGui().getCharacterCardById(1).getStudentsOnCard().remove(student);


        getGui().getViewController().getAvailableIslands().get(island - 1).addStudent(student);

        reset();

        updateControllerAfterCC();

        getGui().setUsedCC();
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }

    private void reset() {
        sendButton.setDisable(true);

        students.getItems().clear();
        islands.getItems().clear();
    }
}
