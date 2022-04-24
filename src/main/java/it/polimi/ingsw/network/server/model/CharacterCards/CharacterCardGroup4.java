package it.polimi.ingsw.network.server.model.CharacterCards;

import it.polimi.ingsw.Exceptions.LastStudentDrawnException;
import it.polimi.ingsw.network.server.model.Player;
import it.polimi.ingsw.network.server.model.StudentColor;

import java.util.ArrayList;

public class CharacterCardGroup4 extends CharacterCard {
    private StudentColor chosenColor;
    private final ArrayList<StudentColor> studentsOnCard = new ArrayList<>();

    /**
     * implementation of the effect of the CharacterCard 9, the CharacterCard 11 and the CharacterCard 12; at the end increase the price of the CharacterCard
     * @param color: indicate a student and it is used in different ways, depend on the effect of each CharacterCard
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    public void activate(StudentColor color) throws LastStudentDrawnException {
        // effect of characterCard n°9
        if (cardId == 9)
            gameBoard.addIgnoredColor(color);

        // effect of characterCard n°11
        else if (cardId == 11) {
            if (studentsOnCard.size() != 4)
                putStudentsOnCard();

            ArrayList<StudentColor> studentToAdd = new ArrayList<>();
            studentToAdd.add(color);

            currentPlayer.getBoard().getDiningHall().addStudents(studentToAdd);
            studentsOnCard.remove(color);
        }

        // else activate effect of characterCard°12
        else if (cardId == 12) {
            int withdrawnStudents = 0;
            for (Player player : gameBoard.getPlayers())
                withdrawnStudents = withdrawnStudents + player.getBoard().getDiningHall().removeStudent(color);

            ArrayList<StudentColor> students = new ArrayList<>();
            for (int i=0; i<withdrawnStudents; i++)
                students.add(color);

            gameBoard.getBag().addStudents(students);
        }

        increasePrice();
    }

    public ArrayList<StudentColor> getStudentsOnCard() {
        return studentsOnCard;
    }

    /**
     * used to put 4 students on the CharacterCard n°11
     * @throws LastStudentDrawnException called if the last student is taken from the bag
     */
    public void putStudentsOnCard() throws LastStudentDrawnException {
        int numOfStudents = 4 - studentsOnCard.size();
        this.studentsOnCard.addAll(gameBoard.getBag().draw(numOfStudents));
    }
}
