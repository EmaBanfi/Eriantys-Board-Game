package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG1;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CCG1Controller extends GenericController{

    @FXML
    private HBox hBoxIsland;
    @FXML
    private ChoiceBox<String> studentsOnCard;
    @FXML
    private ChoiceBox<Integer> availableIslands;
    @FXML
    private Button sendButton;
    private StudentColor student;
    private int island;

    /**
     * Activation of "onSendButton" in the "CCG1.fxml" scene; sends a "cmCCG1" message to the server.
     */
    @FXML
    public void onSendButton(){
        cmCCG1 message = new cmCCG1(island, student);
        getGui().getClient().send(new Gson().toJson(message, cmCCG1.class));

        getGui().getCharacterCardById(1).getStudentsOnCard().remove(student);

        ArrayList<StudentColor> s = new ArrayList<>();
        s.add(student);
        getGui().getViewController().getAvailableIslands().get(island -1).addStudents(s);

        getGui().setUsedCC();
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Activation of "onSelectButtonStudent" in the "CCG1.fxml" scene; sets the student chosen by the player.
     */
    @FXML
    public void onSelectButtonStudent(){
        student = StudentColor.getStudentFromString(studentsOnCard.getValue());
        hBoxIsland.setVisible(true);
    }

    /**
     * Activation of "onSelectButtonIsland" in the "CCG1.fxml" scene; sets the island chosen by the player.
     */
    @FXML
    public void onSelectButtonIsland(){
        island = availableIslands.getValue();
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
     * Updates the "CCG1.fxml" scene, adding the students on the card and the available islands to the choice boxes.
     */
    @Override
    public void update() {
        updateColorsFromCC(1, studentsOnCard);

        int maxNumIslands = getGui().getViewController().getAvailableIslands().size();

        for (int i = 1; i <= maxNumIslands; i++) {
            availableIslands.getItems().add(i);
        }
    }

}
