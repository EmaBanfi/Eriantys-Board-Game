package it.polimi.ingsw.network.server.model;

import java.util.ArrayList;
import java.util.HashMap;

public class DiningHall implements ManageStudents {
    private final HashMap<StudentColor, Table> tables;
    private final ArrayList<StudentColor> students;

    /**
     * create a new ArrayList of StudentColor and five tables, one for each StudentColor, stored in a HashMap
     */
    public DiningHall() {
        students = new ArrayList<>();
        this.tables = new HashMap<>();
        for (StudentColor color : StudentColor.values())
            this.tables.put(color, new Table());
    }

    /**
     * check if there if any coins to take
     * @param color color of the students to be checked
     * @return the number of coins taken
     */
    public int checkCoin(StudentColor color) {
        return tables.get(color).getCoins(countStudentColor(color));
    }

    /**
     * remove three students from the DiningHall, useful for the effect of the character card n°12
     * @param color the color of the three students (or less) that must be removed
     * @return the num of students deleted
     */
    public int removeStudent(StudentColor color) {
        int numOfStudents = countStudentColor(color);
        int count = 0;
        while (numOfStudents > 0 & count < 3) {
            students.remove(color);
            count++;
            numOfStudents--;
        }
        return count;
    }

    public void addStudents(ArrayList<StudentColor> students) {
        for(StudentColor studentColor: students){
            this.students.add(studentColor);
        }
    }

    public void addStudent(StudentColor student){students.add(student);}

    public void removeStudents(ArrayList<StudentColor> students) {
        for (StudentColor color : students)
            if(this.students.contains(color))
                this.students.remove(color);
    }

    public int countStudentColor(StudentColor color) {
        return (int) students.stream().filter(x -> x.equals(color)).count();
    }

    public ArrayList<StudentColor> getStudents() {
        ArrayList<StudentColor> studentColors = new ArrayList<>();
        for (StudentColor color: students){
            studentColors.add(color);
        }
        return studentColors;
    }
}
