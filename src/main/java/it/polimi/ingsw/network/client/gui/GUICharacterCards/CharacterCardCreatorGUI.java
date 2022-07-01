package it.polimi.ingsw.network.client.gui.GUICharacterCards;

import it.polimi.ingsw.network.client.gui.GUI;

public class CharacterCardCreatorGUI {

    public CharacterCardGUI createCard(int id, GUI gui) {
        return switch (id) {
            case 1 -> new CharacterCardGroup1GUI(id, gui);
            case 2, 8 -> new CharacterCardGroup2GUI(id, gui);
            case 4 -> new CharacterCardGroup3GUI(id, gui);
            case 9, 11, 12 -> new CharacterCardGroup4GUI(id, gui);
            case 3, 5, 6 -> new CharacterCardGroup5GUI(id, gui);
            case 7, 10 -> new CharacterCardGroup6GUI(id, gui);
            default -> null;
        };
    }
}
