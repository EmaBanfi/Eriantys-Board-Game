package it.polimi.ingsw.model;

import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.server.model.Bag;
import it.polimi.ingsw.network.server.model.StudentColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag;

    @BeforeEach
    void setUp(){bag = new Bag();}

    @AfterEach
    void tearDown(){bag = null;}

    @Test
    void get() {
    }

    /**
     *  tests if the LastStudentDrawnException is launched correctly after drawing all students from the bag
     */
    @Test
    void draw2(){
        for (StudentColor color : bag.get().keySet()){
            bag.get().put(color, 1);
        }
        boolean error = false;
        try {
            bag.draw(5);
        } catch (LastStudentDrawnException e) {
            error = true;
        }
        assertTrue(error);
    }

    /**
     * tests che correct drawing method
     */
    @Test
    void draw1() {
        ArrayList<StudentColor> drawnStudents = new ArrayList<>();
        int yellowStudents = 0;
        int redStudents = 0;
        int purpleStudents = 0;
        int greenStudents = 0;
        int blueStudents = 0;
        try{
            drawnStudents.addAll(bag.draw(56));
        }catch(LastStudentDrawnException e){e.printStackTrace();}
        for (StudentColor s : drawnStudents) {
            if(s.equals(StudentColor.YELLOW))
                yellowStudents++;
            else if(s.equals(StudentColor.RED))
                redStudents++;
            else if(s.equals(StudentColor.PURPLE))
                purpleStudents++;
            else if(s.equals(StudentColor.GREEN))
                greenStudents++;
            else
                blueStudents++;
        }
        for(StudentColor color : bag.get().keySet()){
            if(color.equals(StudentColor.YELLOW))
                assertEquals(26-yellowStudents, bag.get().get(color));
            else if(color.equals(StudentColor.RED))
                assertEquals(26-redStudents, bag.get().get(color));
            else if(color.equals(StudentColor.PURPLE))
                assertEquals(26-purpleStudents, bag.get().get(color));
            else if(color.equals(StudentColor.GREEN))
                assertEquals(26-greenStudents, bag.get().get(color));
            else
                assertEquals(26-blueStudents, bag.get().get(color));
        }
    }

    @Test
    void addStudents() {
        Bag bag = new Bag();
        ArrayList<StudentColor> studentsToAdd = new ArrayList<>();
        try{
            studentsToAdd.addAll(bag.draw(45));
        }catch(LastStudentDrawnException e){e.printStackTrace();}
        bag.addStudents(studentsToAdd);
        for (StudentColor s: bag.get().keySet()) {
            assertEquals(26, bag.get().get(s));
        }
    }
}