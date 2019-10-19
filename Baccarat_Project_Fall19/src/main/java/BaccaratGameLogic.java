import java.util.ArrayList;

public class BaccaratGameLogic{
    // hand1 = Player, hand2 = Banker
    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2){
        int playerTotal = handTotal(hand1);
        int bankerTotal = handTotal(hand2);

        //check natural winning condition
        if (hand1.size() == 2 && hand2.size() == 2) {
            if (playerTotal >= 8 && bankerTotal < 8)
                return "Player";
            else if (bankerTotal >= 8 && playerTotal < 8)
                return "Banker";
            return "Draw";
        }

        if (playerTotal > bankerTotal)
            return "Player";
        else if (bankerTotal > playerTotal)
            return "Banker";
        return "Draw";
    }

    public int handTotal(ArrayList<Card>hand){
        int value = 0;
        for (Card c : hand) {
            value += checkFaceValue(c.getValue());
        }
        //this should work now.
        if(value > 9)
            return (value - 10);
        else
            return value;
    }

    public boolean evaluateBankerDraw(ArrayList<Card>hand, Card playerCard){
        int value = handTotal(hand);
        if (value <= 2 || (value <= 5 && playerCard == null))
            return true;
        else if (playerCard == null)
            return false;

        int pValue = checkFaceValue(playerCard.getValue());        
        if ((value == 3 && pValue != 8) ||
            (value == 4 && (pValue > 1 && pValue < 8)) || 
            (value == 5 && (pValue > 3 && pValue < 8)) || 
            (value == 6 && (pValue > 5 && pValue < 8)) )
            return true;        
        return false;
    }

    public boolean evaluatePlayerDraw(ArrayList<Card>hand){
        int value = handTotal(hand);
        if (value < 6)
            return true;
        return false;
    }

    private int checkFaceValue(int value) {
        if (value > 9)
            return 0;
        return value;
    }
}