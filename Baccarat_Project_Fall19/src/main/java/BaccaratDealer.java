import java.util.ArrayList;
import java.util.Collections;

/*
 * BaccaratDealer keeps track of a deck of cards and deals for the game
 */
public class BaccaratDealer {
    private ArrayList<Card> deck;

    BaccaratDealer() { // constructor that initiates the arraylist for a deck of card
        deck = new ArrayList<Card>();
    }

    // getDeck returns the deck of card
    public ArrayList<Card> getDeck() {return this.deck;}

    // generateDeck generates a deck of cards
    public void generateDeck() {
        deck.clear();
        String[] suits = {"Spades", "Diamonds", "Clubs", "Hearts"};
        for (int i = 0; i < 52; ++i) {
            deck.add(new Card(suits[i / 13], i % 13 + 1));
        }
    }

    // dealHand returns a hand of cards from the deck
    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();        
        hand.add(deck.remove(0));        
        hand.add(deck.remove(0));
        return hand;
    }

    // drawOne returns a single card from the deck
    public Card drawOne() {
        return deck.remove(0);
    }

    // shuffleDeck generates a new shuffled deck of card
    public void shuffleDeck() {
        deck.clear();
        generateDeck();
        Collections.shuffle(deck);
    }

    // deckSize returns the deck's size
    public int deckSize() {
        return this.deck.size();
    }
}