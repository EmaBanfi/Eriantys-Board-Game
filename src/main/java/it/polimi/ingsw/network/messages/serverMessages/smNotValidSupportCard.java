package it.polimi.ingsw.network.messages.serverMessages;

public class smNotValidSupportCard extends ServerMessage {

    public smNotValidSupportCard() {
    }

    public smNotValidSupportCard(String message) {
        super(message);
        setType("not valid support card");
    }

}
