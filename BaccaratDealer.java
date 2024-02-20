import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// Class representing a Baccarat card dealer
public class BaccaratDealer {
    private ArrayList<Card> deck; // List to hold the deck of cards
    private final Random random; // Random object for shuffling the deck

    // Constructor to initialize the dealer with a specified number of decks
    public BaccaratDealer(int numDecks) {
        random = new Random();
        generateDeck(numDecks); // Generate the initial deck
    }

    // Generates a deck of cards consisting of the specified number of decks
    public void generateDeck(int numDecks) {
        deck = new ArrayList<>();
        String[] suites = {"Hearts", "Diamonds", "Clubs", "Spades"}; // The four suites in a deck

        // Create cards for each suite and add them to the deck
        for (int i = 0; i < numDecks; i++) {
            for (String suite : suites) {
                for (int j = 1; j <= 13; j++) { // 1-13 for Ace through King
                    deck.add(new Card(suite, j));
                }
            }
        }
        shuffleDeck(); // Shuffle the deck after generation
    }

    // Shuffles the deck using the Random object
    public void shuffleDeck() {
        Collections.shuffle(deck, random);
    }

    // Method to "burn" cards from the deck, typically done at the beginning of a new shoe or round
    public void burnCards() {
        if (!deck.isEmpty()) {
            int firstCardValue = deck.get(0).getValue();
            firstCardValue = Math.min(firstCardValue, 10); // Face cards count as 10

            // Remove the first card and then "firstCardValue" number of cards from the end of the deck
            for (int i = 0; i <= firstCardValue && !deck.isEmpty(); i++) {
                deck.remove(deck.size() - 1);
            }
        }
    }

    // Deals a hand of two cards from the deck
    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();
        if (deck.size() < 2) {
            // If not enough cards to deal a new hand, return an empty hand (or consider reshuffling)
            return hand;
        }
        // Draw two cards from the end of the deck and add them to the hand
        hand.add(deck.remove(deck.size() - 1));
        hand.add(deck.remove(deck.size() - 1));
        return hand;
    }

    // Draws a single card from the deck
    public Card drawOne() {
        if (deck.isEmpty()) {
            // If no cards left to draw, return null (or consider reshuffling)
            return null;
        }
        // Remove and return the last card in the deck
        return deck.remove(deck.size() - 1);
    }

    // Method to get the current size of the deck, useful for testing or UI updates
    public int getDeckSize() {
        return deck.size();
    }
}
