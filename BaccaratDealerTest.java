import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class BaccaratDealerTest {
    BaccaratDealer dealer;

    @BeforeEach
    void setUp() {
        // Before each test, create a new BaccaratDealer instance with 6 decks.
        // This ensures that each test starts with a fresh deck state.
        dealer = new BaccaratDealer(6);
    }

    @Test
    void testDeckSizeAfterGeneration() {
        // Verify that the total number of cards in the deck is correct after initialization.
        // Since there are 52 cards in a standard deck, 6 decks should total 312 cards.
        assertEquals(312, dealer.getDeckSize(), "Deck should have 312 cards for 6 standard decks");
    }

    @Test
    void testShuffleDeck() {
        // Test whether shuffling the deck maintains its size.
        // This is to ensure that shuffling does not inadvertently add or remove cards.
        dealer.shuffleDeck();
        assertEquals(312, dealer.getDeckSize(), "Deck size should remain constant after shuffling");
    }

    @Test
    void testBurnCards() {
        // Test the burnCards method, which removes a certain number of cards from the top of the deck.
        // This method simulates the 'burning' of cards as done in many card games before dealing.
        // The test checks if the deck size has reduced after burning cards.
        int initialDeckSize = dealer.getDeckSize();
        dealer.burnCards();
        assertTrue(dealer.getDeckSize() < initialDeckSize, "Deck size should decrease after burning cards");
    }

    @Test
    void testDealHand() {
        // Test the dealHand method, which should give a hand of 2 cards.
        // This test also checks if dealing a hand correctly reduces the deck's size by 2.
        ArrayList<Card> hand = dealer.dealHand();
        assertEquals(2, hand.size(), "A dealt hand should have 2 cards");
        assertEquals(310, dealer.getDeckSize(), "Deck size should decrease by 2 cards after dealing a hand");
    }

    @Test
    void testDrawOne() {
        // Test the drawOne method, which should take one card from the deck.
        // This test checks that the drawn card is not null and that the deck size is reduced by 1.
        Card drawnCard = dealer.drawOne();
        assertNotNull(drawnCard, "Drawn card should not be null");
        assertEquals(311, dealer.getDeckSize(), "Deck size should decrease by 1 after drawing a card");
    }

    @Test
    void testUniqueCardsInDeck() {
        // This test checks for the uniqueness of the cards in the deck.
        // It draws all cards from the deck, adding them to a Set (which does not allow duplicates).
        // If the Set size equals the initial deck size, it confirms all cards were unique.
        Set<Card> drawnCards = new HashSet<>();
        while (dealer.getDeckSize() > 0) {
            Card card = dealer.drawOne();
            if (card != null) {
                drawnCards.add(card);
            }
        }
        assertEquals(312, drawnCards.size(), "All cards in the deck should be unique and total 312 for 6 decks");
    }

}
