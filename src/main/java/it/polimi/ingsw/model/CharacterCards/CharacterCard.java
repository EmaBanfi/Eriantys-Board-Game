package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Hall;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class CharacterCard {
    protected int cardId;
    private int price;
    private boolean increasedPrice=false;
    protected GameBoard gameBoard;
    Player currentPlayer;
    private ArrayList<RequiredInfo> info;

    protected int getCardId() {
        return cardId;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<RequiredInfo> getInfo() {
        return info;
    }

    public CharacterCard(){}
    public void increasePrice(){
        if(increasedPrice==false){
            increasedPrice=true;
            price++;
        }

    }
    public void activate(){}
    public void activate(Hall studentPlace){ }

}
