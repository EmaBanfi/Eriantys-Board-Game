package it.polimi.ingsw;

public enum RequiredInfo {
    STUDENTCOLOR("StudentColor"),
    ISLAND("Island"),
    CLOUD("Cloud");

    private final String typeRequiredInfo;

    RequiredInfo(String typeRequiredInfo) {
        this.typeRequiredInfo = typeRequiredInfo;
    }

    public String getTypeStudentColor() {
        return typeRequiredInfo;
    }
}
