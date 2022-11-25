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

    // <Keyboard>
    private final HashMap<String, Rectangle> keyboard = new HashMap<>();
    private final HashMap<String, GraphicsText> keyLabels = new HashMap<>();
    private final HashMap<String, Integer> keyStatus = new HashMap<>();
    // </Keyboard>

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
                setNextSquareToLtr(key);
            }
        });

        reset();
        assembleKeyboard();
        setPosition(canvas.getWidth()/2 - getWidth()/2 - SQUARE_PADDING, canvas.getHeight()/2 - getHeight()/2 - SQUARE_PADDING);
    }

    private void setNextSquareToLtr(Key key) {
        GraphicsText label = squareLabels[currentRow][currentColumn];
        label.setText(key.toString());
        refreshGraphicsTextPositions();
        shiftColumnRight();
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
    
    // <Grid>
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
    // </Grid>

    // <Keyboard>
    private void assembleKeyboard() {
        double x = 0 + SQUARE_PADDING;
        double y = (SQUARE_PADDING + SQUARE_SIDE_LENGTH) * 6 + KEY_PADDING + KEY_SIDE_LENGTH;
        createKeyRow(x, y, List.of("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"));
        
        x = 0.25 * KEY_SIDE_LENGTH + SQUARE_PADDING;
        y += KEY_SIDE_LENGTH + KEY_PADDING;
        createKeyRow(x, y, List.of("A", "S", "D", "F", "G", "H", "J", "K", "L"));

        x = 0.75 * KEY_SIDE_LENGTH + SQUARE_PADDING;
        y += KEY_SIDE_LENGTH + KEY_PADDING;
        createKeyRow(x, y, List.of("Z", "X", "C", "V", "B", "N", "M"));
    }

    private void createKeyRow(double x, double y, List<String> keys) {
        for (String ltr : keys) {
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
    // </Keyboard>

    // Grid
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

    // Keyboard
    private void refreshKeyboard() {
        for (String key : keyLabels.keySet()) {
            Rectangle square = keyboard.get(key);
            GraphicsText label = keyLabels.get(key);

            switch (keyStatus.get(key)) {
                case 0:
                    setKeyColor(square, label, NOT_IN_COLOR);                    
                    break;
                    
                case 1:
                    setKeyColor(square, label, WRONG_POSITION_COLOR);
                    break;
            
                case 2:
                    setKeyColor(square, label, RIGHT_POSITION_COLOR);
                    break;
            
                default:
                    break;
            }
        }
        canvas.draw();
    }

    private void setKeyColor(Rectangle square, GraphicsText label, Color color) {
        square.setFillColor(color);
        square.setStrokeColor(color);

        label.setFillColor(Color.WHITE);
    }

    // Grid
    private void newSquare(int x, int y, int row, int column) {
        Rectangle nextSquare = new Rectangle(x, y, SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
        add(nextSquare);
        
        squares[row][column] = nextSquare;
    }

    // <Grid>
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

            Rectangle square = row[i];
            switch (checked[i]) {
                case 0:
                    setSquareAndKeyColor(ltr, square, 0);
                    break;
            
                case 1:
                    setSquareAndKeyColor(ltr, square, 1);
                    break;
                    
                case 2:
                    setSquareAndKeyColor(ltr, square, 2);
                    break;
            
                default:
                    break;
            }
            canvas.draw();
            
            canvas.pause(200);
        }
        refreshKeyboard();
    }

    private void setSquareAndKeyColor(String ltr, Rectangle square, Integer targetKeyStatus) {
        Integer currentKeyStatus;
        Color color = List.of(NOT_IN_COLOR, WRONG_POSITION_COLOR, RIGHT_POSITION_COLOR).get(targetKeyStatus);

        square.setFillColor(color);
        square.setStrokeColor(color);

        currentKeyStatus = keyStatus.get(ltr);
        keyStatus.put(ltr, 
            currentKeyStatus < targetKeyStatus ? targetKeyStatus :
            currentKeyStatus
        );
    }
}
