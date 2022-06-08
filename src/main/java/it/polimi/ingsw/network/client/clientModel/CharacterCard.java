package it.polimi.ingsw.network.client.clientModel;

import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public abstract class CharacterCard {
    private int cardId;
    private int price;
    private String text;
    private CLI cli;
    private transient boolean increasedPrice = false;

    public CharacterCard(int cardId, CLI cli) {
        this.cardId = cardId;
        this.cli = cli;
    }

    public CLI getCLI() {
        return cli;
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

    public abstract boolean activate();

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
