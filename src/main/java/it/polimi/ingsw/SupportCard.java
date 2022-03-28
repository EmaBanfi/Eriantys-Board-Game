package it.polimi.ingsw;

public class SupportCard {
    private int turnOrder;
    private int movement;
    private int id;

    public SupportCard(int turnOrder, int movement, int id) {
        this.turnOrder = turnOrder;
        this.movement = movement;
        this.id = id;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public int getMovement() {
        return movement;
    }

    public int getId() {
        return id;
    }
}
