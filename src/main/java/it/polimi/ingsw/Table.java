package it.polimi.ingsw;

import java.util.HashMap;

public class Table {
    private final HashMap<Integer, Boolean> coins;

    public Table() {
        this.coins = new HashMap<>();
        for (int i=0; i<3; i++)
            coins.put(i, Boolean.TRUE);
    }

    // numOfStudents must be the total of the students that we have of that color
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
