package it.polimi.ingsw.network.server.model;

public enum StudentColor {
    BLUE,
    PURPLE,
    YELLOW,
    RED,
    GREEN;

    public static StudentColor getStudentFromString(String student){
        for(StudentColor color : StudentColor.values()){
            if(color.toString().equals(student.toUpperCase())){
                return color;
            }
        }
        return null;
    }
}
