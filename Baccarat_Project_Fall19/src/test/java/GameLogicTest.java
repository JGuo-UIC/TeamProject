import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
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

    @Test
    void testHandTotal() {

    }

    @Test
    void testEvaluateBankerDraw() {

    }

    @Test
    void testEvaluatePlayerDraw() {

    }    
}