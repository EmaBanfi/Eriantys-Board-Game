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

    public CharacterCard() {}

    protected int getCardId() {
        return cardId;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<RequiredInfo> getInfo() {
        return info;
    }

    public void increasePrice(){
        if(!increasedPrice){
            increasedPrice=true;
            price++;
        }
    }

    public void setGameBoard(GameBoard board) {
        this.gameBoard = board;
    }

    public void activate(){}
}
