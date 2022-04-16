package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.StudentColor;

import java.util.ArrayList;

public class CharacterCardGroup6 extends CharacterCard {
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();

    /**
     * implementation of the effect of the CharacterCard 7 and the CharacterCard 10; at the end increase the price of the CharacterCard
     * @param studentsToExchange1: for CharacterCard 7 represent the students taken from the CharacterCard; for CharacterCard 10 represent the students taken from the DiningHall
     * @param studentsToExchange2: for CharacterCard 7 represent the students taken from the Hall; for CharacterCard 10 represent the students taken from the Hall
     */
    public void activate(ArrayList<StudentColor> studentsToExchange1, ArrayList<StudentColor> studentsToExchange2) throws LastStudentDrawnException {
        // effect of characterCard n°7
        if (cardId == 7) {
            if (studentsOnCard.size() != 6)
                putStudentsOnCard();

            // can exchange max 3 students
            currentPlayer.getBoard().addStudentsToHall(studentsToExchange1);
            for (StudentColor student : studentsToExchange1)
                studentsOnCard.remove(student);
            studentsOnCard.addAll(studentsToExchange2);
            currentPlayer.getBoard().removeStudentsFromHall(studentsToExchange2);
        }

        // effect of characterCard n°10
        else if (cardId == 10)
            currentPlayer.getBoard().switchStudents(studentsToExchange1, studentsToExchange2);

        increasePrice();
    }

    /**
     * used to put 6 students on the CharacterCard n°7
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    public void putStudentsOnCard() throws LastStudentDrawnException {
        int numOfStudents = 6 - studentsOnCard.size();
        this.studentsOnCard.addAll(gameBoard.getBag().draw(numOfStudents));
    }

    public ArrayList<StudentColor> getStudentsOnCard() {
        return studentsOnCard;
    }

    public int countStudentColor(StudentColor color) {
        return (int) studentsOnCard.stream().filter(x -> x.equals(color)).count();
    }
}
