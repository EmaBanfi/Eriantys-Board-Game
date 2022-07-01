package it.polimi.ingsw.network.client.gui.GUICharacterCards;

import it.polimi.ingsw.network.client.gui.GUI;

public class CharacterCardGroup2GUI extends CharacterCardGUI {

    public CharacterCardGroup2GUI(int id, GUI gui) {
        super(id, gui);
        setPrice(2);
        if(getCardId() == 2)
            setText("During this turn, you can take control of a teacher even if you have the same amounts of " +
                    "students of the player that controls him in your Dining Hall");
        else if (getCardId() == 8)
            setText("You have 2 more additional influence points during the majority count in this turn");
    }

    @Override
    public boolean checkCCPrecondition() {

        if (getGUI().getViewController().getMainPlayer().getCoins() < getPrice()) {
            setCause("Card " + getCardId() + " can't be activated because you don't have enough coins.");

            return  false;
        }

        return true;
    }
}
