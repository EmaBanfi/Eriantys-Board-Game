package it.polimi.ingsw.model.CharacterCards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardGroup2Test {

    CharacterCard card;
    @BeforeEach
    void setUp() {
        card=new CharacterCardGroup2();
    }

    @AfterEach
    void tearDown() {
        card=null;
    }

    @Test
    void activate() {
    }
}