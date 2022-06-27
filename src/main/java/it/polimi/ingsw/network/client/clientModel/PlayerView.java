package it.polimi.ingsw.network.client.clientModel;

import it.polimi.ingsw.network.server.model.StudentColor;
import it.polimi.ingsw.network.server.model.SupportCard;

import java.util.ArrayList;

public class PlayerView {

    private final ArrayList<StudentColor> hall;
    private final ArrayList<StudentColor> diningHall;
    private final String nickname;
    private SupportCard usedSupportCard;
    private int coins;
    private String tower;
    private  String deckColor;
    private ArrayList<SupportCard> supportCards;

    public PlayerView(String nickname) {
        this.nickname = nickname;
        coins = 1;
        initSupportDeck();
        hall = new ArrayList<>();
        diningHall = new ArrayList<>();
    }

    private void initSupportDeck(){
        supportCards = new ArrayList<>();

        for(int i=1; i<11; i++){
            supportCards.add(new SupportCard(i,i/2+i%2,i));
        }
    }

    public String getTower() {
        return tower;
    }

    public void setTower(String tower) {
        this.tower = tower;
    }


    public String getNickname() {
        return nickname;
    }

    public ArrayList<StudentColor> getHall() {
        return new ArrayList<>(hall);
    }

    public ArrayList<StudentColor> getDiningHall() {
        return new ArrayList<>(diningHall);
    }

    public SupportCard getUsedSupportCard() {
        return usedSupportCard;
    }

    public int getCoins() {
        return coins;
    }

    public void setUsedSupportCard(int usedSupportCard) {
        SupportCard usedCard;
        for(SupportCard card: supportCards)
            if(card.getId()==usedSupportCard){
                usedCard= new SupportCard((int)card.getTurnOrder(),card.getMovement(),card.getId());
                this.usedSupportCard=usedCard;
                supportCards.remove(card);
                break;
            }
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addToDiningHall(ArrayList<StudentColor> students){
        diningHall.addAll(students);
    }

    public void addToDiningHall(StudentColor student){
        diningHall.add(student);
    }

    public void removeFromDiningHall(ArrayList<StudentColor> students){
        for (StudentColor color : students)
            diningHall.remove(color);
    }

    public void addToHall(ArrayList<StudentColor> students){
        for (StudentColor color : students)
            hall.add(color);
    }

    public void addToHall(StudentColor student){
        hall.add(student);
    }

    public void removeFromHall(StudentColor student){
        hall.remove(student);
    }

    public void removeFromHall(ArrayList<StudentColor> students){
        for (StudentColor color : students)
            hall.remove(color);
    }

    public void setDeckColor(String deckColor){
        this.deckColor = deckColor;
    }

    public ArrayList<SupportCard> getSupportCards() {
        return supportCards;
    }

    public void decreaseCoins(int price) {
        coins = coins - price;
    }

    public void resetSupportCard(){usedSupportCard=null;}
}
