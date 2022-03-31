package it.polimi.ingsw;

import java.util.ArrayList;

public class Cloud extends Hall{
    private final int maxStudents;

    public Cloud(int maxStudents){
        super();
        this.maxStudents = maxStudents;
    }

    public int getMaxStudents(){
        return maxStudents;
    }

    public void refillStudents(ArrayList<StudentColor> studentsToAdd){
        removeStudents(getStudents());
        addStudents(studentsToAdd);
    }
}


