import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;


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
    void testWhoWonBanker() {
        ArrayList<Card> hand1, hand2;
        hand1 = new ArrayList<>();
        hand2 = new ArrayList<>();
        hand1.add(new Card("Spade", 2));
        hand1.add(new Card("Spade", 3));
        hand2.add(new Card("Spade", 5));
        hand2.add(new Card("Spade", 3));
        assertEquals("Banker", gLogic.whoWon(hand1, hand2), "Should return 'Banker'");
    }
    @Test
    void testWhoWonPlayer() {
        ArrayList<Card> hand1, hand2;
        hand1 = new ArrayList<>();
        hand2 = new ArrayList<>();
        hand1.add(new Card("Spade", 5));
        hand1.add(new Card("Spade", 3));
        hand2.add(new Card("Spade", 3));
        hand2.add(new Card("Spade", 3));
        assertEquals("Player", gLogic.whoWon(hand1, hand2), "Should return 'Player'");
    }
    @Test
    void testWhoWonTie() {
        ArrayList<Card> hand1, hand2;
        hand1 = new ArrayList<>();
        hand2 = new ArrayList<>();
        hand1.add(new Card("Spade", 3));
        hand1.add(new Card("Diamond", 3));
        hand2.add(new Card("Heart", 3));
        hand2.add(new Card("Club", 3));
        assertEquals("Draw", gLogic.whoWon(hand1, hand2), "Should return 'Draw'");
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

    @Nested
    class testEvaluateBankerDraw {
        ArrayList<Card> hand;
        Card playerCard;

        @BeforeEach
        void setup(){
            hand = new ArrayList<>();
        }

        @Test
        void testZero() {
            playerCard.setValue(5);
            hand.add(new Card("Spade", 0));
            hand.add(new Card("Diamond", 0));
            assertTrue(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testOne(){
            playerCard.setValue(5);
            hand.add(new Card("Spade", 1));
            hand.add(new Card("Diamond", 0));
            assertTrue(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testTwo(){
            playerCard.setValue(5);
            hand.add(new Card("Spade", 1));
            hand.add(new Card("Diamond", 1));
            assertTrue(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testThreeDrew() {
            playerCard.setValue(6);
            hand.add(new Card("Spade", 1));
            hand.add(new Card("Diamond", 2));
            assertTrue(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testFourDrew() {
            playerCard.setValue(6);
            hand.add(new Card("Spade", 2));
            hand.add(new Card("Diamond", 2));
            assertTrue(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testFiveDrew() {
            playerCard.setValue(6);
            hand.add(new Card("Spade", 3));
            hand.add(new Card("Diamond", 2));
            assertTrue(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testSixDrew() {
            playerCard.setValue(6);
            hand.add(new Card("Spade", 3));
            hand.add(new Card("Diamond", 3));
            assertTrue(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testFourNDrew() {
            playerCard.setValue(5);
            hand.add(new Card("Spade", 2));
            hand.add(new Card("Diamond", 2));
            assertFalse(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testFiveNDrew() {
            playerCard.setValue(5);
            hand.add(new Card("Spade", 3));
            hand.add(new Card("Diamond", 2));
            assertFalse(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testSixNDrew() {
            playerCard.setValue(5);
            hand.add(new Card("Spade", 3));
            hand.add(new Card("Diamond", 3));
            assertFalse(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testSeven() {
            playerCard.setValue(6);
            hand.add(new Card("Spade", 3));
            hand.add(new Card("Diamond", 4));
            assertFalse(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testEight() {
            playerCard.setValue(6);
            hand.add(new Card("Spade", 4));
            hand.add(new Card("Diamond", 4));
            assertFalse(gLogic.evaluateBankerDraw(hand, playerCard));
        }
        @Test
        void testNine() {
            playerCard.setValue(6);
            hand.add(new Card("Spade", 5));
            hand.add(new Card("Diamond", 4));
            assertFalse(gLogic.evaluateBankerDraw(hand, playerCard));
        }
    }
    @Nested
    class testEvaluatePlayerDraw{
        ArrayList<Card> hand;

        @BeforeEach
        void setup(){
            hand = new ArrayList<>();
        }
        @Test
        void testZero(){
            hand.add(new Card("Spade", 0));
            hand.add(new Card("Diamond", 0));
            assertTrue(gLogic.evaluatePlayerDraw(hand));
        }
        @Test
        void testOne(){
            hand.add(new Card("Spade", 1));
            hand.add(new Card("Diamond", 0));
            assertTrue(gLogic.evaluatePlayerDraw(hand));
        }
        @Test
        void testTwo(){
            hand.add(new Card("Spade", 1));
            hand.add(new Card("Diamond", 1));
            assertTrue(gLogic.evaluatePlayerDraw(hand));
        }
        @Test
        void testThree(){
            hand.add(new Card("Spade", 2));
            hand.add(new Card("Diamond", 1));
            assertTrue(gLogic.evaluatePlayerDraw(hand));
        }
        @Test
        void testFour(){
            hand.add(new Card("Spade", 2));
            hand.add(new Card("Diamond", 2));
            assertTrue(gLogic.evaluatePlayerDraw(hand));
        }
        @Test
        void testFive(){
            hand.add(new Card("Spade", 3));
            hand.add(new Card("Diamond", 2));
            assertTrue(gLogic.evaluatePlayerDraw(hand));
        }
        @Test
        void testSix(){
            hand.add(new Card("Spade", 3));
            hand.add(new Card("Diamond", 3));
            assertFalse(gLogic.evaluatePlayerDraw(hand));
        }
    }
}