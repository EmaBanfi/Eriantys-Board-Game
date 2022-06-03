package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;

public class PlayerBoard {
    private final DiningHall diningHall;
    private final Hall hall;
    private int coins;

    /**
     * create a new PlayerBoard with a DiningHall and am Hall and, at the end, set at zero the coins of the owner
     */
    public PlayerBoard() {
        this.diningHall = new DiningHall();
        this.hall = new Hall();
        this.coins = 1;
    }

    /**
     * add students to the Hall
     * @param students: students that must be added
     */
    public void addStudentsToHall(ArrayList<StudentColor> students) {
        this.hall.addStudents(students);
    }

    /**
     * remove students from the Hall
     * @param students students that must be deleted
     */
    public void removeStudentsFromHall(ArrayList<StudentColor> students) {
        this.hall.removeStudents(students);
    }

    /**
     * add students to the DiningHall and check if there's any coins to take
     * @param students students that must be added
     */
    public void addStudentsToDiningHall(ArrayList<StudentColor> students) {
        this.diningHall.addStudents(students);
    }

    public void removeStudentsFromD(ArrayList<StudentColor> students){
        diningHall.removeStudents(students);
    }

    public boolean assignCoins() {
        boolean increasedCoins = false;
        int check = 0;

        for (StudentColor color : StudentColor.values()){
            check = checkCoins(color);
            if (check != 0) {
                this.coins = this.coins + check;
                increasedCoins = true;
            }
        }

        return increasedCoins;
    }

    /**
     * remove a student from the DiningHall, , useful for the effect of the character card 12
     * @param color: student that must be deleted
     * @return the number of student successfully removed
     */
    public int removeStudents(StudentColor color) {
        return diningHall.removeStudent(color);
    }

    /**
     * take a student from the Hall and move it in the DiningHall
     * @param students students to move
     */
    public void moveStudents(ArrayList<StudentColor> students) {
        addStudentsToDiningHall(students);
        removeStudentsFromHall(students);
    }

    /**
     * spend coins, useful if the player need to buy a CharacterCard
     * @param price price of the CharacterCard
     */
    public void useCoins(int price) {
        this.coins = this.coins - price;
    }

    /**
     * check if there's any coin to take from a specified table
     * @param color the color of the table to be checked
     * @return coins that we have to take from the table
     */
    public int checkCoins(StudentColor color) {
        return diningHall.checkCoin(color); // pass the color we want to check for
    }

    public DiningHall getDiningHall() {
        return diningHall;
    }

    public Hall getHall() {
        return hall;
    }

    public int getCoins() {
        return coins;
    }

    public int countStudentColorInDH(StudentColor color){
        return (int) diningHall.getStudents().stream().filter(x -> x.equals(color)).count();
    }
}
