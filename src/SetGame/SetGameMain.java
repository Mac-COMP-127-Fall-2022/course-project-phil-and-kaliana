package SetGame;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;

public class SetGameMain {
    // Instance Variables
    private SetUI gameUI;
    private ArrayList<Card> deck;

    public static final int NUMBER_OF_TRAIT_VARIANTS = 3;

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
        for (int color = 0; color < NUMBER_OF_TRAIT_VARIANTS; color++) {
            for (int number = 0; number < NUMBER_OF_TRAIT_VARIANTS; number++) {
                for (int shape = 0; shape < NUMBER_OF_TRAIT_VARIANTS; shape++) {
                    for (int fill = 0; fill < NUMBER_OF_TRAIT_VARIANTS; fill++) {
                        deck.add(new Card(color, number, shape, fill));
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
