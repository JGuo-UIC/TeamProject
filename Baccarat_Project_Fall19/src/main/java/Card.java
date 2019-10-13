public class Card {
    private String suite;
    private int value;

    Card(String theSuite, int theValue) {
        this.suite = theSuite;
        this.value = theValue;
    }

    public String getSuite() {return this.suite;}
    public int getValue() {return this.value;}
    public void setSuite(String newSuite) {this.suite = newSuite;}
    public void setValue(int newVal) {this.value = newVal;}
}

