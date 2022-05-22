package it.polimi.ingsw.network.client;

public class characterCardView {
    private int id;
    private int cost;

    public characterCardView(int id){
        this.id = id;
    }

    public int getCardId(){
        return id;
    }

    public int getCost(){
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
