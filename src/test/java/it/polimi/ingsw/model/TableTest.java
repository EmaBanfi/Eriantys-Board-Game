package it.polimi.ingsw.model;

import it.polimi.ingsw.network.server.model.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table table;
    int numOfStudents = 0;
    int coins = 0;

    @BeforeEach
    void setUp() {
        table = new Table();
    }

    @AfterEach
    void tearDown() {
        table = null;
        numOfStudents = 0;
        coins = 0;
    }

    /**
     * first test of the method getCoins()
     */
    @Test
    void getCoins1() {
        numOfStudents = 7;
        coins = table.getCoins(numOfStudents);
        assertEquals(2, coins);

        numOfStudents = 10;
        coins = table.getCoins(numOfStudents);
        assertEquals(1, coins);

        numOfStudents = 10;
        coins = table.getCoins(numOfStudents);
        assertEquals(0, coins);
    }

    /**
     * second test of the method getCoins()
     */
    @Test
    void getCoins2() {
        numOfStudents = 10;
        coins = table.getCoins(numOfStudents);
        assertEquals(3, coins);

        numOfStudents = 3;
        coins = table.getCoins(numOfStudents);
        assertEquals(0, coins);
    }

    /**
     * third test of the method getCoins()
     */
    @Test
    void getCoins3() {
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