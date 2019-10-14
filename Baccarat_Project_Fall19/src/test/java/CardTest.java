import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CardTest {        
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12,13})
    void testSpadesCard(int arg) {
        Card card1 = new Card("Spades", arg);
        assertEquals("Spades", card1.getSuite(), "GetSuite returned wrong suit");
        assertEquals(arg, card1.getValue(), "GetValue returned wrong value");
    
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12,13})
    void testDiamondsCard(int arg) {
        Card card1 = new Card("Diamonds", arg);
        assertEquals("Diamonds", card1.getSuite(), "GetSuite returned wrong suit");
        assertEquals(arg, card1.getValue(), "GetValue returned wrong value");
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12,13})
    void testClubsCard(int arg) {
        Card card1 = new Card("Clubs", arg);
        assertEquals("Clubs", card1.getSuite(), "GetSuite returned wrong suit");
        assertEquals(arg, card1.getValue(), "GetValue returned wrong value");
    }    

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 1999999999, 14, 103, 1324, -12})
    void testSetFaultyValue(int arg) {
        Card card1 = new Card("Spades", 1);        
        assertFalse(card1.setValue(arg), "SetValue returned true for invalid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "Spade", "Hello", "hearts", "Diamonds "})
    void testSetFaultySuit(String arg) {
        Card card1 = new Card("Hearts", 1);        
        assertFalse(card1.setSuite(arg), "SetSuite returned true for invalid suit");
    }


}