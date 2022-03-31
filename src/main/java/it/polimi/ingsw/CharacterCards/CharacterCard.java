package it.polimi.ingsw.CharacterCards;

import it.polimi.ingsw.RequiredInfo;

import java.util.ArrayList;

public abstract class CharacterCard {
    private int cardId;
    private int price;
    private boolean increasedPrice;
    //private GameBoard gameBoard;
    //private Player currentPlayer;
    private ArrayList<RequiredInfo> info;

    public int getCardId() {
        return cardId;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<RequiredInfo> getInfo() {
        return info;
    }

    public void increasePrice(){
        if(increasedPrice==false){
            increasedPrice=true;
            price++;
        }
    }
    public void activate(){}
}
