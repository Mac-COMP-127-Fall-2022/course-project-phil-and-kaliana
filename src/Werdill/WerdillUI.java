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

    private static final int SQUARE_SIDE_LENGTH = 60;
    private static final int PADDING = 10;

    private int guessNumber;
    private final ArrayList<ArrayList<Rectangle>> squares;

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

            for (int j = 0; j < 5; j++) { // extract 5 to constant? (in case we want to add longer words)
                Rectangle nextSquare = new Rectangle(x, y, SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
                add(nextSquare);
                
                x += PADDING + SQUARE_SIDE_LENGTH;

                newList.add(nextSquare);
            }

            x = PADDING;
            y += PADDING + SQUARE_SIDE_LENGTH;
        }

        canvas.draw();
    }

    public void setCurrentRowTo(int[] checkedCharacters) { //TODO: SET BACK TO PRIVATE AFTER TESTING
        // 0 = not in; 1 = in wrong position; 2 = in correct position

        ArrayList<Rectangle> row = squares.get(guessNumber);
        for (int i = 0; i < 5; i++) {
            Rectangle square = row.get(i);
            switch (checkedCharacters[i]) {
                case 0:
                    square.setFillColor(NOT_IN_COLOR);
                    square.setStrokeColor(NOT_IN_COLOR);
                    break;
            
                case 1:
                    square.setFillColor(WRONG_POSITION_COLOR);
                    square.setStrokeColor(WRONG_POSITION_COLOR);
                    break;
            
                case 2:
                    square.setFillColor(RIGHT_POSITION_COLOR);
                    square.setStrokeColor(RIGHT_POSITION_COLOR);
                    break;
            
                default:
                    break;
            }
        }

        guessNumber += 1;
        canvas.draw();
    }

}
