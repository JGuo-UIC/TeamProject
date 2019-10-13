import java.util.ArrayList;

public class BaccaratGameLogic{
    // hand1 = Player, hand2 = Banker
    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2){
        if (handTotal(hand1) >= 8 || 
            handTotal(hand1) > handTotal(hand2))
            return "Player";
        else if (handTotal(hand2) >= 8 || 
                 handTotal(hand2) > handTotal(hand1))
            return "Banker";
        return "Draw";
    }

    public int handTotal(ArrayList<Card>hand){
        int value = 0;
        for (Card c : hand) {
            value += checkFaceValue(c.getValue());
        }
        return value;
    }

    public boolean evaluateBankerDraw(ArrayList<Card>hand, Card playerCard){
        int value = handTotal(hand);
        if (value <= 2 || (value <= 5 && playerCard == null))
            return true;

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