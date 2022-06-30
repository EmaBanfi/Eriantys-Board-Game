package it.polimi.ingsw.network.client.gui;

import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PlayerGui {
    private final Label tower;
    private final Label deck;
    private final Label remainingSupportCards;
    private final Label teachers;
    private final Label hall;
    private final Label diningHall;
    private final Label coins;
    private ImageView card;
    private PlayerView playerView;


    public PlayerGui(Label tower, Label deck, Label remainingSupportCards, Label teachers,Label hall, Label diningHall, Label coins, ImageView card, PlayerView playerView) {
        this.tower = tower;
        this.deck = deck;
        this.remainingSupportCards = remainingSupportCards;
        this.teachers = teachers;
        this.hall = hall;
        this.diningHall = diningHall;
        this.coins = coins;
        this.card = card;
        this.playerView = playerView;

        updateRemaining();
    }



    public void initTower(){
        Platform.runLater(() -> tower.setText("Tower:  " + playerView.getTower().toLowerCase()));
    }

    public void initDeck(){
        Platform.runLater(() -> deck.setText("Deck:  " + playerView.getDeckColor().toLowerCase()));

    }

    public void updateRemaining(){
        Platform.runLater(() -> remainingSupportCards.setText("Remaining support cards:  " + playerView.remainingSupportCards()));
    }

    public void updateCard(){
        if(playerView.getUsedSupportCard()!=null) {
            Platform.runLater(() -> {
                card.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/supportCards/sc" + playerView.getUsedSupportCard().getId() + ".png"))));
                updateRemaining();
            });
        }
        else
            card.setVisible(false);
    }

    public void updateTeachers(String teachers){
        Platform.runLater(() -> this.teachers.setText(teachers));
    }

    public void updateHall(){
        StringBuilder text = new StringBuilder();
        text.append("Hall: ");
        boolean first = true;
        for(StudentColor color: playerView.getHall()){
            if(first)
                first=false;
            else
                text.append(",");
            text.append(" ").append(color);
        }
        hall.setText(text.toString());
    }

    public void updateDiningHall(){
        StringBuilder text = new StringBuilder();
        text.append("Dining hall: ");
        boolean first = true;
        for(StudentColor color: playerView.getDiningHall()){
            if(first)
                first=false;
            else
                text.append(",");
            text.append(" ").append(color);
        }
        diningHall.setText(text.toString());
    }

    public void updateCoins(){
        Platform.runLater(() -> coins.setText("Coins  " + playerView.getCoins()));
    }
}
