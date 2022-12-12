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
    
    // TEST METHOD

    void visualCardTest() {
        Random rand = new Random();
        canvas.onClick((event) -> {
            canvas.removeAll();

            Card card = new Card(rand.nextInt(3), rand.nextInt(3), rand.nextInt(3), rand.nextInt(3));
            canvas.add(card);

            card.setPosition(Card.CARD_WIDTH, Card.CARD_HEIGHT);
            
            GraphicsObject elementAt = canvas.getElementAt(event.getPosition());
            System.out.println(elementAt.getClass());
            if (elementAt != null && elementAt.getClass() == Rectangle.class) {
                ((Rectangle) elementAt).setStrokeColor(Color.RED);
                ((Rectangle) elementAt).setStrokeWidth(Card.SHAPE_AND_CARD_STROKE_WIDTH + 1);
            }

            canvas.draw();
        });
    }

    // END TEST METHOD

    public SetGameMain() {
        canvas = new CanvasWindow("Set", 1200, 800);
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
        // game.visualCardTest();
        game.startGame();
    }
}
