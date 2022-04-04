package it.polimi.ingsw.model;

public class SupportCard {
    private int turnOrder;
    private int movement;
    private int cardId;

    /**
     * Constructor
     * @param turnOrder: determines the playing order in the next action phase
     * @param movement: determines the movements of motherNature
     * @param id: indicates the cardId
     */
    public SupportCard(int turnOrder, int movement, int id) {
        this.turnOrder = turnOrder;
        this.movement = movement;
        this.cardId = id;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public int getMovement() {
        return movement;
    }

    public int getId() {
        return cardId;
    }
}
