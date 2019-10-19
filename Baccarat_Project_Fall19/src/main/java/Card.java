/*
 * Card contains the attributes that describe a card: suite, value
 */
public class Card {
    public String suite;
    public int value;

    // constructor that initializes a card with the given values
    Card(String theSuite, int theValue) {
        this.suite = theSuite;
        this.value = theValue;
    }

    public String getSuite() {return this.suite;}
    public int getValue() {return this.value;}
    public boolean setSuite(String newSuite) {
        String[] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
        for (String suit : suits) {
            if (newSuite.equals(suit)) {
                this.suite = newSuite;
                return true;
            }
        }        
        return false;
    }
    public boolean setValue(int newVal) {
        if (newVal > 0 && newVal < 14) {        
            this.value = newVal;
            return true;
        }
        return false;
    }
}

