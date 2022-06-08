package it.polimi.ingsw.network.client.clientModel;

import it.polimi.ingsw.network.client.CLI;

public class CharacterCardCreator {

    public CharacterCard createCard(int id, CLI cli) {
        return switch (id) {
            case 1 -> new CharacterCardGroup1(id, cli);
            case 2, 8 -> new CharacterCardGroup2(id, cli);
            case 4 -> new CharacterCardGroup3(id, cli);
            case 9, 11, 12 -> new CharacterCardGroup4(id, cli);
            case 3, 5, 6 -> new CharacterCardGroup5(id, cli);
            case 7, 10 -> new CharacterCardGroup6(id, cli);
            default -> null;
        };
    }
}
