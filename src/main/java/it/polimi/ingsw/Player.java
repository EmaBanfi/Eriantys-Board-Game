package it.polimi.ingsw;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private PlayerBoard board;
    private ArrayList<SupportCard> deck;
    private Tower tower;
    private SupportCard usedSupportCard;
    private String deckColor;
    private boolean bonusToPromotion;
    private boolean additionalInfluencePoints;
    private final String nickName;

    public Player(String nickName) {
        this.nickName = nickName;
        board = new PlayerBoard();
        for(int i=1; i<11; i++){
            deck.add(new SupportCard(i,i/2+1%2,i));
        }
    }

    public PlayerBoard getBoard() {
        return board;
    }

    public Tower getTower() {
        return tower;
    }

    public SupportCard getUsedCard() {
        return usedSupportCard;
    }

    public boolean isBonusToPromotion() {
        return bonusToPromotion;
    }

    public boolean isAdditionalInfluencePoints() {
        return additionalInfluencePoints;
    }

    public String getNickName() {
        return nickName;
    }
}
