package it.polimi.ingsw.network.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentColorTest {

    @Test
    void getStudentFromString() {
        for(StudentColor color : StudentColor.values() ){
            assertEquals(color, StudentColor.getStudentFromString(color.toString()));
        }
    }
}