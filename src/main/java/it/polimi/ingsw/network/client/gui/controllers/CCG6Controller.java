package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmCCG6;
import it.polimi.ingsw.network.server.model.StudentColor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CCG6Controller extends GenericController {

    @FXML
    private Label title;
    @FXML
    private ChoiceBox<Integer> numOfStudents;
    @FXML
    private HBox hBox0;
    @FXML
    private Label text00;
    @FXML
    private Label text01;
    @FXML
    private ChoiceBox<String> choice00;
    @FXML
    private ChoiceBox<String> choice01;
    @FXML
    private HBox hBox1;
    @FXML
    private Label text10;
    @FXML
    private Label text11;
    @FXML
    private ChoiceBox<String> choice10;
    @FXML
    private ChoiceBox<String> choice11;
    @FXML
    private HBox hBox2;
    @FXML
    private Label text20;
    @FXML
    private Label text21;
    @FXML
    private ChoiceBox<String> choice20;
    @FXML
    private ChoiceBox<String> choice21;
    @FXML
    private Button sendButton;

    private int cardId;

    @Override
    public void setInput(Integer value) {
        cardId = value;
    }

    @Override
    public void update() {
        numOfStudents.getItems().add(1);
        numOfStudents.getItems().add(2);

        updateColorsFromHall(choice01);

        if (cardId == 7) {
            title.setText("Exchange up to 3 students between the character card 7 and your hall");

            numOfStudents.getItems().add(3);

            updateColorsFromCC(7, choice00);

            text00.setText("Students from card:");
            text10.setText("Students from card:");
            text20.setText("Students from card:");
            text01.setText("Students from your hall:");
            text11.setText("Students from your hall:");
            text21.setText("Students from your hall:");
        }
        else {
            title.setText("Exchange up to 2 students between your dining hall 7 and your hall");

            updateColorsFromDiningHall(choice00);

            text00.setText("Students from your dining hall:");
            text10.setText("Students from your dining hall:");
            text20.setText("Students from your dining hall:");
            text01.setText("Students from your hall:");
            text11.setText("Students from your hall:");
            text21.setText("Students from your hall:");
        }

        numOfStudents.setValue(numOfStudents.getItems().get(0));
        choice00.setValue(choice00.getItems().get(0));
        choice01.setValue(choice01.getItems().get(0));
    }

    @FXML
    public void onSelectButton() {
        hBox0.setVisible(true);
        if (numOfStudents.getValue() >= 2) {
            updateColorsFromHall(choice11);
            if (cardId == 7)
                updateColorsFromCC(7, choice10);
            else
                updateColorsFromDiningHall(choice10);

            hBox1.setVisible(true);
            if (numOfStudents.getValue() == 3) {
                hBox2.setVisible(true);

                updateColorsFromHall(choice21);
                updateColorsFromCC(7, choice20);
            }
        }
    }

    @FXML
    public void onSelectButton0() {
        hBox0.setDisable(true);
        hBox1.setDisable(false);
    }

    @FXML
    public void onSelectButton1() {
        hBox1.setDisable(true);

        if (numOfStudents.getValue() == 2)
            sendButton.setVisible(true);
        else
            hBox2.setDisable(false);
    }

    @FXML
    public void onSelectButton2(){
        hBox2.setDisable(true);

        sendButton.setVisible(true);
    }

    @FXML
    public void onSendButton() {
        ArrayList<StudentColor> array = new ArrayList<>();
        ArrayList<StudentColor> hall = new ArrayList<>();

        hall.add(StudentColor.getStudentFromString(choice01.getValue()));
        if (numOfStudents.getValue() >= 2) {
            hall.add(StudentColor.getStudentFromString(choice11.getValue()));
            if (numOfStudents.getValue() == 3)
                hall.add(StudentColor.getStudentFromString(choice21.getValue()));
        }

        array.add(StudentColor.getStudentFromString(choice00.getValue()));
        if (numOfStudents.getValue() >= 2) {
            array.add(StudentColor.getStudentFromString(choice10.getValue()));
            if (numOfStudents.getValue() == 3)
                array.add(StudentColor.getStudentFromString(choice20.getValue()));
        }

        getGui().getViewController().getMainPlayer().removeFromHall(hall);
        getGui().getViewController().getMainPlayer().addToHall(array);
        if (cardId == 7) {
            getGui().getCharacterCardById(7).updateStudentsOnCard(array, false);
            getGui().getCharacterCardById(7).updateStudentsOnCard(hall, true);
        }
        else {
            getGui().getViewController().getMainPlayer().removeFromDiningHall(array);
            getGui().getViewController().getMainPlayer().addToDiningHall(hall);
        }

        cmCCG6 message = new cmCCG6(cardId, array, hall);
        getGui().getClient().send(getGui().getGson().toJson(message, cmCCG6.class));

        getGui().setUsedCC();
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
}
