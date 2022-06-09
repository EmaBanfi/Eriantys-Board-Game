package it.polimi.ingsw.network.client.clientModel;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG5;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CharacterCardGroup5 extends CharacterCard {
    private int availableBlockCards;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * create the CharacterCard 3, 5 or 6 and put on it 4 students
     */
    public CharacterCardGroup5(int id, CLI cli) {
        super(id, cli);
        if(getCardId() == 3){
            setText("You can choose an island and apply the majority count as if Mother Nature landed on that island." +
                    "Mother Nature will continue her normal movements after this effect");
            setPrice(3);
        }
        else if (getCardId() == 5) {
            this.availableBlockCards = 4;
            setText("You can place a Block Card on an island: on the first time Mother Nature lands on that island," +
                    "the block card will come back on top of this card and skip the majority count for that island");
            setPrice(2);
        }
        else if (getCardId() == 6){
            setText("The Towers won't affect the majority count on an island during this turn");
            setPrice(3);
        }
    }

    /**
     * implementation of the effect of the CharacterCard 5 and the CharacterCard 6; at the end increase the price of the CharacterCard
     *
     * @return
     */
    public boolean activate() {
        getCLI().showIslands(null);

        ClientMessage message;

        if (getCardId() == 3) {
            if (confirmActivation())
                return false;

            message = new cmCCG5(3, getCLI().askIsland(false, null));
            getCLI().getClient().send(new Gson().toJson(message, cmCCG5.class));
        }

        else if (getCardId() == 5) {
            System.out.println("Blocks on card: " + availableBlockCards);
            getCLI().showIslands(null);

            if (confirmActivation())
                return false;

            message = new cmCCG5(5, getCLI().askIsland(false, null));
            getCLI().getClient().send(new Gson().toJson(message, cmCCG5.class));
        }

        else if (getCardId() == 6){
            if (confirmActivation())
                return false;

            message = new cmCCG5(6, getCLI().askIsland(false, null));
            getCLI().getClient().send(new Gson().toJson(message, cmCCG5.class));
        }

        return true;
    }

    /**
     * used to add an block card on the CharacterCard n°5
     */
    @Override
    public void updateAvailableBlockCards(boolean update){
        if(update)
            availableBlockCards++;
        else
            availableBlockCards--;
    }

    @Override
    public boolean checkCCPrecondition() {
        if (getCLI().getPlayer().getCoins() < getPrice())
            System.out.println("Not enough coins");

        else if (getCardId() == 5)
            if (availableBlockCards == 0)
                System.out.println("0 block cards");

        else if (getCardId() == 6)
            if (getCLI().getResumeFrom().equals(Phase.CHOOSE_CLOUDS))
                System.out.println("Majority has already been calculated, please select another character card");

        else
             return true;

        return false;
    }
}
