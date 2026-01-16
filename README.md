# Blackjack 2.0 (README UPDATED 1/8/25)

Blackjack 2.0 is a gui-based simulation of the classic casino card game, Blackjack. The game allows a single player to play against the dealer with standard Blackjack rules, including hitting, standing, busting, and scoring. The program supports advanced mechanics such as betting, double down, surrender, checking for blackjack, and handling bust scenarios.

# Features 

* Standard Blackjack Mechanics:
  * Deal cards to both the player and dealer.
  * Hit, Stand, and check for blackjack 
  * Dealer reveals their hole card when the player's turn ends
* Advanced Gameplay Options:
  * **Double Down**: Double your bet and receive exactly one more card (only available after initial two cards). The double down card is visually displayed horizontally to indicate the action.
  * **Surrender**: Forfeit half your bet and end the round early (only available after initial two cards). The dealer's hole card is revealed when surrendering.
* Betting System:
  * Place bets before each round (minimum $25)
  * Supports decimal values for precise betting (e.g., $12.50 on surrender)
  * Winnings are doubled for wins, and returned for pushes
  * Blackjack pays 2.5x the bet
* UI Features:
  * Large, easy-to-read fonts and buttons
  * Spacious window (1800x1000) with scaled card graphics
  * Visual indicators for double down (horizontal card orientation)
  * Clear balance display with 2 decimal precision

# How to Play

* Setup
  * The player starts with $500.00
  * Deck is shuffled once program is started and when the Deck is less than 20 cards
* Gameplay
  * Place a bet (minimum $25). 
  * After receiving your initial two cards, choose from:
    * **Hit**: Take another card
    * **Stand**: End your turn and let the dealer play
    * **Double Down**: Double your bet and take exactly one more card (requires sufficient balance)
    * **Surrender**: Forfeit half your bet and end the round (only available after first two cards)
  * Try to get a hand value as close to 21 as possible without exceeding it.
* Dealer's Turn:
  * The dealer must hit until their hand value is at least 17. 
  * If the dealer busts, the player automatically wins.
  * The dealer's hole card is revealed when the player stands, surrenders, or the round ends.

# To Do:
* Add split functionality

# Discord - itravis
