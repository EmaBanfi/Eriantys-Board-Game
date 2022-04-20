package it.polimi.ingsw.model;

import java.util.HashMap;

public class Table {
    private final HashMap<Integer, Boolean> coins;

    /**
     * constructor, set an HashMap with three TRUE cause the seats that have a coin are three
     */
    public Table() {
        this.coins = new HashMap<>();
        for (int i=0; i<3; i++)
            coins.put(i, Boolean.TRUE);
    }

    /**
     * check if by adding the students to their table, there's some coins to take; if it takes money change that seat on FALSE
     * @param numOfStudents must be the total of the students that we have of that color
     * @return the coins which must be taken
     */
    public int getCoins(int numOfStudents) {
        int count = 0;
        int index = numOfStudents / 3;
        for (int i = 0; i<index; i++)
            if (coins.get(i) == Boolean.TRUE) {
                count++;
                coins.put(i, Boolean.FALSE);
            }
        return count;
    }
}
