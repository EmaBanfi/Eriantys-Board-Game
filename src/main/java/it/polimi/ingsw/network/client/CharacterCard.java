package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.model.GameBoard;
import it.polimi.ingsw.network.server.model.Player;

import java.util.ArrayList;

public class CharacterCard {
    protected int cardId;
    private int price;
    private String text;
    private View view;
    private transient boolean increasedPrice = false;

    public CharacterCard(int cardId, int price, View view) {
        this.cardId = cardId;
        this.price = price;
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    protected int getCardId() {
        return cardId;
    }

    public int getPrice() {
        return price;
    }

    public void activate(){}

    public boolean increasePrice() {
        if (!increasedPrice) {
            increasedPrice = true;
            price++;
            return true;
        } else
            return false;

    }
}
