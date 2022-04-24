package it.polimi.ingsw.network.server.model.CharacterCards;

import it.polimi.ingsw.network.server.model.GameBoard;
import it.polimi.ingsw.network.server.model.Player;

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

    public void setCardId(int id) {
        cardId = id;
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

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void activate(){}
}
