package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.clientModel.*;
import it.polimi.ingsw.network.client.gui.AlertBox;
import it.polimi.ingsw.network.client.gui.CharacterCardGUI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG2;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG6;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class AskActivateCCController extends GenericController {

    @FXML
    private VBox vBox0;
    @FXML
    private ImageView image0;
    @FXML
    private Button info0Button;
    @FXML
    private Button activate0Button;
    @FXML
    private VBox vBox1;
    @FXML
    private ImageView image1;
    @FXML
    private Button info1Button;
    @FXML
    private Button activate1Button;
    @FXML
    private VBox vBox2;
    @FXML
    private ImageView image2;
    @FXML
    private Button info2Button;
    @FXML
    private Button activate2Button;
    @FXML
    private Label label0;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Button goBack;
    @FXML
    private VBox warningVBox;

    private final String pathToCCImage = "/images/characterCards/cc";
    private ArrayList<CharacterCardGUI> availableCC;
    private final ColorAdjust monochrome = new ColorAdjust();

    @Override
    public void update() {
        getGui().updateUsableCC();

        availableCC = getGui().getAvailableCC();
        ArrayList<Integer> usableCC = getGui().getUsableCC();

        label0.setText(availableCC.get(0).getCause());
        label1.setText(availableCC.get(1).getCause());
        label2.setText(availableCC.get(2).getCause());

        warningVBox.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(warningVBox, 0.0);
        AnchorPane.setRightAnchor(warningVBox, 0.0);
        warningVBox.setAlignment(Pos.BOTTOM_CENTER);

        image0.setImage(new Image(Objects.requireNonNull(getClass().getResource(pathToCCImage + availableCC.get(0).getCardId() + ".jpg")).toExternalForm()));
        image1.setImage(new Image(Objects.requireNonNull(getClass().getResource(pathToCCImage + availableCC.get(1).getCardId() + ".jpg")).toExternalForm()));
        image2.setImage(new Image(Objects.requireNonNull(getClass().getResource(pathToCCImage + availableCC.get(2).getCardId() + ".jpg")).toExternalForm()));

        int minPrice = 4;
        for (CharacterCardGUI card : availableCC)
            if (card.getPrice() < minPrice)
                minPrice = card.getPrice();

        monochrome.setSaturation(-1);

        for (int i = 0; i < 3; i++)
            if (!usableCC.contains(availableCC.get(i).getCardId())) {
                if (i == 0) {
                    vBox0.setDisable(true);
                }
                else if (i == 1) {
                    vBox1.setDisable(true);
                }
                else {
                    vBox2.setDisable(true);
                }
            }
    }

    private void disablePlusGray(ImageView deck) {
        deck.setDisable(true);
        deck.setEffect(monochrome);
    }

    @FXML
    public void onInfo0Button() {
        AlertBox.display("Info of character card " + availableCC.get(0).getCardId(), availableCC.get(0).getText());
    }

    @FXML
    public void onActivate0Button() {
        activate(0);
    }

    @FXML
    public void onInfo1Button() {
        AlertBox.display("Info of character card " + availableCC.get(1).getCardId(), availableCC.get(1).getText());
    }

    @FXML
    public void onActivate1Button() {
        activate(1);
    }

    @FXML
    public void onInfo2Button() {
        AlertBox.display("Info of character card " + availableCC.get(2).getCardId(), availableCC.get(2).getText());
    }

    @FXML
    public void onActivate2Button() {
        activate(2);
    }

    private void activate(int pos) {
        if (availableCC.get(pos).getCardId() == 2 || availableCC.get(pos).getCardId() == 8) {
            cmCCG2 message = new cmCCG2(availableCC.get(pos).getCardId());
            getGui().getClient().send(getGui().getGson().toJson(message, cmCCG2.class));

            getGui().setUsedCC();
            getGui().backToScene();
        }
        else {
            switch (availableCC.get(pos).getCardId()) {
                case 1 -> {
                    getGui().getSceneManager("CCG1.fxml").getController().update();
                    getGui().updateSceneOnStage("CCG1.fxml");
                }
                case 4 -> getGui().updateSceneOnStage("CCG3.fxml");
                case 9, 11, 12 -> {
                    getGui().getSceneManager("CCG4.fxml").getController().setInput(availableCC.get(pos).getCardId());
                    getGui().getSceneManager("CCG4.fxml").getController().update();
                    getGui().updateSceneOnStage("CCG4.fxml");
                }
                case 3, 5, 6 -> {
                    getGui().getSceneManager("CCG5.fxml").getController().setInput(availableCC.get(pos).getCardId());
                    getGui().getSceneManager("CCG5.fxml").getController().update();
                    getGui().updateSceneOnStage("CCG5.fxml");
                }
                case 7, 10 -> {
                    getGui().getSceneManager("CCG6.fxml").getController().setInput(availableCC.get(pos).getCardId());
                    getGui().getSceneManager("CCG6.fxml").getController().update();
                    getGui().updateSceneOnStage("CCG6.fxml");
                }
            }
        }
    }

    @FXML
    public void onGoBackButton() {
        getGui().backToScene();
    }
}
