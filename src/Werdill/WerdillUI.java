package Werdill;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.events.Key;

public class WerdillUI extends GraphicsGroup {
    // private static final Color BASE_COLOR = new Color();
    
    private static final Color NOT_IN_COLOR = new Color(0xafafaf);
    private static final Color WRONG_POSITION_COLOR = new Color(0xffc900);
    private static final Color RIGHT_POSITION_COLOR = new Color(0x049c00);
    private static final Color BACKGROUND_COLOR = new Color(0xffffff);

    private static final Color WRONG_RIGHT_TEXT_COLOR = new Color(0xffffff);
    private static final Color NOT_IN_AND_BASE_TEXT_COLOR = new Color(0xffffff);

    private static final int SQUARE_SIDE_LENGTH = 40;
    private static final int PADDING = 5;

    private int guessNumber;
    private ArrayList<ArrayList<Rectangle>> squares;

    private final CanvasWindow canvas;


    public WerdillUI(WerdillGame werdillGame, CanvasWindow canvas) {
        this.canvas = canvas;

        this.squares = new ArrayList<>();

        canvas.onKeyDown((event) -> {
            if (event.getKey() == Key.RETURN_OR_ENTER) {

            }
        });

        reset();
    }

    public void reset() {
        guessNumber = 0;

        if (squares.size() == 6) {
            // will eventually reset the colors if rectangles exist
        }

        // squares = new ArrayList<>();

        int x = PADDING;
        int y = PADDING;
        for (int i = 0; i < 6; i++) {
            ArrayList<Rectangle> newList = new ArrayList<>();

            squares.add(newList);

            for (int j = 0; j < 5; j++) {
                Rectangle nextSquare = new Rectangle(x, y, SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
                add(nextSquare);
                x += PADDING + SQUARE_SIDE_LENGTH;
            }

            x = PADDING;
            y += PADDING + SQUARE_SIDE_LENGTH;
        }

        canvas.draw();
    }

    private void setCurrentRowTo(int[] checkedCharacters) {
        // 0 = not in; 1 = in wrong position; 2 = in correct position

        
    }

}
