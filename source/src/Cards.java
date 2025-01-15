import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Cards
{

    int topIndex = 0;
    //Two 52 Card Deck
    private final String[] cards = {
            //first deck
            "2-S", "2-D", "2-H", "2-S", "3-C", "3-D", "3-H",
            "3-S", "4-C", "4-D", "4-H", "4-S", "5-C", "5-D",
            "5-H", "5-S", "6-C", "6-D", "6-H", "6-S", "7-C",
            "7-D", "7-H", "7-S", "8-C", "8-D", "8-H", "8-S",
            "9-C", "9-D", "9-H", "9-S", "10-C", "10-D", "10-H",
            "10-S", "A-C", "A-D", "A-H", "A-S", "J-C", "J-D",
            "J-H", "J-S", "K-C", "K-D", "K-H", "K-S", "Q-C",
            "Q-D", "Q-H", "Q-S",

            //second deck
            "2-S", "2-D", "2-H", "2-S", "3-C", "3-D", "3-H",
            "3-S", "4-C", "4-D", "4-H", "4-S", "5-C", "5-D",
            "5-H", "5-S", "6-C", "6-D", "6-H", "6-S", "7-C",
            "7-D", "7-H", "7-S", "8-C", "8-D", "8-H", "8-S",
            "9-C", "9-D", "9-H", "9-S", "10-C", "10-D", "10-H",
            "10-S", "A-C", "A-D", "A-H", "A-S", "J-C", "J-D",
            "J-H", "J-S", "K-C", "K-D", "K-H", "K-S", "Q-C",
            "Q-D", "Q-H", "Q-S"
    };
    private String[] newDeck = Arrays.copyOf(cards, cards.length);

    //shuffle deck every time (will change so it's possible to card count)
    public void shuffleDeck()
    {
        //make the array of cards into a Arraylist of Cards
        ArrayList<String> deck = new ArrayList<>(Arrays.asList(cards));

        Collections.shuffle(deck);

        // Update the cards array with the shuffled cards
        for (int i = 0; i < cards.length; i++) {
            cards[i] = deck.get(i);
        }
    }

    public void initializeDeck() {
        newDeck = Arrays.copyOf(newDeck, newDeck.length);
        topIndex = 0;
        shuffleDeck();
    }

    // Override toString() to return the full deck as a string
    @Override
    public String toString() {
        return Arrays.toString(cards);
    }

    public String drawCard() {
        if (topIndex < cards.length) {
            return cards[topIndex++]; // Return the current card and move to the next
        }
        return null; // Return null if all cards are drawn
    }

    public int remainingCards() {
        return cards.length - topIndex;
    }
}
