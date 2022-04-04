package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.model.StudentColor;

import java.util.ArrayList;

public class CharacterCardGroup6 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard;

    /**
     * create the CharacterCard 5 or 6 and put on it 6 students
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    public CharacterCardGroup6() throws LastStudentDrawnException {
        this.studentsOnCard = new ArrayList<>();
        studentsOnCard.addAll(gameBoard.getBag().draw(6));
    }

    /**
     * implementation of the effect of the CharacterCard 7 and the CharacterCard 10; at the end increase the price of the CharacterCard
     * @param studentsToExchange1: for CharacterCard 7 represent the students taken from the CharacterCard; for CharacterCard 10 represent the students taken from the DiningHall
     * @param studentsToExchange2: for CharacterCard 7 represent the students taken from the Hall; for CharacterCard 10 represent the students taken from the Hall
     */
    public void activate(ArrayList<StudentColor> studentsToExchange1, ArrayList<StudentColor> studentsToExchange2) {
        // effect of characterCard n°7
        if (cardId == 7) {
            // can exchange max 3 students
            currentPlayer.getBoard().addStudentsToHall(studentsToExchange1);
            studentsOnCard.removeAll(studentsToExchange1);
            studentsOnCard.addAll(studentsToExchange2);
            currentPlayer.getBoard().removeStudentsFromHall(studentsToExchange2);
        }

        // effect of characterCard n°10
        else if (cardId == 10)
            currentPlayer.getBoard().switchStudents(studentsToExchange1, studentsToExchange2);

        increasePrice();
    }
}
