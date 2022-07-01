package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG4;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CCG4Controller extends GenericController{

    @FXML
    private Label effectTitle;
    @FXML
    private ChoiceBox<String> availableStudents;
    @FXML
    private Button sendButton;
    private int cardId;
    private final ArrayList<StudentColor> studentsOnCard11 = new ArrayList<>();

    /**
     * Sets the ID of the card used.
     * @param value cardID
     */
    @Override
    public void setInput(Integer value){
        cardId = value;
    }

    /**
     * Activation of "onSendButton" in the "CCG$.fxml" scene; sends a "cmCCG4" to the server and if the cardID = 11 
     */
    @FXML
    public void onSendButton(){
        StudentColor chosenStudent;

        chosenStudent = StudentColor.getStudentFromString(availableStudents.getValue());
        cmCCG4 message = new cmCCG4(cardId, chosenStudent);
        getGui().getClient().send(new Gson().toJson(message, cmCCG4.class));
        if(cardId == 11) {
            getGui().getCharacterCardById(11).getStudentsOnCard().remove(chosenStudent);
            getGui().getViewController().getMainPlayer().addToDiningHall(chosenStudent);
        }

        reset();

        updateControllerAfterCC();

        getGui().setUsedCC();
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Updates the "CC4.fxml" scene, adding the students in the ChoiceBox.
     */
    @Override
    public void update(){
        if(cardId == 9) {
            effectTitle.setText("Effect of the card 9");
            insertAllColors();
        }
        else if(cardId == 11) {
            effectTitle.setText("Effect of the card 11");
            updateColorsFromCC(cardId, availableStudents);
        }
        else {
            effectTitle.setText("Effect of the card 12");
            insertAllColors();
        }

        availableStudents.setValue(availableStudents.getItems().get(0));
    }

    private void insertAllColors() {
        availableStudents.getItems().add("Blue");
        availableStudents.getItems().add("Red");
        availableStudents.getItems().add("Yellow");
        availableStudents.getItems().add("Purple");
        availableStudents.getItems().add("Green");
    }

    private void reset() {
        availableStudents.getItems().clear();
    }
}
