package Werdill;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.macalester.graphics.events.Key;

public class WerdillUI extends GraphicsGroup {
    private static final Color NOT_IN_COLOR = Color.GRAY;//new Color(0xafafaf);
    private static final Color WRONG_POSITION_COLOR = new Color(0xffc900);
    private static final Color RIGHT_POSITION_COLOR = new Color(0x049c00);

    private static final int SQUARE_SIDE_LENGTH = 60;
    private static final int SQUARE_PADDING = 10;

    private static final double KEY_PADDING = SQUARE_PADDING / 2;
    private static final double KEY_SIDE_LENGTH = ((SQUARE_SIDE_LENGTH + SQUARE_PADDING) * 5.0 - KEY_PADDING * 11) / 10;

    private int currentRow;
    private int currentColumn;
    private final Rectangle[][] squares = new Rectangle[6][5];
    private final GraphicsText[][] squareLabels = new GraphicsText[6][5];

    private final HashMap<String, Rectangle> keyboard = new HashMap<>();
    private final HashMap<String, GraphicsText> keyLabels = new HashMap<>();
    private final HashMap<String, Integer> keyStatus = new HashMap<>();

    private final CanvasWindow canvas;
    private final Checker checker;

    public WerdillUI(CanvasWindow canvas, Checker checker) {
        this.canvas = canvas;
        this.checker = checker;

        canvas.onKeyDown((event) -> { //TODO: needs refactoring into smaller methods
            Key key = event.getKey();
            if (key == Key.RETURN_OR_ENTER) {
                sumbitGuess();
                System.out.println(Arrays.toString(checker.getSolution())); //TODO: should be replaced with way of displaying solution at end
            } 
            if (key == Key.DELETE_OR_BACKSPACE) {
                GraphicsText label = squareLabels[currentRow][currentColumn];
                if (label.getText() == "") {
                    shiftColumnLeft();
                } else {
                    label.setText("");
                }
                refreshGraphicsTextPositions();
            } else if (key == Key.LEFT_ARROW) {
                shiftColumnLeft();
            } else if (key == Key.RIGHT_ARROW || key == Key.SPACE) {
                shiftColumnRight();
            } else if ("QWERTYUIOPASDFGHJKLZXCVBNM".contains(key.toString()) && currentColumn < 5) {
                GraphicsText label = squareLabels[currentRow][currentColumn];
                label.setText(key.toString());
                refreshGraphicsTextPositions();
                shiftColumnRight();
            }
        });

        reset();
        assembleKeyboard();
        setPosition(canvas.getWidth()/2 - getWidth()/2 - SQUARE_PADDING, canvas.getHeight()/2 - getHeight()/2 - SQUARE_PADDING);
    }

    private void sumbitGuess() {
        for (int i = 0; i < 5; i++) {
            if (squareLabels[currentRow][i].getText() == "") {
                return;
            }
        }

        String[] guess = getGuess();
        Integer[] checked = checker.check(guess);

        if (checked == null) {
            doInvalidGuess();
            return;
        }

        setUnselected();
        currentColumn = 0;
        
        setCurrentRowTo(checked);

        currentRow += 1;
        setSelected();
    }

    private void doInvalidGuess() {
        return;
    }

    private String[] getGuess() {
        String[] ret = new String[5];
        for (int i = 0; i < 5; i++) {
            ret[i] = squareLabels[currentRow][i].getText();
        }
        return ret;
    }

    private void shiftColumnRight() {
        if (currentColumn > 3) {
            return;
        }

        setUnselected();
        currentColumn++;
        setSelected();
    }
    
    private void shiftColumnLeft() {
        if (currentColumn < 1) {
            return;
        }

        setUnselected();
        currentColumn--;
        setSelected();
    }

    private void setSelected() {
        squares[currentRow][currentColumn].setStrokeWidth(SQUARE_PADDING/2);
    }
    
    private void setUnselected() {
        squares[currentRow][currentColumn].setStrokeWidth(1);
    }

    public void reset() {
        currentRow = 0;
        currentColumn = 0;
            
        assembleArrayOfSquares();
        assembleArrayOfGraphicsTexts();

        canvas.draw();
    }

    private void assembleArrayOfSquares() {
        int x = SQUARE_PADDING;
        int y = SQUARE_PADDING;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                newSquare(x, y, i, j);
                x += SQUARE_PADDING + SQUARE_SIDE_LENGTH;
            }

            x = SQUARE_PADDING;
            y += SQUARE_PADDING + SQUARE_SIDE_LENGTH;
        }
    }

    private void assembleArrayOfGraphicsTexts() {
        int x = SQUARE_PADDING;
        int y = SQUARE_PADDING;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                newSquareGraphicsText(x, y, i, j);
                x += SQUARE_PADDING + SQUARE_SIDE_LENGTH;
            }

            x = SQUARE_PADDING;
            y += SQUARE_PADDING + SQUARE_SIDE_LENGTH;
        }
    }

    private void assembleKeyboard() {
        double x = 0 + SQUARE_PADDING;
        double y = (SQUARE_PADDING + SQUARE_SIDE_LENGTH) * 6 + KEY_PADDING + KEY_SIDE_LENGTH;
        for (String ltr : List.of("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P")) {
            createKey(x, y, ltr);
            x += KEY_SIDE_LENGTH + KEY_PADDING;
        }
        x = 0.25 * KEY_SIDE_LENGTH + SQUARE_PADDING;
        y += KEY_SIDE_LENGTH + KEY_PADDING;
        for (String ltr : List.of("A", "S", "D", "F", "G", "H", "J", "K", "L")) {
            createKey(x, y, ltr);
            x += KEY_SIDE_LENGTH + KEY_PADDING;
        }
        x = 0.75 * KEY_SIDE_LENGTH + SQUARE_PADDING;
        y += KEY_SIDE_LENGTH + KEY_PADDING;
        for (String ltr : List.of("Z", "X", "C", "V", "B", "N", "M")) {
            createKey(x, y, ltr);
            x += KEY_SIDE_LENGTH + KEY_PADDING;
        }
    }

    private void createKey(double x, double y, String ltr) {
        keyStatus.put(ltr, -1);

        Rectangle newKey = new Rectangle(x, y, KEY_SIDE_LENGTH, KEY_SIDE_LENGTH);
        keyboard.put(ltr, newKey);

        GraphicsText newKeyLabel = new GraphicsText(ltr, x, y);
        keyLabels.put(ltr, newKeyLabel);

        newKeyLabel.setFontSize(KEY_SIDE_LENGTH/1.5);

        double dx = newKey.getCenter().getX() - newKeyLabel.getCenter().getX();
        double dy = newKey.getCenter().getY() - newKeyLabel.getCenter().getY();
        newKeyLabel.moveBy(dx, dy);

        add(newKey);
        add(newKeyLabel);
    }

    private void refreshGraphicsTextPositions() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Rectangle square = squares[i][j];
                GraphicsText label = squareLabels[i][j];
                
                double dx = square.getCenter().getX() - label.getCenter().getX();
                double dy = square.getCenter().getY() - label.getCenter().getY();

                label.moveBy(dx, dy);
            }
        }
    }

    private void refreshKeyboard() {
        for (String key : keyLabels.keySet()) {
            Rectangle square = keyboard.get(key);
            GraphicsText label = keyLabels.get(key);

            switch (keyStatus.get(key)) {
                case 0:
                    square.setFillColor(NOT_IN_COLOR);
                    square.setStrokeColor(NOT_IN_COLOR);

                    label.setFillColor(Color.WHITE);                    
                    break;
                    
                case 1:
                    square.setFillColor(WRONG_POSITION_COLOR);
                    square.setStrokeColor(WRONG_POSITION_COLOR);

                    label.setFillColor(Color.WHITE);
                    break;
            
                case 2:
                    square.setFillColor(RIGHT_POSITION_COLOR);
                    square.setStrokeColor(RIGHT_POSITION_COLOR);

                    label.setFillColor(Color.WHITE);
                    break;
            
                default:
                    break;
            }
        }
        canvas.draw();
    }

    private void newSquare(int x, int y, int row, int column) {
        Rectangle nextSquare = new Rectangle(x, y, SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
        add(nextSquare);
        
        squares[row][column] = nextSquare;
    }

    private void newSquareGraphicsText(int x, int y, int row, int column) {
        GraphicsText nextLabel = new GraphicsText("", x, y);
        nextLabel.setFontSize(SQUARE_SIDE_LENGTH/1.5);
        add(nextLabel);

        squareLabels[row][column] = nextLabel;
    }

    private void setCurrentRowTo(Integer[] checked) { //TODO: is this method a bit long?
        // 0 = not in; 1 = in wrong position; 2 = in correct position
        for (Integer integer : checked) {
            if (integer == null) {
                return;
            }
        }

        Rectangle[] row = squares[currentRow];
        for (int i = 0; i < 5; i++) {
            GraphicsText label = squareLabels[currentRow][i];
            label.setFillColor(Color.WHITE);
            String ltr = label.getText();
            Integer currentKeyStatus;

            Rectangle square = row[i];
            switch (checked[i]) {
                case 0:
                    square.setFillColor(NOT_IN_COLOR);
                    square.setStrokeColor(NOT_IN_COLOR);

                    currentKeyStatus = keyStatus.get(ltr);
                    keyStatus.put(ltr, 
                        currentKeyStatus < 0  ? 0 :
                        currentKeyStatus
                    );
                    break;
            
                case 1:
                    square.setFillColor(WRONG_POSITION_COLOR);
                    square.setStrokeColor(WRONG_POSITION_COLOR);

                    currentKeyStatus = keyStatus.get(ltr);
                    keyStatus.put(ltr, 
                        currentKeyStatus < 1 ? 1 :
                        currentKeyStatus
                    );
                    break;
            
                case 2:
                    square.setFillColor(RIGHT_POSITION_COLOR);
                    square.setStrokeColor(RIGHT_POSITION_COLOR);

                    currentKeyStatus = keyStatus.get(ltr);
                    keyStatus.put(ltr, 
                        currentKeyStatus < 2 ? 2 :
                        currentKeyStatus
                    );
                    break;
            
                default:
                    break;
            }
            canvas.draw();
            
            canvas.pause(200);
        }
        refreshKeyboard();
    }
}
