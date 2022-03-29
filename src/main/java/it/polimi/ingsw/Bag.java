
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
                if (students.isEmpty()) throw new LastStudentDrawnException();
            }
        }
            return drawnStudents;
    }

    public void addStudents(ArrayList<StudentColor> studentsToAdd){
        for(int i=0; i<studentsToAdd.size(); i++){
            int studentsNumber = students.get(studentsToAdd.get(i));
            studentsNumber++;
            students.put(studentsToAdd.get(i), studentsNumber);

        }
    }

}
