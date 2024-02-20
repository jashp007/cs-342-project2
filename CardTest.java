import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CardTest {

    /**
     * Tests the creation of a Card object to ensure it is instantiated correctly.
     * This test validates the constructor of the Card class.
     */
    @Test
    void testCardCreation() {
        Card card = new Card("Hearts", 1);
        assertNotNull(card, "Card should be created successfully");
    }

    /**
     * Tests the getSuite method of the Card class.
     * This test checks if the suit of the card is correctly returned.
     */
    @Test
    void testGetSuite() {
        Card card = new Card("Hearts", 1);
        assertEquals("Hearts", card.getSuite(), "Suite should be Hearts");
    }

    /**
     * Tests the getValue method of the Card class.
     * This test confirms that the card's value is returned correctly.
     */
    @Test
    void testGetValue() {
        Card card = new Card("Hearts", 1);
        assertEquals(1, card.getValue(), "Value should be 1");
    }

    /**
     * Tests the toString method for an Ace card.
     * This test checks if the toString method returns the correct string representation for an Ace.
     */
    @Test
    void testToStringAce() {
        Card card = new Card("Hearts", 1);
        assertEquals("Hearts Ace", card.toString(), "toString should return 'Hearts Ace' for Ace of Hearts");
    }

    /**
     * Tests the toString method for a numbered card.
     * This test validates that the toString method correctly formats cards with numeric values.
     */
    @Test
    void testToStringNumberCard() {
        Card card = new Card("Diamonds", 5);
        assertEquals("Diamonds 5", card.toString(), "toString should return 'Diamonds 5' for 5 of Diamonds");
    }

    /**
     * Tests the toString method for face cards (Jack, Queen, King).
     * This test ensures that the string representation for each type of face card is correct.
     */
    @Test
    void testToStringFaceCard() {
        Card card = new Card("Spades", 11); // Jack
        assertEquals("Spades Jack", card.toString(), "toString should return 'Spades Jack' for Jack of Spades");

        card = new Card("Clubs", 12); // Queen
        assertEquals("Clubs Queen", card.toString(), "toString should return 'Clubs Queen' for Queen of Clubs");

        card = new Card("Hearts", 13); // King
        assertEquals("Hearts King", card.toString(), "toString should return 'Hearts King' for King of Hearts");
    }
}
