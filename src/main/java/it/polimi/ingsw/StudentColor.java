package it.polimi.ingsw;

public enum StudentColor {
    BLUE("Blue"),
    PURPLE("Purple"),
    YELLOW("Yellow"),
    RED("Red"),
    GREEN("Green");

    private final String typeStudentColor;

    StudentColor(String typeStudentColor) {
        this.typeStudentColor = typeStudentColor;
    }

    public String getTypeStudentColor() {
        return typeStudentColor;
    }
}
