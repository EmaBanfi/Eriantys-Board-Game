package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;

public class DiningHall extends Hall {
    private final HashMap<StudentColor, Table> tables;

    public DiningHall() {
        super();
        this.tables = new HashMap<>();
        for (StudentColor color : StudentColor.values())
            this.tables.put(color, new Table());
    }

    public Table showTable(StudentColor color) {
        return tables.get(color);
    }

    public int checkCoin(StudentColor color) {
        return tables.get(color).getCoins(countStudentColor(color));
    }

    // return the num of students deleted by the effect of the character card n°12
    public int ripStudents(StudentColor color) {
        int numOfStudents = countStudentColor(color);
        int count = 0;
        while (numOfStudents > 0 & count < 3) {
            removeStudentsColor(color);
            count++;
            numOfStudents--;
        }

        return count;
    }
}
