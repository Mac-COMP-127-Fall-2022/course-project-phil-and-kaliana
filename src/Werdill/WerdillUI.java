package Werdill;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.events.KeyboardEvent;
import edu.macalester.graphics.ui.Button;

public class WerdillUI extends GraphicsGroup {
    public static final Color NOT_IN_COLOR = Color.GRAY;
    public static final Color WRONG_POSITION_COLOR = new Color(0xffc900);
    public static final Color RIGHT_POSITION_COLOR = new Color(0x049c00);

    private Integer gameNumber = 0;

    private final CanvasWindow canvas;
    private final Checker checker;

    private final Keyboard keyboard;

    public Keyboard getKeyboard() {
        return keyboard;
    }

    private final Grid grid;

    public WerdillUI(CanvasWindow canvas, Checker checker) {
        this.canvas = canvas;
        this.checker = checker;

        this.keyboard = new Keyboard(canvas, this);
        this.grid = new Grid(canvas, this);

        canvas.onKeyDown((event) -> {
            keyDownCallback(event);
        });

        grid.assemble();
        keyboard.assemble();
        setPosition(canvas.getWidth()/2 - getWidth()/2 - Grid.SQUARE_PADDING, canvas.getHeight()/2 - getHeight()/2 - Grid.SQUARE_PADDING);
    }

    public Checker getChecker() {
        return checker;
    }

    private void keyDownCallback(KeyboardEvent event) {
        if (grid.getCurrentRow() >= 6) {
            return;
        }
        Key key = event.getKey();
        if (key == Key.RETURN_OR_ENTER) {
            sumbitGuess();
        }
        grid.onKeyDown(key);
    }

    private void sumbitGuess() {
        String[] guess = grid.getGuess();
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

    public void reset() {
        grid.reset();
        keyboard.reset();

        canvas.draw();
        
        gameNumber++;
        return;
    }
}
