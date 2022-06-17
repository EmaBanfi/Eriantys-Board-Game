package it.polimi.ingsw.network.client.clientModel;

import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class CharacterCard {
    private final int cardId;
    private int price;
    private String text;
    private final CLI cli;

    private final Scanner input = new Scanner(System.in);
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

    public void updateAvailableBlockCards(boolean add){
    }

    public void showCard() {
        System.out.println("Card id: " + cardId);
        System.out.println("Card price: " + price);
        System.out.println("Card effect: " + text + "\n");
    }

    public boolean confirmActivation() {

        System.out.println("Do you still want to activate the card? (yes|no)");
        String str;
        boolean notValidChoice;
        do {
            str = input.nextLine();

            notValidChoice = (!str.equalsIgnoreCase("yes") && !str.equalsIgnoreCase("no"));
            if(notValidChoice)
                System.out.println("please select yes or no");
        } while( notValidChoice);

        return str.equalsIgnoreCase("no");
    }

    public abstract boolean checkCCPrecondition();

    public  Scanner getInput(){
        return input;
    }
}
