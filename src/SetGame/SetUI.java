package SetGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.macalester.graphics.*;


public class SetUI {
    public ArrayList<Card> board;
    private ArrayList<Card> deck;

    private Random rand = new Random();

    public SetUI(ArrayList<Card> deck) {
        this.deck = deck;
    }

    private void clearBoard() {
        board.clear();
    }

    private void addCards() {
        for (int i = 0; i < 3; i++) {
            int choice = rand.nextInt(deck.size());
            board.add(deck.get(choice));
            deck.remove(choice);
        }
    }

    boolean isFull() {
        return (board.size() >= 12);
    }

    void checkBoard() {
        if (!this.isFull()){
            if (deck.size() >= 3) {
                addCards();
            }
        }
    }

    // Create Cards
    void createCard(Card card){
        for (int i = 0; i < card.getTrait(1); i++){
            switch (card.getTrait(2)) {
                case 0:
                    createSquiggle(card.getTrait(0), card.getTrait(3));
                case 1:
                    createDiamond(card.getTrait(0), card.getTrait(3));
                case 2:
                    createOval(card.getTrait(0), card.getTrait(3));
                default:
                    createSquiggle(card.getTrait(0), card.getTrait(3));
            }
        }
    }

    // createShapes
    void createSquiggle(int color, int fill){
        // crying
         
    }

    void createDiamond(int color, int fill){
        // more crying
        Rectangle diamond
    }

    void createOval(int color, int fill){
        // even more crying
        Ellipse oval = new Ellipse(x, y, 30, 10);
        switch (fill) {
            case 0:
                oval.setFillColor(fillColor);
        
            default:
                break;
        }
    }
}
