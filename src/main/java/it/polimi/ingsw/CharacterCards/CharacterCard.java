package it.polimi.ingsw.CharacterCards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.GameBoard;
import it.polimi.ingsw.Hall;
import it.polimi.ingsw.Player;

import java.util.ArrayList;

public class CharacterCard {
    protected int cardId;
    private int price;
    @JsonIgnore
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
