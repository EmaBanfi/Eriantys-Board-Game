package it.polimi.ingsw.network.client.gui;

import it.polimi.ingsw.network.client.clientModel.PlayerView;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PlayerGui {
    private final Label name;
    private final Label tower;
    private final Label deck;
    private final Label remainingSupportCards;
    private final Label teachers;
    private final Label hall;
    private final Label diningHall;
    private final Label coins;
    private ImageView card;
    private PlayerView playerView;


    public PlayerGui(Label name, Label tower, Label deck, Label remainingSupportCards, Label teachers,Label hall, Label diningHall, Label coins, ImageView card, PlayerView playerView) {
        this.name = name;
        this.tower = tower;
        this.deck = deck;
        this.remainingSupportCards = remainingSupportCards;
        this.teachers = teachers;
        this.hall = hall;
        this.diningHall = diningHall;
        this.coins = coins;
        this.card = card;
        this.playerView = playerView;
        initName();
        initTower();
        initDeck();
        updateRemaining();
    }

    private void initName(){
        name.setText("Name:  " + playerView.getNickname());
    }

    private void initTower(){
        tower.setText("Tower:  " + playerView.getTower().toLowerCase());
    }

    private void initDeck(){
        deck.setText("Deck:  " + playerView.getDeckColor().toLowerCase());
    }

    public void updateRemaining(){
        remainingSupportCards.setText("Remaining support cards:  " + playerView.remainingSupportCards());
    }

    public void updateCard(){
        if(playerView.getUsedSupportCard()!=null) {
            card.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/SupportCard" + playerView.getUsedSupportCard().getId() + ".jpg"))));
            updateRemaining();
        }
        else
            card.setVisible(false);
    }

    public void updateTeachers(String teachers){
        this.teachers.setText(teachers);
    }

    public void updateHall(){
        StringBuilder text = new StringBuilder();
        text.append("Hall:");
        for(StudentColor color: playerView.getHall()){
            text.append("  ").append(color);
        }
    }

    public void updateDiningHall(){
        StringBuilder text = new StringBuilder();
        text.append("Dining hall:");
        for(StudentColor color: playerView.getDiningHall()){
            text.append("  ").append(color);
        }
    }

    public void updateCoins(){
        coins.setText("Coins  " + playerView.getCoins());
    }
}
