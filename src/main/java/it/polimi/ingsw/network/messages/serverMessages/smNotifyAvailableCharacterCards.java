package it.polimi.ingsw.network.messages.serverMessages;



import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCard;

import java.util.ArrayList;

public class smNotifyAvailableCharacterCards extends ServerMessage{
    ArrayList<CharacterCard> availableCharacterCards;

    public smNotifyAvailableCharacterCards(ArrayList<CharacterCard> availableCharacterCards) {
        this.availableCharacterCards = availableCharacterCards;
    }
}
