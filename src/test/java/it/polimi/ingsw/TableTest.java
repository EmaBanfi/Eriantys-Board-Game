package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void getCoins() {
        Table table = new Table();
        int numOfStudents = 7;
        int coins = table.getCoins(numOfStudents);
        assertEquals(2, coins);

        numOfStudents = 10;
        coins = table.getCoins(numOfStudents);
        assertEquals(1, coins);

        numOfStudents = 10;
        coins = table.getCoins(numOfStudents);
        assertEquals(0, coins);

        // new test
        table = new Table();
        numOfStudents = 10;
        coins = table.getCoins(numOfStudents);
        assertEquals(3, coins);

        numOfStudents = 3;
        coins = table.getCoins(numOfStudents);
        assertEquals(0, coins);

        // new test
        table = new Table();
        numOfStudents = 3;
        coins = table.getCoins(numOfStudents);
        assertEquals(1, coins);

        numOfStudents = 3;
        coins = table.getCoins(numOfStudents);
        assertEquals(0, coins);

        numOfStudents = 6;
        coins = table.getCoins(numOfStudents);
        assertEquals(1, coins);

        numOfStudents = 9;
        coins = table.getCoins(numOfStudents);
        assertEquals(1, coins);

        numOfStudents = 10;
        coins = table.getCoins(numOfStudents);
        assertEquals(0, coins);
    }
}