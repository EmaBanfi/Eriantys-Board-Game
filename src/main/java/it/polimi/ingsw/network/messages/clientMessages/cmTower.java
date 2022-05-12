package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class cmTower extends ClientMessage {

    @SuppressWarnings("FieldCanBeLocal")
    private String tower;

    public cmTower() {
    }

    public cmTower(String tower) {
        this.tower = tower;
        setType("tower");
    }

}
