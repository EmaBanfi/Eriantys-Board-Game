
package it.polimi.ingsw.network.server.model;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Bag {
    private HashMap<StudentColor, Integer> students;

    private final int maxStudents = 26;
    public HashMap<StudentColor, Integer> get(){
        return students;
    }
    public Bag(){
        students = new HashMap<>();
        students.put(StudentColor.PURPLE, maxStudents);
        students.put(StudentColor.YELLOW, maxStudents);
        students.put(StudentColor.BLUE, maxStudents);
        students.put(StudentColor.GREEN, maxStudents);
        students.put(StudentColor.RED, maxStudents);
    }

    /**
     * This method is used to draw a certain number of students from the bag
     * @param studentsToDraw number of students to be drawn
     * @return An Arraylist of random students which size is equal to studentTobeDrawn
     * @throws LastStudentDrawnException if there are no more students of a certain color inside the bag
     */
    public ArrayList<StudentColor> draw(int studentsToDraw) throws LastStudentDrawnException {
        ArrayList<StudentColor> drawnStudents = new ArrayList<>();
        int i = 0;
        while (i < studentsToDraw) {
            int randIndex = new Random().nextInt(5);
            StudentColor randKey = StudentColor.values()[randIndex];
            int studentsNumber = students.get(randKey);
            if (studentsNumber != 0) {
                drawnStudents.add(randKey);
                i++;
                studentsNumber--;
                students.put(randKey, studentsNumber);
            }
            boolean empty = true;
            for(StudentColor color : students.keySet())
                if(students.get(color)!=0)
                    empty = false;
            if(empty) throw new LastStudentDrawnException();
        }
            return drawnStudents;
    }

    /**
     * put the students in the bag
     * @param studentsToAdd the students that have to be added to the bag
     */
    public void addStudents(ArrayList<StudentColor> studentsToAdd){
        for (StudentColor studentColor : studentsToAdd) {
            int studentsNumber = students.get(studentColor);
            studentsNumber++;
            students.put(studentColor, studentsNumber);

        }
    }

}
