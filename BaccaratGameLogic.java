import java.util.ArrayList;

public class BaccaratGameLogic {

    /**
     * Determines the winner of a round of Baccarat based on the hands of the player and banker.
     *
     * @param playerHand The hand of the player.
     * @param bankerHand The hand of the banker.
     * @return A String indicating the winner - "Player", "Banker", or "Draw".
     */
    public String whoWon(ArrayList<Card> playerHand, ArrayList<Card> bankerHand) {
        int playerTotal = handTotal(playerHand);
        int bankerTotal = handTotal(bankerHand);
        // The game is won by the party with the higher hand total.
        // In Baccarat, the highest possible hand value is 9.
        if (playerTotal > bankerTotal) {
            return "Player";
        } else if (bankerTotal > playerTotal) {
            return "Banker";
        } else {
            return "Draw"; // Equal totals result in a draw.
        }
    }

    /**
     * Calculates the total value of a hand in Baccarat.
     * Face cards and 10s are worth 0, Aces are worth 1, and other cards are worth their face value.
     * The total value is the sum of card values modulo 10.
     *
     * @param hand The hand to calculate the total for.
     * @return The Baccarat total of the hand.
     */
    public int handTotal(ArrayList<Card> hand) {
        int total = 0;
        for (Card card : hand) {
            if (card.getValue() > 9) { // For 10, Jack, Queen, King
                continue; // Worth zero points in Baccarat, so they are skipped in the calculation.
            }
            total += card.getValue(); // Aces count as 1, and other cards count as their number value.
        }
        return total % 10; // Only the rightmost digit of the total is used in Baccarat.
    }

    /**
     * Checks if a "natural win" has occurred, which happens when either the player or banker has a total of 8 or 9.
     * If a natural win occurs, the round ends immediately.
     *
     * @param playerHand The player's hand.
     * @param bankerHand The banker's hand.
     * @return True if a natural win has occurred; otherwise, false.
     */
    public boolean checkNaturalWin(ArrayList<Card> playerHand, ArrayList<Card> bankerHand) {
        int playerTotal = handTotal(playerHand);
        int bankerTotal = handTotal(bankerHand);
        return playerTotal >= 8 || bankerTotal >= 8; // Natural win is a total of 8 or 9 for either hand.
    }

    /**
     * Determines if the player should draw a third card.
     * According to Baccarat rules, the player draws if the total is 5 or less.
     *
     * @param hand The player's hand.
     * @return True if the player should draw a card; otherwise, false.
     */
    public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
        return handTotal(hand) <= 5; // Player draws if total is 5 or less.
    }

    /**
     * Determines if the banker should draw a third card, based on complex Baccarat rules.
     * The decision depends on the banker's current total and the value of the player's third card (if drawn).
     *
     * @param bankerHand The banker's hand.
     * @param playerThirdCard The player's third card, if any. Null if the player did not draw a third card.
     * @return True if the banker should draw a card; otherwise, false.
     */
    public boolean evaluateBankerDraw(ArrayList<Card> bankerHand, Card playerThirdCard) {
        int bankerTotal = handTotal(bankerHand);
        // Banker's action depends on their total and potentially the player's third card.
        if (bankerTotal >= 7) {
            return false; // Banker stands on totals of 7, 8, or 9.
        }
        if (bankerTotal <= 2) {
            return true; // Banker always draws on totals of 0, 1, or 2.
        }
        // If the player did not draw a third card, the banker's action depends solely on their total.
        if (playerThirdCard == null) {
            return bankerTotal <= 5; // Banker draws on totals of 3, 4, or 5 and stands on 6.
        }

        int playerThirdValue = playerThirdCard.getValue();
        playerThirdValue = (playerThirdValue > 10) ? 0 : playerThirdValue; // Face cards (including 10s) count as 0.

        // Banker's drawing rules when the player has drawn a third card.
        switch (bankerTotal) {
            case 3:
                return (playerThirdValue != 8); // Banker draws unless the player's third card is an 8.
            case 4:
                return (playerThirdValue >= 2 && playerThirdValue <= 7); // Banker draws on player's 2-7.
            case 5:
                return (playerThirdValue >= 4 && playerThirdValue <= 7); // Banker draws on player's 4-7.
            case 6:
                return (playerThirdValue == 6 || playerThirdValue == 7); // Banker draws on player's 6-7.
            default:
                return false; // Banker stands in all other scenarios.
        }
    }
}
