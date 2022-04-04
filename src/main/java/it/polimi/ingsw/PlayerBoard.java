package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.NotEnoughMoneyException;

import java.util.ArrayList;

public class PlayerBoard {
    private final DiningHall diningHall;
    private final Hall hall;
    private int coins;

    public PlayerBoard() {
        this.diningHall = new DiningHall();
        this.hall = new Hall();
        this.coins = 0;
    }

    public void addStudentsToDiningHall(ArrayList<StudentColor> students) {
        this.diningHall.addStudents(students);

        // by adding students we have to check if there are coins to take for each table
        StudentColor[] colors = StudentColor.values();
        for (StudentColor color : colors)
            this.coins = this.coins + checkCoins(color);
    }
    public void addStudentsToHall(ArrayList<StudentColor> students){
        hall.addStudents(students);
    }

    // remove students from the dining hall, useful for the effect of the character card 12
    public int removeStudents(StudentColor color) {
        return diningHall.removeStudent(color);
    }

    // take a student from the Hall and move it in the DiningHall
    public void moveStudents(ArrayList<StudentColor> students) {
        addStudentsToDiningHall(students);
        this.hall.removeStudents(students);
    }

    // switchStudentsToH() and switchStudentsToD() together swap two students between the Hall and the DiningHall (useful for character card 10)

    public void switchStudentsToH(ArrayList<StudentColor> students) {
        hall.addStudents(students);
        diningHall.removeStudents(students);
    }

    public void switchStudentsToD(ArrayList<StudentColor> students) {
        addStudentsToDiningHall(students);
        hall.removeStudents(students);
    }

    public void useCoins(int price) throws NotEnoughMoneyException {
        if ((this.coins - price) < 0)
            throw new NotEnoughMoneyException();
        else
            this.coins = this.coins - price;
    }

    // return the coins that we have to take from the table for that color
    public int checkCoins(StudentColor color) {
        return diningHall.checkCoin(color); // pass the color we want to check for
    }

    // getDiningHall, getHall and getCoins are methods useful for the class PlayerBoardTest
    public DiningHall getDiningHall() {
        return diningHall;
    }

    public Hall getHall() {
        return hall;
    }

    public int getCoins() {
        return coins;
    }
}
