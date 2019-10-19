import javafx.application.Application;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GameTest {
    BaccaratGame bG;

    @BeforeEach
    void setUp() {
        bG = new BaccaratGame();
        bG.playerHand = new ArrayList<Card>();
        bG.bankerHand = new ArrayList<Card>();
        bG.currentBet = 100;
        bG.totalWinnings = 0;
    }

    @Test
    void testInit() {
        assertEquals("BaccaratGame", bG.getClass().getName(), "Failed to properly initialize from class");
    }

    @Test
    void testEvalWinningBetPlayerWin() {
        bG.playerWin = true;
        bG.choice = "Player";
        bG.playerHand.add(new Card("Spade", 8));
        bG.playerHand.add(new Card("Spade", 11));
        bG.bankerHand.add(new Card("Spade", 1));
        bG.bankerHand.add(new Card("Spade", 2));
        assertEquals(200,bG.evaluateWinnings(),"Wrong value returned");
    }

    @Test
    void testEvalWinningBetBankerWin() {
        bG.playerWin = true;
        bG.choice = "Banker";
        bG.playerHand.add(new Card("Spade", 1));
        bG.playerHand.add(new Card("Spade", 2));
        bG.bankerHand.add(new Card("Spade", 8));
        bG.bankerHand.add(new Card("Spade", 12));
        assertEquals(195, bG.evaluateWinnings(),"Wrong value returned");
    }

    @Test
    void testEvalWinningBetDrawWin() {
        bG.playerWin = true;
        bG.choice = "Draw";
        bG.playerHand.add(new Card("Spade", 8));
        bG.playerHand.add(new Card("Spade", 10));
        bG.bankerHand.add(new Card("Diamond", 8));
        bG.bankerHand.add(new Card("Diamond", 10));
        assertEquals(800, bG.evaluateWinnings(),"Wrong value returned");
    }

    @Test
    void testEvalWinningBetPlayerLost() {
        bG.playerWin = false;
        bG.choice = "Player";
        bG.playerHand.add(new Card("Spade", 8));
        bG.playerHand.add(new Card("Spade", 10));
        bG.bankerHand.add(new Card("Diamond", 8));
        bG.bankerHand.add(new Card("Diamond", 10));
        assertEquals(-100, bG.evaluateWinnings(),"Wrong value returned");
    }

}
