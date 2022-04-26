package it.polimi.ingsw.network.message.serverMessages;

import java.util.ArrayList;

public class smAskTowerColor extends ServerMessage{
    ArrayList<String> availableColors;

    public smAskTowerColor(ArrayList<String> availableColors) {
        this.availableColors = availableColors;
    }
}
