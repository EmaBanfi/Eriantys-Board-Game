
package it.polimi.ingsw;
import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Exceptions.LastStudentDrawnException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Bag {
    private HashMap<StudentColor, Integer> students;

   public HashMap<StudentColor, Integer> get(){
        return students;
    }
    public Bag(){
        students = new HashMap<>();
        students.put(StudentColor.PURPLE, 26);
        students.put(StudentColor.YELLOW, 26);
        students.put(StudentColor.BLUE, 26);
        students.put(StudentColor.GREEN, 26);
        students.put(StudentColor.RED, 26);
    }

    /**
     * This method is used to draw a certain number of students from the bag
     * @param studentsToDraw: number of students to be drawn
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
                if (studentsNumber==0) throw new LastStudentDrawnException();
            }
        }
            return drawnStudents;
    }

    public void addStudents(ArrayList<StudentColor> studentsToAdd){
        for (StudentColor studentColor : studentsToAdd) {
            int studentsNumber = students.get(studentColor);
            studentsNumber++;
            students.put(studentColor, studentsNumber);

        }
    }

}
