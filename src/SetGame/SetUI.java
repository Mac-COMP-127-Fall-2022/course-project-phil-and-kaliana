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

    private final List<Color> colors = List.of(
        Color.RED,
        Color.GREEN,
        Color.BLUE
    );

    private final List<Color> halfColors = List.of(
        new Color(255, 127, 127),
        new Color(127, 255, 127),
        new Color(127, 127, 255)
    );

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
        // trait 1 is number of
        for (int i = 0; i < card.getTrait(1); i++) {
            // trait 2 is shape
            switch (card.getTrait(2)) {
                // trait 0 is color, trait 3 is fill
                case 0:
                    add(createTriangle(card.getTrait(0), card.getTrait(3)));
                    break;
                case 1:
                    add(createDiamond(card.getTrait(0), card.getTrait(3)));
                    break;
                case 2:
                    add(createRectangle(card.getTrait(0), card.getTrait(3)));
                    break;
                default:
                    add(createTriangle(card.getTrait(0), card.getTrait(3)));
                    break;
            }
        }
    }


    private Color getColorFromInt(int colorInt, int fillInt) {
        if (fillInt == 1) {
            return halfColors.get(colorInt);
        } else if (fillInt == 2) {
            return colors.get(colorInt);
        } else {
            return Color.WHITE;
        }
    }

    // createShapes
    private Path createTriangle(int color, int fill){
        // crying
        Path triangle = Path.makeTriangle(
            0, 0,
            10, 10,
            20, 0
        );
        
        triangle.setFillColor(getColorFromInt(color, fill));
        triangle.setStrokeColor(colors.get(color));

        return triangle;
    }

    private Path createDiamond(int color, int fill){
        // more crying
        Path diamond = new Path(List.of(
            new Point(0, 5),
            new Point(10, 10),
            new Point(20, 5),
            new Point(10, 0)
        ));
        
        diamond.setFillColor(getColorFromInt(color, fill));
        diamond.setStrokeColor(colors.get(color));

        return diamond;
    }

    private Rectangle createRectangle(int color, int fill){
        // even more crying
        Rectangle rect = new Rectangle(
            0, 0,
            20, 10
        );
        
        rect.setFillColor(getColorFromInt(color, fill));
        rect.setStrokeColor(colors.get(color));

        return rect;
    }
}
