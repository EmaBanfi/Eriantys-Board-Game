package it.polimi.ingsw;

import java.util.HashMap;

public class DiningHall extends Hall {
    private final HashMap<StudentColor, Table> tables;

    public DiningHall() {
        super();
        this.tables = new HashMap<>();
        for (StudentColor color : StudentColor.values())
            this.tables.put(color, new Table());
    }

    public int checkCoin(StudentColor color) {
        return tables.get(color).getCoins(countStudentColor(color));
    }
}
