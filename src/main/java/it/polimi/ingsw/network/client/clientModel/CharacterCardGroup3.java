package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.View;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CharacterCardGroup3 extends CharacterCard{

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public CharacterCardGroup3(int id, View view) {
        super(id, view);
        setPrice(1);
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
        cmCCG3 message = new cmCCG3(movementChoice);
        getView().getClient().send(new Gson().toJson(message, cmCCG3.class));
    }
}
