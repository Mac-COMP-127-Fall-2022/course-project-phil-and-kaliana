package SetGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.macalester.graphics.*;

import java.awt.Color;


public class SetUI extends GraphicsGroup {
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
}
