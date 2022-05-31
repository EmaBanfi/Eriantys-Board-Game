package it.polimi.ingsw.network.server.model;

public class SupportCard {
    private final int turnOrder;
    private final int movement;
    private final int cardId;
    private double additionalTurnOrder ;

    /**
     * Constructor
     * @param turnOrder determines the playing order in the next action phase
     * @param movement determines the movements of motherNature
     * @param id indicates the cardId
     */
    public SupportCard(int turnOrder, int movement, int id) {
        this.turnOrder = turnOrder;
        this.movement = movement;
        this.cardId = id;
        additionalTurnOrder=0;
    }

    public double getTurnOrder() {
        return turnOrder+additionalTurnOrder;
    }

    public int getMovement() {
        return movement;
    }

    public void setAdditionalTurnOrder(double delay){
        additionalTurnOrder=delay;
    }
    public int getId() {
        return cardId;
    }
}
