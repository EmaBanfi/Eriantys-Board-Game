package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG2;

public class CharacterCardGroup2 extends CharacterCard{

    public CharacterCardGroup2(int id, CLI cli) {
        super(id, cli);
        setPrice(2);
        if(getCardId() == 2)
            setText("During this turn, you can take control of a teacher even if you have the same amounts of " +
                    "students of the player that controls him in your Dining Hall");
        else if (getCardId() == 8)
            setText("You have 2 more additional influence points during the majority count in this turn");
    }


    @Override
    public boolean activate(){
        cmCCG2 message = null;

        if (getCardId() == 2) {
            getCLI().showAllDH();
            if (getCLI().getResumeFrom().equals(Phase.CHOOSE_MOTHER_MOVEMENTS) || getCLI().getResumeFrom().equals(Phase.CHOOSE_CLOUDS))
                System.out.println("Teachers have already been assigned");
            if (confirmActivation())
                return false;

            message = new cmCCG2(2);
        }
        else if (getCardId() == 8){
            getCLI().showIslands(getCLI().getMainPlayer().getUsedSupportCard().getMovement());
            if (getCLI().getResumeFrom().equals(Phase.CHOOSE_CLOUDS))
                System.out.println("Majority has already been calculated.");
            if (confirmActivation())
                return false;

            message = new cmCCG2(8);
        }

        getCLI().getClient().send(new Gson().toJson(message, cmCCG2.class));

        return true;
    }

    @Override
    public boolean checkCCPrecondition() {
        System.out.println("\n\nCHARACTER CARD ACTIVATION\n");

        if (getCLI().getMainPlayer().getCoins() < getPrice()) {
            System.out.println("Card " + getCardId() + " can't be activated because you don't have enough coins\n");

            return  false;
        }

        return true;
    }
}
