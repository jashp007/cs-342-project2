/**
 * Represents a playing card with a suite and a value.
 * This class models the basic properties of a playing card used in games like Baccarat.
 */
public class Card {
    // Fields to store the suite and value of the card
    private final String suite;
    private final int value;

    /**
     * Constructs a Card with a specified suite and value.
     *
     * @param suite The suite of the card (e.g., "Hearts", "Spades").
     * @param value The value of the card (1 for Ace, 2-10 for numeric cards, 11 for Jack, 12 for Queen, 13 for King).
     */
    public Card(String suite, int value) {
        this.suite = suite;
        this.value = value;
    }

    /**
     * Returns the suite of the card.
     *
     * @return The suite of the card.
     */
    public String getSuite() {
        return suite;
    }

    /**
     * Returns the value of the card.
     *
     * @return The value of the card. Numeric values for number cards, 1 for Ace, 11 for Jack, 12 for Queen, and 13 for King.
     */
    public int getValue() {
        return value;
    }

    /**
     * Provides a string representation of the card, combining its suite and value.
     * For face cards (Jack, Queen, King) and Aces, their names are used instead of numbers.
     *
     * @return A string representing the card, like "Hearts Ace" or "Spades Queen".
     */
    @Override
    public String toString() {
        switch (value) {
            case 1: return suite + " Ace";
            case 11: return suite + " Jack";
            case 12: return suite + " Queen";
            case 13: return suite + " King";
            default: return suite + " " + value; // For numeric cards, the number is returned with the suite
        }
    }
}
