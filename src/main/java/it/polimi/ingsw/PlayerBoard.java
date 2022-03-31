package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.NotEnoughMoneyException;

import java.util.ArrayList;

public class PlayerBoard {
    private final DiningHall diningHall;
    private final Hall hall;
    private int coins = 0;

    public PlayerBoard() {
        this.diningHall = new DiningHall();
        this.hall = new Hall();
    }

    public void addStudentsToHall(ArrayList<StudentColor> students) {
        this.hall.addStudents(students);
    }

    public void addStudentsToDiningHall(ArrayList<StudentColor> students) {
        this.diningHall.addStudents(students);

        // by adding students we have to check if there are coins to take for each table
        for (StudentColor color : students)
            if (checkCoins(color) > 0)
                coins++;
    }

    // remove students from the dining hall, useful for the effect of the character card 12
    public int removeStudents(StudentColor color) {
        return diningHall.removeStudent(color);
    }

    // take a student from the Hall and move it in the DiningHall
    public void moveStudents(ArrayList<StudentColor> students) {
        this.diningHall.addStudents(students);
        this.hall.removeStudents(students);
    }

    // switchStudentsToH() and switchStudentsToD() together swap two students between the Hall and the DiningHall (useful for character card 10)

    public void switchStudentsToH(ArrayList<StudentColor> students) {
        hall.addStudents(students);
        diningHall.removeStudents(students);
    }

    public void switchStudentsToD(ArrayList<StudentColor> students) {
        diningHall.addStudents(students);
        for (StudentColor color : students)
            hall.removeStudents(students);
    }

    public void useCoins(int price) throws NotEnoughMoneyException {
        if ((coins - price) < 0)
            throw new NotEnoughMoneyException();
        else
            coins = - price;
    }

    // return the coins that we have to take from the table for that color
    public int checkCoins(StudentColor color) {
        return diningHall.checkCoin(color); // pass the color we want to check for
    }
}
