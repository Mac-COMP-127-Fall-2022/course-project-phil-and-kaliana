package SetGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;

public class SetGameMain {
    // Instance Variables
    private SetUI gameUI;
    private ArrayList<Card> deck = new ArrayList<>();

    public static final int NUMBER_OF_TRAIT_VARIANTS = 3;

    public final CanvasWindow canvas;

    // Instance Methods

    public SetGameMain() {
        canvas = new CanvasWindow("Set", 1200, 800);

        startGame();
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
        createDeck();
        gameUI = new SetUI(deck, canvas);
        canvas.add(gameUI);
    }
    
    public static void main(String[] args) {
        SetGameMain game = new SetGameMain();
    }
}
