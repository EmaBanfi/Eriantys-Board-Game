package it.polimi.ingsw.network.server.model.CharacterCards;

public class CharacterCard {
    protected int cardId;
    private int price;
    private transient boolean increasedPrice=false;

    public CharacterCard(int cardID, int price) {
        this.cardId = cardID;
        this.price = price;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int id) {
        cardId = id;
    }

    public int getPrice() {
        return price;
    }

    public boolean increasePrice(){
        if(!increasedPrice){
            increasedPrice=true;
            price++;
            return true;
        }
        else
            return false;
    }
}
