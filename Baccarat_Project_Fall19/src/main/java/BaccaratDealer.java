import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
    private ArrayList<Card> deck;

    BaccaratDealer() {
        deck = new ArrayList<Card>();
    }

    public ArrayList<Card> getDeck() {return this.deck;}

    public void generateDeck() {
        String[] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
        for (int i = 0; i < 52; ++i) {
            deck.add(new Card(suits[i / 13], i % 13 + 1));
        }
    }

    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();        
        hand.add(deck.remove(0));        
        hand.add(deck.remove(0));
        return hand;
    }

    public Card drawOne() {
        return deck.remove(0);
    }

    public void shuffleDeck() {
        deck.clear();
        generateDeck();
        Collections.shuffle(deck);
    }

    public int deckSize() {
        return this.deck.size();
    }
}