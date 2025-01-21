import java.util.ArrayList;

public class dealer
{
    ArrayList<String> hand = new ArrayList<String>();// To store the hidden card

    boolean busted;

    public void firstTwo(Cards deck)
    {
        String firstCard = deck.drawCard(); // Dealer's face-up card
        String hiddenCard = deck.drawCard(); // Dealer's face-down card

        hand.add(firstCard);
        hand.add(hiddenCard);
    }

    public void clearHand()
    {
        hand.clear();
    }

    public void hit(Cards deck) {
        String card = deck.drawCard();
        hand.add(card);
    }


    //returns whole hand
    public ArrayList<String> getHand()
    {
        return this.hand;
    }

    public ArrayList<String> revealHand()
    {
        return this.hand;
    }

    public boolean bust()
    {
        this.busted = true;
        System.out.println("dealer busted");
        return this.busted;
    }

    public boolean getBust()
    {
        return this.busted;
    }

}
