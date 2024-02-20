import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

class BaccaratGameLogicTest {
    final BaccaratGameLogic logic = new BaccaratGameLogic();

    /**
     * Tests the handTotal method to ensure it correctly calculates the total value of a Baccarat hand.
     * In this test, the hand consists of an Ace (value 1) and a 9, which should total 0 in Baccarat.
     */
    @Test
    void testHandTotal() {
        ArrayList<Card> hand = new ArrayList<>(Arrays.asList(new Card("Hearts", 1), new Card("Diamonds", 9)));
        assertEquals(0, logic.handTotal(hand), "Hand total should be 0 (Ace + 9)");
    }

    /**
     * Tests the whoWon method. In this scenario, the player's hand (total of 5) should beat the banker's hand (total of 4).
     * The player's hand consists of 2 and 3, while the banker's hand has 10 (value 0) and 4.
     */
    @Test
    void testWhoWon() {
        ArrayList<Card> playerHand = new ArrayList<>(Arrays.asList(new Card("Hearts", 2), new Card("Diamonds", 3)));
        ArrayList<Card> bankerHand = new ArrayList<>(Arrays.asList(new Card("Spades", 10), new Card("Clubs", 4)));
        assertEquals("Player", logic.whoWon(playerHand, bankerHand), "Player should win with a total of 5 against 4");
    }

    /**
     * Tests the evaluatePlayerDraw method to confirm the player will draw a card with a hand total of 5 or less.
     * Here, the player's hand has a total of 5 (2 and 3), so they should draw another card.
     */
    @Test
    void testEvaluatePlayerDrawWithFiveOrLess() {
        ArrayList<Card> hand = new ArrayList<>(Arrays.asList(new Card("Hearts", 2), new Card("Clubs", 3)));
        assertTrue(logic.evaluatePlayerDraw(hand), "Player should draw with a total of 5 or less");
    }

    /**
     * Tests the evaluatePlayerDraw method to ensure the player does not draw a card when their hand total is more than 5.
     * In this test, the player's hand totals 1 (7 and 4, since only the last digit of the sum counts), so they should still draw.
     */
    @Test
    void testEvaluatePlayerDrawWithMoreThanFive() {
        ArrayList<Card> hand = new ArrayList<>(Arrays.asList(new Card("Hearts", 7), new Card("Clubs", 4)));
        assertTrue(logic.evaluatePlayerDraw(hand), "Player should draw with a total of 1");
    }
}
