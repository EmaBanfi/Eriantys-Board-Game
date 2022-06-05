package it.polimi.ingsw.network.client.clientModel;

import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.model.GameBoard;
import it.polimi.ingsw.network.server.model.Player;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class CharacterCard {
    private int cardId;
    private int price;
    private String text;
    private View view;
    private transient boolean increasedPrice = false;

    public CharacterCard(int cardId, View view) {
        this.cardId = cardId;
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

    public int getCardId() {
        return cardId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public void updateStudentsOnCard(ArrayList<StudentColor> students, boolean add){}

    public void updateAvailableBlockCards(boolean update){
    }
}
