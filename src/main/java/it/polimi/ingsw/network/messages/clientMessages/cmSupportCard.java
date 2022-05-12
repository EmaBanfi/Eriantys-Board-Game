package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class cmSupportCard extends ClientMessage {

    @SuppressWarnings("FieldCanBeLocal")
    private int id;

    public cmSupportCard() {
    }

    public cmSupportCard(int id) {
        this.id = id;
        setType("support card");
    }

}
