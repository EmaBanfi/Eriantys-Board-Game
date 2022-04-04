package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.model.StudentColor;

import java.util.ArrayList;

public class CharacterCardGroup1 extends CharacterCard {
    private ArrayList<StudentColor> studentsOnCard;

    /**
     * create a new CharacterCard n°1 and put on it 4 students; at the end increase the price of the CharacterCard
     * @throws LastStudentDrawnException : called if the last student is taken from the bag
     */
    public CharacterCardGroup1() throws LastStudentDrawnException {
        this.studentsOnCard.addAll(gameBoard.getBag().draw(4));
    }

    /**
     * implement the effect of the characterCard n°1
     * @param color: student that must be taken
     * @param island: island where the student must be put
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    public void activate(StudentColor color, int island) throws LastStudentDrawnException {
        gameBoard.getIsland(island).addStudent(color);
        studentsOnCard.remove(color);
        this.studentsOnCard.addAll(gameBoard.getBag().draw(1));

        increasePrice();
    }
}
