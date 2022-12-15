package Werdill;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;

import java.awt.Color;

import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.events.KeyboardEvent;

/**
 * Parent class for handling all of Werdill's user interface. Contains Grid and Keyboard objects
 * that are responsive to user input. 
 * 
 * @author Phil Reitz-Jones
 * @author Kaliana Andriamananjara
 */
public class WerdillUI extends GraphicsGroup {
    public static final Color NOT_IN_COLOR = Color.GRAY;
    public static final Color WRONG_POSITION_COLOR = new Color(0xffc900);
    public static final Color RIGHT_POSITION_COLOR = new Color(0x049c00);

    private Integer gameNumber = 0;

    private final CanvasWindow canvas;
    private final Checker checker;

    private final Keyboard keyboard;
    private final Grid grid;

    public WerdillUI(CanvasWindow canvas, Checker checker) {
        this.canvas = canvas;
        this.checker = checker;

        this.keyboard = new Keyboard(canvas, this);
        this.grid = new Grid(canvas, this);

        canvas.onKeyDown((event) -> {
            keyDownCallback(event);
        });

        // This must be incremented to allow user to reset Werdill without creating new objects.
        gameNumber++;

        grid.assemble();
        keyboard.assemble();
        setPosition(canvas.getWidth()/2 - getWidth()/2 - Grid.SQUARE_PADDING, canvas.getHeight()/2 - getHeight()/2 - Grid.SQUARE_PADDING);
    }

    public Checker getChecker() {
        return checker;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    /**
     * Handles user input on the keyboard. If the input is return, the method checks the user's
     * guess against the solution via the Checker. It prevents the user from shifting their selection
     * off screen, finally passing all remaining input to Grid for further processing.
     * 
     * @param event KeyboardEvent passed by canvas.
     */
    private void keyDownCallback(KeyboardEvent event) {
        // Prevent spillovers
        if (grid.getCurrentRow() >= 6) {
            return;
        }
        // If input is not enter, it's handled by Grid
        Key key = event.getKey();
        if (key == Key.RETURN_OR_ENTER) {
            sumbitGuess();
        }
        grid.onKeyDown(key);
    }

    /**
     * Gets and stores guess from `getGuess()` and sends to checker to check against solution.
     * Response is then passed to Keyboard and Grid to update their respective UI elements.
     */
    private void sumbitGuess() {
        String[] guess = grid.getGuess();
        // Return (fail) if any empty characters
        for (String ltr : guess) {
            if (ltr == "" || ltr == " ") {
                return;
            }
        }

        Integer[] checked = checker.check(guess);

        if (checked == null) {
            return;
        }

        grid.setCurrentRowTo(checked);
        keyboard.setKeyColors(guess, checked);
    }

    /**
     * Resets all UI elements and generates new solution.
     */
    public void reset() {
        if (gameNumber > 0) {
            grid.reset();
            keyboard.reset();

            canvas.draw();
            
            gameNumber++;
            return;
        }
    }
}
