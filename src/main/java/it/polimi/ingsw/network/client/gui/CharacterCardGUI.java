package it.polimi.ingsw.network.client.gui;

import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.server.model.StudentColor;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class CharacterCardGUI {

    private final int cardId;
    private int price;
    private String text;
    private String cause = "";
    private final GUI gui;

    private final Scanner input = new Scanner(System.in);
    private transient boolean increasedPrice = false;

    public CharacterCardGUI(int cardId, GUI gui) {
        this.cardId = cardId;
        this.gui = gui;
    }

    public GUI getGUI() {
        return gui;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public void setCause(String cause){
        this.cause = cause;
    }

    public String getCause(){
        return cause;
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

    public boolean increasePrice() {
        if (!increasedPrice) {
            increasedPrice = true;
            price++;
            return true;
        } else
            return false;

    }

    public void updateStudentsOnCard(ArrayList<StudentColor> students, boolean add){}

    public ArrayList<StudentColor> getStudentsOnCard(){
        return null;
    }

    public void updateAvailableBlockCards(boolean add){
    }

    public abstract boolean checkCCPrecondition();
}
