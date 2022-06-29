package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.clientModel.*;
import it.polimi.ingsw.network.client.gui.AlertBox;
import it.polimi.ingsw.network.client.gui.CharacterCardGUI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG2;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG6;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
    private Label warning;

    private final String pathToCCImage = "/images/characterCards/cc";
    private ArrayList<CharacterCardGUI> availableCC;

    @Override
    public void update() {
        getGui().updateUsableCC();

        availableCC = getGui().getAvailableCC();
        ArrayList<Integer> usableCC = getGui().getUsableCC();

        image0.setImage(new Image(Objects.requireNonNull(getClass().getResource(pathToCCImage + availableCC.get(0).getCardId() + ".jpg")).toExternalForm()));
        image1.setImage(new Image(Objects.requireNonNull(getClass().getResource(pathToCCImage + availableCC.get(1).getCardId() + ".jpg")).toExternalForm()));
        image2.setImage(new Image(Objects.requireNonNull(getClass().getResource(pathToCCImage + availableCC.get(2).getCardId() + ".jpg")).toExternalForm()));

        int minPrice = 4;
        for (CharacterCardGUI card : availableCC)
            if (card.getPrice() < minPrice)
                minPrice = card.getPrice();

        if (getGui().getViewController().getMainPlayer().getCoins() < minPrice || usableCC.isEmpty())
               warning.setText("You can't activate any character card because you don't have enough money or some conditions are not respected");
        else {
            for (int i = 0; i < 3; i++)
                if (!usableCC.contains(availableCC.get(i).getCardId())) {
                    if (i == 0)
                        vBox0.setDisable(true);
                    else if (i == 1)
                        vBox1.setDisable(true);
                    else
                        vBox2.setDisable(true);
                }
        }
    }

    @FXML
    public void onInfo0Button() {
        AlertBox.display("Info of character card " + availableCC.get(0).getCardId(), availableCC.get(0).getText());
    }

    @FXML
    public void onActivate0Button() {
        activate(availableCC.get(0).getCardId());
    }

    @FXML
    public void onInfo1Button() {
        AlertBox.display("Info of character card " + availableCC.get(1).getCardId(), availableCC.get(1).getText());
    }

    @FXML
    public void onActivate1Button() {
        activate(availableCC.get(1).getCardId());
    }

    @FXML
    public void onInfo2Button() {
        AlertBox.display("Info of character card " + availableCC.get(2).getCardId(), availableCC.get(2).getText());
    }

    @FXML
    public void onActivate2Button() {
        activate(availableCC.get(2).getCardId());
    }

    private void activate(int id) {
        if (id == 2 || id == 8) {
            cmCCG2 message = new cmCCG2(id);
            getGui().getClient().send(getGui().getGson().toJson(message, cmCCG2.class));
        }
        else {
            switch (availableCC.get(0).getCardId()) {
                case 1 -> {
                    getGui().getSceneManager("CCG1.fxml").getController().update();
                    getGui().updateSceneOnStageOnlyForCC("CCG1.fxml");
                }
                case 4 -> getGui().updateSceneOnStageOnlyForCC("CCG3.fxml");
                case 9, 11, 12 -> {
                    getGui().getSceneManager("CCG4.fxml").getController().setInput(id);
                    getGui().getSceneManager("CCG4.fxml").getController().update();
                    getGui().updateSceneOnStageOnlyForCC("CCG4.fxml");
                }
                case 3, 5, 6 -> {
                    getGui().getSceneManager("CCG5.fxml").getController().setInput(id);
                    getGui().getSceneManager("CCG5.fxml").getController().update();
                    getGui().updateSceneOnStageOnlyForCC("CCG5.fxml");
                }
                case 7, 10 -> {
                    getGui().getSceneManager("CCG6.fxml").getController().setInput(id);
                    getGui().getSceneManager("CCG6.fxml").getController().update();
                    getGui().updateSceneOnStageOnlyForCC("CCG6.fxml");
                }
            }
        }
    }
}
