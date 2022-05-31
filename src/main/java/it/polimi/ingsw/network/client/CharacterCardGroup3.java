package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.CCG3;

import java.io.BufferedReader;
import java.io.IOException;

public class CharacterCardGroup3 extends CharacterCard{

    private int chosenIsland;
    private RequiredInfo requiredInfo;
    private BufferedReader br;

    public CharacterCardGroup3(int id, int price, View view) {
        super(id, price, view);
        setText("You can move up to 2 more islands in this turn");
    }

    public void activate(){
        System.out.println("Choose how many islands do you want to move Mother Nature: ");
        int movementChoice = 0;
        do{
            System.out.println("Please select between 1 and 2: ");
            try {
                movementChoice = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(movementChoice != 1 || movementChoice != 2);
        CCG3 message = new CCG3(4, movementChoice);
        getView().getClient().send(new Gson().toJson(message, CCG3.class));
    }
}
