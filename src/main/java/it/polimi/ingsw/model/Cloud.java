package it.polimi.ingsw.model;

public class Cloud extends Hall{
    private final int maxStudents;

    public Cloud(int maxStudents){
        super();
        this.maxStudents = maxStudents;
    }

    public int getMaxStudents(){
        return maxStudents;
    }

}


