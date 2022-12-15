package Werdill;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import edu.macalester.graphics.ui.Button;

/**
 * Class representing the keyboard UI elements under the Werdill grid. This allows the user to easily
 * determine which letters have been guessed (or not), and whether they are in the word. 
 * Adds 26 Rectangles and Labels to the parent WerdillUI (extending GraphicsGroup) in the same layout
 * as a QWERTY keyboard. Each rectangle's fill is updated to match whether the letter is in the solution. 
 * 
 * @author Phil Reitz-Jones
 * @author Kaliana Andriamananjara
 */
public class Keyboard {
    public static final double KEY_PADDING = Grid.SQUARE_PADDING / 2;
    public static final double KEY_SIDE_LENGTH = ((Grid.SQUARE_SIDE_LENGTH + Grid.SQUARE_PADDING) * 5.0 - KEY_PADDING * 11) / 10;

    private final HashMap<String, Rectangle> keys = new HashMap<>();
    private final HashMap<String, GraphicsText> keyLabels = new HashMap<>();
    private final HashMap<String, Integer> keyStatus = new HashMap<>();
    
    private final CanvasWindow canvas;
    private final WerdillUI parentUI;
    
    public Keyboard(CanvasWindow canvas, WerdillUI parentUI) {
        this.canvas = canvas;
        this.parentUI = parentUI;
    }

    /**
     * Returns the current status/color of the queried key.
     * Legend: -1 = blank; 0 = letter is not in solution; 1 = letter is in solution but is in
     * the wrong place; 2 = letter is in solution at correct position. 
     * @param ltr   Letter to query
     * @return      Status
     */
    public Integer getKeyStatus(String ltr) {
        return keyStatus.get(ltr);
    }

    /**
     * Changes the input Rectangle's fill and stroke colors to color. Changes label's font color 
     * to #ffffff. 
     * 
     * @param square    The Rectangle object to be recolored
     * @param label     The GraphicsText on top of that Rectangle (this changes text color to white)
     * @param color     The Color to which to change both square's stroke and fill color
     */
    private void setKeyColor(Rectangle square, GraphicsText label, Color color) {
        square.setFillColor(color);
        square.setStrokeColor(color);

        label.setFillColor(Color.WHITE);
    }

    /**
     * Update the UI elements representing each key to reflect internal state (keyStatus ltr). Changes
     * each key's fill color to match internal list.
     * Legend: -1 = blank; 0 = letter is not in solution; 1 = letter is in solution but is in
     * the wrong place; 2 = letter is in solution at correct position. 
     */
    public void refresh() {
        // Choice of key labels for loop is somewhat arbitrary
        for (String key : keyLabels.keySet()) {
            Rectangle square = keys.get(key);
            GraphicsText label = keyLabels.get(key);
            // Each case represents its corresponding fill color.
            switch (keyStatus.get(key)) {
                case 0:
                    setKeyColor(square, label, WerdillUI.NOT_IN_COLOR);                    
                    break;
                    
                case 1:
                    setKeyColor(square, label, WerdillUI.WRONG_POSITION_COLOR);
                    break;
            
                case 2:
                    setKeyColor(square, label, WerdillUI.RIGHT_POSITION_COLOR);
                    break;
            
                default:
                    break;
            }
        }
        canvas.draw();
    }

    /**
     * Creates a key labeled with letter ltr at position x, y in the parent object. Instantiates
     * a new Rectangle to serve as key background and GraphicsText for the label. Centers the 
     * label within the bounds of the Rectangle.
     *  
     * @param x     x coordinate in parent object.
     * @param y     y coordinate in parent object.
     * @param ltr   letter for key label
     */
    private void createKey(double x, double y, String ltr) {
        // -1 simply indicates the letter has not yet been in any of the user's guesses
        keyStatus.put(ltr, -1);

        Rectangle newKey = new Rectangle(x, y, KEY_SIDE_LENGTH, KEY_SIDE_LENGTH);
        keys.put(ltr, newKey);

        GraphicsText newKeyLabel = new GraphicsText(ltr, x, y);
        keyLabels.put(ltr, newKeyLabel);

        newKeyLabel.setFontSize(KEY_SIDE_LENGTH/1.5);
        // Label centering logic
        double dx = newKey.getCenter().getX() - newKeyLabel.getCenter().getX();
        double dy = newKey.getCenter().getY() - newKeyLabel.getCenter().getY();
        newKeyLabel.moveBy(dx, dy);

        parentUI.add(newKey);
        parentUI.add(newKeyLabel);
    }

    /**
     * Creates and positions all relevant GUI elements making up the keyboard. This method
     * creates NEW OBJECTS and should not be used once the user has played >= 1 game. (call
     * reset() in that case)
     */
    public void assemble() {
        double x = 0 + Grid.SQUARE_PADDING;
        double y = (Grid.SQUARE_PADDING + Grid.SQUARE_SIDE_LENGTH) * 6 + KEY_PADDING + KEY_SIDE_LENGTH;
        createKeyRow(x, y, List.of("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"));
        
        x = 0.25 * KEY_SIDE_LENGTH + Grid.SQUARE_PADDING;
        y += KEY_SIDE_LENGTH + KEY_PADDING;
        createKeyRow(x, y, List.of("A", "S", "D", "F", "G", "H", "J", "K", "L"));

        x = 0.75 * KEY_SIDE_LENGTH + Grid.SQUARE_PADDING;
        y += KEY_SIDE_LENGTH + KEY_PADDING;
        createKeyRow(x, y, List.of("Z", "X", "C", "V", "B", "N", "M"));
        
        Button resetButton = new Button("Reset");
        resetButton.setPosition(keys.get("M").getX() + KEY_PADDING + KEY_SIDE_LENGTH, y);
        // This is a somewhat gnarlacious implementation for the reset button, but it works...
        resetButton.onClick(() -> {
            parentUI.reset();
            parentUI.getChecker().chooseSolution();
        });

        parentUI.add(resetButton);
        canvas.draw();
    }

    /**
     * Calls createKey for each key in keys, increasing x each time so they do not overlap.
     * 
     * @param x     key's x coordinate
     * @param y     key's y coordinate
     * @param keys  list of keys
     */
    private void createKeyRow(double x, double y, List<String> keys) {
        for (String ltr : keys) {
            createKey(x, y, ltr);
            x += KEY_SIDE_LENGTH + KEY_PADDING;
        }
    }

    public void setKeyStatus(String ltr, Integer status) {
        keyStatus.put(ltr, status);
    }

    /**
     * Sets the colors of all keys in ltrs to respective indices in colors.
     * Legend: -1 = blank; 0 = letter is not in solution; 1 = letter is in solution but is in
     * the wrong place; 2 = letter is in solution at correct position. 
     * 
     * @param ltrs      Array of input letters (usually the user's guess)
     * @param colors    Integer Array of equal-length encoding each key's color
     */
    public void setKeyColors(String[] ltrs, Integer[] colors) {
        for (int index = 0; index < 5; index++) {
            Integer targetKeyStatus = colors[index];
            String ltr = ltrs[index];

            Integer currentKeyStatus = keyStatus.get(ltr);
            keyStatus.put(ltr, 
                currentKeyStatus < targetKeyStatus ? targetKeyStatus :
                currentKeyStatus
            );
            refresh();
        }
    }

    /**
     * Resets all keys to white fill and black text. Also reverts all keys' statuses to -1.
     */
    public void reset() {
        for (String key : keys.keySet()) {
            keys.get(key).setFillColor(Color.WHITE);
            keys.get(key).setStrokeColor(Color.BLACK);

            keyLabels.get(key).setFillColor(Color.BLACK);

            keyStatus.put(key, -1);
        }
    }
}
