package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCard;
import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyAvailableCharacterCards extends ServerMessage{

    private ArrayList<CharacterCard> availableCharacterCards;

    public smNotifyAvailableCharacterCards() {
    }

    public smNotifyAvailableCharacterCards(String message, ArrayList<CharacterCard> availableCharacterCards) {
        super(message);
        this.availableCharacterCards = availableCharacterCards;
        setType("notify available character cards");
    }

}
