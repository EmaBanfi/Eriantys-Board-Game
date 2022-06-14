package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CharacterCardGroup3 extends CharacterCard{

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public CharacterCardGroup3(int id, CLI cli) {
        super(id, cli);
        setPrice(1);
        setText("You can move up to 2 more islands in this turn");
    }

    public boolean activate(){
        if (getCLI().getResumeFrom().equals(Phase.CHOOSE_CLOUDS))
            getCLI().showIslands(2);
        else
            getCLI().showIslands(2 + getCLI().getMainPlayer().getUsedSupportCard().getMovement());

        if (confirmActivation())
            return false;

        System.out.println("Choose how many islands do you want to move Mother Nature (1 or 2)");
        Integer movementChoice;
        String str = "";
        boolean validChoice;
        do {
            validChoice = false;

            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            movementChoice = getCLI().stringToInteger(str);

            if (movementChoice != null) {
                if (movementChoice == 1 || movementChoice == 2)
                    validChoice = true;
                else
                    System.out.println("Please select 1 or 2");
            }
            else
                System.out.println("Not an int");
        } while (!validChoice);

        getCLI().updateMotherPosition((getCLI().getMotherPosition() + 2) % getCLI().getAvailableIslands().size());

        cmCCG3 message = new cmCCG3(movementChoice);
        getCLI().getClient().send(new Gson().toJson(message, cmCCG3.class));

        return true;
    }

    @Override
    public boolean checkCCPrecondition() {
        if (getCLI().getMainPlayer().getCoins() < getPrice())
            System.out.println("Not enough coins");

        else
            return true;

        return false;
    }
}
