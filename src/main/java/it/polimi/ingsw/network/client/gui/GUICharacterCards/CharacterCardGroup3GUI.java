package it.polimi.ingsw.network.client.gui.GUICharacterCards;

import it.polimi.ingsw.network.client.gui.GUI;

public class CharacterCardGroup3GUI extends CharacterCardGUI {

    public CharacterCardGroup3GUI(int id, GUI gui) {
        super(id, gui);
        setPrice(1);
        setText("You can move up to 2 more islands in this turn");
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
