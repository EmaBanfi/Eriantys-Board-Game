package it.polimi.ingsw.network.client.clientModel;

import it.polimi.ingsw.network.client.View;

public class CharacterCardCreator {

    public CharacterCard createCard(int id, View view) {
        return switch (id) {
            case 1 -> new CharacterCardGroup1(id, view);
            case 2, 8 -> new CharacterCardGroup2(id, view);
            case 4 -> new CharacterCardGroup3(id, view);
            case 9, 11, 12 -> new CharacterCardGroup4(id, view);
            case 3, 5, 6 -> new CharacterCardGroup5(id, view);
            case 7, 10 -> new CharacterCardGroup6(id, view);
            default -> null;
        };
    }
}
