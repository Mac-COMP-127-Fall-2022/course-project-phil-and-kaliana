package SetGame;

import java.util.ArrayList;

public class SetGameMain {
    // Instance Variables
    private SetUI gameUI;
    private ArrayList<Card> deck;


    // Instance Methods
    

    public boolean checkIfSet(Card cardA, Card cardB, Card cardC) {
        return (checkTrait(cardA, cardB, cardC, 0) &&
                checkTrait(cardA, cardB, cardC, 1) &&
                checkTrait(cardA, cardB, cardC, 2) &&
                checkTrait(cardA, cardB, cardC, 3)
        );
    }

    boolean checkTrait(Card cardA, Card cardB, Card cardC, Integer t) {
        if ((cardA.getTrait(t) == cardB.getTrait(t)) && (cardA.getTrait(t) == cardC.getTrait(t))) {
            return true;
        }
        else if (!(cardA.getTrait(t) == cardB.getTrait(t)) &&
                 !(cardA.getTrait(t) == cardC.getTrait(t)) &&
                 !(cardB.getTrait(t) == cardC.getTrait(t))) {
            return true;
        } else {
            return false;
        }
    }

    public void createDeck() {
        deck = new ArrayList<>();
        for (int c = 0; c < 3; c++) {
            for (int n = 0; n < 3; n++) {
                for (int s = 0; s < 3; s++) {
                    for (int f = 0; f < 3; f++) {
                        deck.add(new Card(c, n, s, f));
                    }
                }
            }
        }
    }

    public void startGame(){
        gameUI = new SetUI(deck);
        createDeck();
    }
    
    public static void main() {
        SetGameMain game = new SetGameMain();
        game.startGame();
    }
}
