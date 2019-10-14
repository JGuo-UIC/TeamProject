import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class GameLogicTest {
    BaccaratGameLogic gLogic;

    @BeforeAll
    void setup() {
        gLogic = new BaccaratGameLogic();
    }

    @Test
    void testInit() {
        assertEquals("BaccaratGameLogic", gLogic.getClass().getName(), "Failed to correctly initialize game logic");
    }

    @Test
    void testWhoWon() {

    }

    @Nested
    class testHandTotal {
        ArrayList<Card> hand;

        @BeforeEach
        void setup() {
            hand = new ArrayList<>();
        }

        @Test
        void testLessThanTen() {             
            hand.add(new Card("Spades", 2));
            hand.add(new Card("Spades", 7));
            assertEquals(9, gLogic.handTotal(hand), "HandTotal returned the wrong value");
        }

        @Test
        void testEqualTen() {
            hand.add(new Card("Spades", 3));
            hand.add(new Card("Spades", 7));
            assertEquals(0, gLogic.handTotal(hand), "HandTotal returned the wrong value");
        }

        
        @Test
        void testMoreThanTen() {
            hand.add(new Card("Spades", 9));
            hand.add(new Card("Spades", 8));
            assertEquals(7, gLogic.handTotal(hand), "HandTotal returned the wrong value");
        }
    }

    @Test
    void testEvaluateBankerDraw() {

    }

    @Test
    void testEvaluatePlayerDraw() {

    }    
}