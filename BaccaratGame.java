/**
 * Baccarat Game
 *
 * This JavaFX application simulates a Baccarat card game, allowing the user to place bets
 * on either the player's hand, the banker's hand, or a draw. The game follows traditional
 * Baccarat rules, including card dealing, natural win checks, and calculating winnings
 * with banker commission. The application features a user-friendly interface for interactive
 * gameplay.
 *
 * Features:
 * - Start a new game or round
 * - Place bets on player, banker, or draw
 * - Animated gameplay with results display
 * - Option to restart the game or exit
 *
 * Author: Dishant Desle
 * Author: Jash Patel
 * Date: 10/29/2023
 *
 * Note: This project is part of CS342/Baccarat Game
 */









// Import statements for JavaFX components, animations, and standard Java utilities
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import java.util.ArrayList;

// Main class extending from JavaFX's Application class for GUI applications
 public class BaccaratGame extends Application {
	// Game-related attributes
	private BaccaratDealer theDealer; // Dealer of the game
	private BaccaratGameLogic gameLogic; // Logic handler for Baccarat rules
	private ArrayList<Card> playerHand; // Holds player's cards
	private ArrayList<Card> bankerHand; // Holds banker's cards
	private double currentBet; // Current bet amount placed by the player
	private double totalWinnings = 0; // Total amount of winnings

	// JavaFX UI components for displaying game state and accepting user input
	private Label playerCardsLabel;
	private Label bankerCardsLabel;
	private Label resultLabel; // Displays results of each round
	private Label winningsLabel; // Displays total winnings
	private TextField betAmount; // Input for the bet amount
	private ToggleGroup betChoices; // Group for bet type selection (Player, Banker, Draw)

	// Constants for the game configuration
	private static final int NUM_DECKS = 6; // Number of decks used in the game
	private static final double BANKER_COMMISSION = 0.05; // Commission on banker wins

	// Entry point of the JavaFX application
	public static void main(String[] args) {
		launch(args);
	}

	// Start method to set up the primary stage (main window) of the application
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Baccarat Game"); // Setting the title of the window
		Scene startScene = startScreen(primaryStage); // Creating the start screen scene
		primaryStage.setScene(startScene); // Setting the start scene in the stage
		primaryStage.show(); // Displaying the stage
	}

	// Method to create the initial start screen
	private Scene startScreen(Stage primaryStage) {
		Label welcomeLabel = new Label("Welcome to Baccarat!"); // Welcome label
		Button startGameBtn = new Button("Start Game"); // Button to start the game
		// Event handler for button click to change scene to the game scene
		startGameBtn.setOnAction(e -> primaryStage.setScene(gameScene()));

		// Layout setup using a vertical box layout with specified spacing and padding
		VBox layout = new VBox(20, welcomeLabel, startGameBtn);
		layout.setPadding(new Insets(100, 50, 100, 50)); // Padding around the VBox
		layout.setAlignment(javafx.geometry.Pos.CENTER); // Center alignment of children

		return new Scene(layout, 400, 600); // Returning the constructed scene
	}

	// Method to create the main game scene
	private Scene gameScene() {
		// Initialization of game components and dealer
		theDealer = new BaccaratDealer(NUM_DECKS);
		gameLogic = new BaccaratGameLogic();

		// Setup UI components for player and banker card display
		playerCardsLabel = new Label("Player's Cards: ");
		HBox playerArea = new HBox(10, playerCardsLabel);
		playerArea.setPadding(new Insets(10));

		bankerCardsLabel = new Label("Banker's Cards: ");
		HBox bankerArea = new HBox(10, bankerCardsLabel);
		bankerArea.setPadding(new Insets(10));

		// Setup for betting interface: input field and radio buttons
		betAmount = new TextField();
		betAmount.setPromptText("Enter bet amount");
		betChoices = new ToggleGroup();
		RadioButton betPlayer = new RadioButton("Player");
		betPlayer.setToggleGroup(betChoices);
		RadioButton betBanker = new RadioButton("Banker");
		betBanker.setToggleGroup(betChoices);
		RadioButton betDraw = new RadioButton("Draw");
		betDraw.setToggleGroup(betChoices);
		VBox bettingArea = new VBox(10, betAmount, betPlayer, betBanker, betDraw);
		bettingArea.setPadding(new Insets(10));

		// Button to start a round and its event handler
		Button startRoundBtn = new Button("Start Round");
		startRoundBtn.setOnAction(e -> startRound());
		HBox controlArea = new HBox(10, startRoundBtn);
		controlArea.setPadding(new Insets(10));

		// Labels for displaying game results and total winnings
		resultLabel = new Label("Results will be shown here.");
		winningsLabel = new Label("Total Winnings: $0.0");
		VBox resultArea = new VBox(10, resultLabel, winningsLabel);
		resultArea.setPadding(new Insets(10));

		// Menu setup for additional options like exit and fresh start
		Menu optionsMenu = new Menu("Options");
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setOnAction(e -> System.exit(0));
		MenuItem freshStartItem = new MenuItem("Fresh Start");
		freshStartItem.setOnAction(e -> freshStart());
		optionsMenu.getItems().addAll(exitItem, freshStartItem);
		MenuBar menuBar = new MenuBar(optionsMenu);

		// Main layout comprising all the above elements
		VBox mainLayout = new VBox(menuBar, playerArea, bankerArea, bettingArea, controlArea, resultArea);
		mainLayout.setPadding(new Insets(20));

		return new Scene(mainLayout, 400, 600); // Scene with the main game layout
	}

	// Method to handle the start of a new game round
	private void startRound() {
		// Check if bet amount is entered and a bet option is selected
		RadioButton selectedRadioButton = (RadioButton) betChoices.getSelectedToggle();
		if (selectedRadioButton == null || betAmount.getText().isEmpty()) {
			resultLabel.setText("Please enter bet amount and choose a bet option.");
			return;
		}

		// Parsing the bet amount and handling incorrect format
		try {
			currentBet = Double.parseDouble(betAmount.getText());
		} catch (NumberFormatException e) {
			resultLabel.setText("Please enter a valid bet amount.");
			return;
		}

		// Get the selected bet choice (Player, Banker, or Draw)
		String betChoice = selectedRadioButton.getText();

		// Game logic for dealing cards and setting up the game state
		theDealer.burnCards(); // Burn the top card as per Baccarat rules
		playerHand = theDealer.dealHand(); // Deal cards to player
		bankerHand = theDealer.dealHand(); // Deal cards to banker
		updateCardDisplay(); // Update the UI to show dealt cards

		// Setting up animations to handle game flow with delays
		PauseTransition pause1 = new PauseTransition(Duration.seconds(2));
		PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
		PauseTransition pause3 = new PauseTransition(Duration.seconds(2));

		// Logic for the first pause: check for natural win or evaluate player's draw
		pause1.setOnFinished(e -> {
			if (gameLogic.checkNaturalWin(playerHand, bankerHand)) {
				resolveBet(betChoice, gameLogic.whoWon(playerHand, bankerHand));
				return;
			}
			if (gameLogic.evaluatePlayerDraw(playerHand)) {
				playerHand.add(theDealer.drawOne()); // Draw an additional card for player
				updateCardDisplay(); // Update UI with new card
			}
			pause2.play(); // Proceed to next pause
		});

		// Logic for the second pause: evaluating banker's draw
		pause2.setOnFinished(e -> {
			Card playerThirdCard = (playerHand.size() > 2) ? playerHand.get(2) : null; // Check for player's third card
			if (gameLogic.evaluateBankerDraw(bankerHand, playerThirdCard)) {
				bankerHand.add(theDealer.drawOne()); // Draw an additional card for banker
				updateCardDisplay(); // Update UI with new card
			}
			pause3.play(); // Proceed to final pause
		});

		// Logic for the third pause: resolve the round based on final hands
		pause3.setOnFinished(e -> resolveBet(betChoice, gameLogic.whoWon(playerHand, bankerHand)));

		pause1.play(); // Start the first pause

    }

	// Method to update the display of player and banker cards
	private void updateCardDisplay() {
		playerCardsLabel.setText("Player's Cards: " + cardsToString(playerHand));
		bankerCardsLabel.setText("Banker's Cards: " + cardsToString(bankerHand));
	}

	// Method to handle betting outcome, updating winnings and result display
	private void resolveBet(String betChoice, String winner) {
		double payout = 0;
		boolean isWinner = winner.equals(betChoice);
		if (isWinner && "Banker".equals(winner)) {
			payout = currentBet * (1 - BANKER_COMMISSION); // Deduct commission for banker bet
		} else if (isWinner) {
			payout = currentBet; // Full payout for winning on other bets
		}

		// Updating UI based on the outcome of the round
		if (isWinner) {
			totalWinnings += payout; // Increase total winnings for win
			// Displaying result of the round with winning amount
			resultLabel.setText(String.format("Player Total: %d Banker Total: %d\n%s wins\nCongrats! You bet %s! You win $%.2f!",
					gameLogic.handTotal(playerHand), gameLogic.handTotal(bankerHand), winner, betChoice, payout));
		} else if ("Draw".equals(winner)) {
			// Special handling for draw result when not bet on draw
			resultLabel.setText(String.format("Player Total: %d Banker Total: %d\nIt's a Draw! You get your bet back!",
					gameLogic.handTotal(playerHand), gameLogic.handTotal(bankerHand)));
		} else {
			totalWinnings -= currentBet; // Deduct bet amount from total winnings for loss
			// Displaying result of the round for loss
			resultLabel.setText(String.format("Player Total: %d Banker Total: %d\n%s wins\nSorry, you bet %s! You lost your bet!",
					gameLogic.handTotal(playerHand), gameLogic.handTotal(bankerHand), winner, betChoice));
		}
		winningsLabel.setText(String.format("Total Winnings: $%.2f", totalWinnings)); // Updating total winnings display
	}

	// Method to reset the game to initial state, clearing winnings and resetting UI
	private void freshStart() {
		totalWinnings = 0; // Reset total winnings to zero
		winningsLabel.setText("Total Winnings: $0.0"); // Reset winnings display
		resultLabel.setText("Results will be shown here."); // Reset results label
		playerCardsLabel.setText("Player's Cards: "); // Reset player's cards display
		bankerCardsLabel.setText("Banker's Cards: "); // Reset banker's cards display

		// Clear the bet amount input field
		betAmount.clear();

		// Deselect any selected betting option
		if (betChoices.getSelectedToggle() != null) {
			betChoices.getSelectedToggle().setSelected(false); // Deselect chosen bet option
		}
	}

	// Method to convert a list of Card objects into a String for display
	private String cardsToString(ArrayList<Card> hand) {
		StringBuilder handString = new StringBuilder();
		for (Card card : hand) {
			handString.append(card.toString()).append(" "); // Append each card's string representation
		}
		return handString.toString().trim(); // Return the concatenated string of cards
	}
}
