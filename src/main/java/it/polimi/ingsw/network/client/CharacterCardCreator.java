package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.Server;

public class CharacterCardCreator {

    public CharacterCard createCard(int id, View view) {
        switch (id) {
            case 1:
                return new CharacterCardGroup1(id, view);
            case 2:
            case 8:
                return new CharacterCardGroup2(id, view);
            case 4:
                return new CharacterCardGroup3(id, view);
            case 9:
            case 11:
            case 12:
                return new CharacterCardGroup4(id, view);
            case 3:
            case 5:
            case 6:
                return new CharacterCardGroup5(id, view);
            case 7:
            case 10:
                return new CharacterCardGroup6(id, view);
        }
        return null;
    }
}
