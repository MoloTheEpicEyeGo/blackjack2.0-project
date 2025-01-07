import java.util.ArrayList;

public class util
{
    public static int calculateHand(ArrayList<String> hand)
    {
        //aceCount tracks Aces to adjust their value from 11 to 1 if needed
        int aceCount = 0;
        int handVal = 0;

        for (String card : hand)
        {
            //get the first character of the card string, which is now the rank
            char rank = card.charAt(0);

            if (rank == 'A')
            {
                aceCount++;
                handVal += 11;
            }
            else if (rank == 'K' || rank == 'Q' || rank == 'J')
            {
                handVal += 10;
            }
            else if (rank == '1') //is case is 10
            {
                handVal += 10;
            }
            else
            {
                handVal += Character.getNumericValue(rank); //changes char into an int
            }
        }

        while (handVal > 21 && aceCount > 0)
        {
            handVal -= 10; //changes 11 to 1 to adjust score.
            aceCount--;
        }
        return handVal;
    }
}
