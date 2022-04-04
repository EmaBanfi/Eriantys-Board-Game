package it.polimi.ingsw.model;

import java.util.HashMap;

public class DiningHall extends Hall {
    private final HashMap<StudentColor, Table> tables;

    /**
     * create a new ArrayList of StudentColor and five tables, one for each StudentColor, stored in a HashMap
     */
    public DiningHall() {
        super();
        this.tables = new HashMap<>();
        for (StudentColor color : StudentColor.values())
            this.tables.put(color, new Table());
    }

    /**
     * check if there if any coins to take
     * @param color: color of the students to be checked
     * @return the number of coins taken
     */
    public int checkCoin(StudentColor color) {
        return tables.get(color).getCoins(countStudentColor(color));
    }
}
