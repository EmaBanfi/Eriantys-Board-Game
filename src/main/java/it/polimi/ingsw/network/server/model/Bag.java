
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
        StudentColor randKey;
        ArrayList<StudentColor> drawnStudents = new ArrayList<>();
        ArrayList<StudentColor> drawableStudents= new ArrayList<>();
        for(StudentColor color : students.keySet()){
            if(students.get(color)>0){
                drawableStudents.add(color);
            }
        }
        int i = 0;
        while (i < studentsToDraw) {
            if(drawableStudents.size()>1) {
                int randIndex = new Random().nextInt(drawableStudents.size() - 1);
                randKey = drawableStudents.get(randIndex);
            }
            else
                randKey=drawableStudents.get(0);
            drawnStudents.add(randKey);
            int studentsNumber = students.get(randKey);
            studentsNumber--;
            students.put(randKey, studentsNumber);
            if(studentsNumber==0){
                drawableStudents.remove(randKey);
                if(drawableStudents.size()==0)
                    throw new LastStudentDrawnException();
            }
            i++;
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
