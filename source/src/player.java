import java.util.ArrayList;

public class player
{

    private int money;
    ArrayList<String> hand = new ArrayList<String>();
    int handScore;
    boolean busted;

    //player object
    public player(int initialMoney)
    {
        this.money = initialMoney;
        this.hand = new ArrayList<>();
        this.handScore = 0;
        this.busted = false;
    }

    public void firstTwo(Cards deck)
    {
        String card = deck.drawCard();
        String secondCard = deck.drawCard();

        hand.add(card);
        hand.add(secondCard);
    }

    public void clearHand()
    {
        hand.clear();
    }

    public void hit(Cards deck) {
        String card = deck.drawCard();
        hand.add(card);
    }

    public void stand() {
        System.out.println("player stands");
    }

    public int getMoney()
    {
        return this.money;
    }

    //winning money method
    public void winMoney(int bet)
    {
        this.money += bet * 2;
    }

    public void pushMoney(int bet)
    {
        this.money += bet;
    }

    public ArrayList<String> printHand()
    {
        System.out.print("player hand: ");
        return this.hand;
    }

    public ArrayList<String> getHand()
    {
        return this.hand;
    }

    public void bet(int amount)
    {
        this.money -= amount; //Deduct the bet amount from the player's money
    }


    public boolean bust()
    {
        System.out.println("player busted");
        return this.busted = true;
    }

    public void bustReset()
    {
        this.busted = false;
    }

    public boolean getBust()
    {
        return this.busted;
    }
}
