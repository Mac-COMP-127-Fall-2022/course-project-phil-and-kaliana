package Werdill;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import edu.macalester.graphics.events.Key;

public class WerdillUI extends GraphicsGroup {
    /* these constants may or may not be used in final version: */
        // private static final Color BACKGROUND_COLOR = new Color(0xffffff);

        // private static final Color BASE_COLOR = Color.WHITE;
        // private static final Color SELECTED_COLOR = Color.LIGHT_GRAY;

        // private static final Color WRONG_RIGHT_TEXT_COLOR = new Color(0xffffff);
        // private static final Color NOT_IN_AND_BASE_TEXT_COLOR = new Color(0xffffff);

    private static final Color NOT_IN_COLOR = Color.GRAY;//new Color(0xafafaf);
    private static final Color WRONG_POSITION_COLOR = new Color(0xffc900);
    private static final Color RIGHT_POSITION_COLOR = new Color(0x049c00);

    private static final int SQUARE_SIDE_LENGTH = 60;
    private static final int PADDING = 10;

    private int currentRow;
    private int currentColumn;
    private final Rectangle[][] squares = new Rectangle[6][5];
    private final GraphicsText[][] squareLabels = new GraphicsText[6][5];

    private final CanvasWindow canvas;
    private final Checker checker;

    public WerdillUI(CanvasWindow canvas, Checker checker) {
        this.canvas = canvas;
        this.checker = checker;

        canvas.onKeyDown((event) -> { //TODO: needs refactoring into smaller methods
            Key key = event.getKey();
            if (key == Key.RETURN_OR_ENTER && currentColumn >= 4) {
                sumbitGuess();
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
        squares[currentRow][currentColumn].setStrokeWidth(PADDING/2);
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
        int x = PADDING;
        int y = PADDING;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                newSquare(x, y, i, j);
                x += PADDING + SQUARE_SIDE_LENGTH;
            }

            x = PADDING;
            y += PADDING + SQUARE_SIDE_LENGTH;
        }
    }

    private void assembleArrayOfGraphicsTexts() {
        int x = PADDING;
        int y = PADDING;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                newSquareGraphicsText(x, y, i, j);
                x += PADDING + SQUARE_SIDE_LENGTH;
            }

            x = PADDING;
            y += PADDING + SQUARE_SIDE_LENGTH;
        }
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

    private void newSquare(int x, int y, int row, int column) {
        Rectangle nextSquare = new Rectangle(x, y, SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
        add(nextSquare);
        

        squares[row][column] = nextSquare;
    }

    private void newSquareGraphicsText(int x, int y, int row, int column) {
        GraphicsText nextLabel = new GraphicsText("", x, y);
        nextLabel.setFontSize(43);
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

            Rectangle square = row[i];
            switch (checked[i]) {
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
            canvas.draw();

            canvas.pause(200);
        }
    }
}
