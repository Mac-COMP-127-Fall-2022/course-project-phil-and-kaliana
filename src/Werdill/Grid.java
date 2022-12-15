package Werdill;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.events.Key;

/**
 * Class representing Werdill's 5x6 grid. Used color-changing rectangles underneath GraphicsText
 * objects to display guess letter's "correctness" and the entered letter itself, respectively.
 * Has methods to update the grid objects' colors and contents, and respond to user input.
 * 
 * @author Phil Reitz-Jones
 * @author Kaliana Andriamananjara
 */
public class Grid {
    public static final int SQUARE_SIDE_LENGTH = 60;
    public static final int SQUARE_PADDING = 10;

    private int currentRow = 0;
    private int currentColumn = 0;

    private final Rectangle[][] squares = new Rectangle[6][5];
    private final GraphicsText[][] squareLabels = new GraphicsText[6][5];

    private final CanvasWindow canvas;
    private final WerdillUI parentUI;

    public Grid(CanvasWindow canvas, WerdillUI parentUI) {
        this.canvas = canvas;
        this.parentUI = parentUI;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * Loops over the 5 Rectangles and Labels in the current row, storing the letter the user has
     * put in each position. This constitutes the guess for that round.
     * 
     * @return  String Array of length 5, with each index containing one letter of the user's guess
     */
    public String[] getGuess() {
        String[] ret = new String[5];
        for (int i = 0; i < 5; i++) {
            ret[i] = squareLabels[getCurrentRow()][i].getText();
        }
        return ret;
    }

    /**
     * Finishes construction of the Grid's UI elements and resets current row and column to 0,
     * preparing for a new game. The first rectangle in row 0 is then selected.
     */
    public void assemble() {
        currentRow = 0;
        currentColumn = 0;
        
        assembleArrayOfSquares();
        assembleArrayOfGraphicsTexts();

        setSelected();
    }

    /**
     * onKeyDown callback. Handles arrow keys (shifting the displayed selection over), delete/backspace
     * (clears character if there is one, otherwise shifts over one), and if given a letter, it updates
     * the letter displayed on each square. Return/enter is handled by WerdillUI.
     * 
     * @param key   Key that was pressed by the user.
     */
    public void onKeyDown(Key key) {
        if (key == Key.DELETE_OR_BACKSPACE) {
            GraphicsText label = squareLabels[getCurrentRow()][currentColumn];
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
            // is valid letter; updates the square.
            setNextSquareToLtr(key);
        }
    }

    /**
     * Updates the currently selected to square with the letter just entered by the user.
     * 
     * @param key   Key that was pressed by user.
     */
    private void setNextSquareToLtr(Key key) {
        GraphicsText label = squareLabels[getCurrentRow()][currentColumn];
        label.setText(key.toString());
        refreshGraphicsTextPositions();
        shiftColumnRight();
    }

    /**
     * Changes selected square to the one on the immediate right of current selection.
     * Refreshes the UI to reflect the change.
     */
    private void shiftColumnRight() {
        if (currentColumn > 3) {
            return;
        }

        setUnselected();
        currentColumn++;
        setSelected();
    }

    /**
     * Changes selected square to the one on the immediate left of current selection.
     * Refreshes the UI to reflect the change.
     */
    private void shiftColumnLeft() {
        if (currentColumn < 1) {
            return;
        }

        setUnselected();
        currentColumn--;
        setSelected();
    }

    /**
     * Changes stroke width of square at (currentRow, currentColumn) to floor(PADDING/2). This
     * indicates the current selection to the user. 
     */
    private void setSelected() {
        if (getCurrentRow() >= 6) {
            return;
        }
        squares[getCurrentRow()][currentColumn].setStrokeWidth(SQUARE_PADDING/2);
    }
    
    /**
     * Changes stroke width of square at (currentRow, currentColumn) back to 1. This
     * indicates this square is not currently selected. 
     */
    private void setUnselected() {
        if (getCurrentRow() >= 6) {
            return;
        }
        squares[getCurrentRow()][currentColumn].setStrokeWidth(1);
    }

    /**
     * Creates a Grid of blank Rectangle objects with equal side lengths with dimensions 5u x 6u.
     */
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

    /**
     * Creates a Grid of blank GraphicsText objects centered on top of each Rectangle.
     */
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

    /**
     * Recenters each label directly above the colored square below it. Useful after a label's value
     * has been changed and may no longer appear centered to the user.
     */
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

    /**
     * Creates a new Werdill Grid square. The color of each of these will change after a guess
     * is submitted to reflect the Checker's results. Method should be used BEFORE 
     * newSquareGraphicsText() is called at the same location.
     * 
     * @param x         x coordinate in parent
     * @param y         y coordinate in parent
     * @param row       row in grid
     * @param column    column in grid
     */
    private void newSquare(int x, int y, int row, int column) {
        Rectangle nextSquare = new Rectangle(x, y, SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
        parentUI.add(nextSquare);
        
        squares[row][column] = nextSquare;
    }

    /**
     * Creates a new Werdill Grid label. This will update when the user presses a key to reflect
     * its corresponding letter (similar to a text field). Method should be used AFTER
     * newSquare() is called at the same location.
     * 
     * @param x         x coordinate in parent
     * @param y         y coordinate in parent
     * @param row       row in grid
     * @param column    column in grid
     */
    private void newSquareGraphicsText(int x, int y, int row, int column) {
        GraphicsText nextLabel = new GraphicsText("", x, y);
        nextLabel.setFontSize(SQUARE_SIDE_LENGTH/1.5);
        parentUI.add(nextLabel);

        squareLabels[row][column] = nextLabel;
    }

    /**
     * Updates the fill color of the given Rectangle to match the status provided.
     * Legend: -1 = blank; 0 = letter is not in solution; 1 = letter is in solution but is in
     * the wrong place; 2 = letter is in solution at correct position. 
     * 
     * @param ltr               Letter in label
     * @param square            Square underneath that label
     * @param targetKeyStatus   Either 0, 1, or 2; representing desired color
     */
    private void setSquareColor(String ltr, Rectangle square, Integer targetKeyStatus) {
        Color color = List.of(WerdillUI.NOT_IN_COLOR, WerdillUI.WRONG_POSITION_COLOR, WerdillUI.RIGHT_POSITION_COLOR).get(targetKeyStatus);

        square.setFillColor(color);
        square.setStrokeColor(color);
    }

    /**
     * Method iterates over input list from checked guess and applies it to the current row.
     * Squares are recolored to reflect quality of user's guess. All text is changed to
     * white for improved visibility on color. Selected suqare is also deseleted; the currentRow is
     * incremented, and the first square of that next row is selected.
     * Legend: -1 = blank; 0 = letter is not in solution; 1 = letter is in solution but is in
     * the wrong place; 2 = letter is in solution at correct position.
     * 
     * @param checked   Integer Array from Checker
     */
    public void setCurrentRowTo(Integer[] checked) {
        // 0 = not in; 1 = in wrong position; 2 = in correct position
        for (Integer integer : checked) {
            if (integer == null) {
                return;
            }
        }

        setUnselected();
        currentColumn = 0;

        Rectangle[] row = squares[currentRow];
        for (int i = 0; i < 5; i++) {
            GraphicsText label = squareLabels[currentRow][i];
            label.setFillColor(Color.WHITE);
            String ltr = label.getText();

            Rectangle square = row[i];
            switch (checked[i]) {
                case 0:
                    setSquareColor(ltr, square, 0);
                    break;
            
                case 1:
                    setSquareColor(ltr, square, 1);
                    break;
                    
                case 2:
                    setSquareColor(ltr, square, 2);
                    break;
            
                default:
                    break;
            }
            canvas.draw();
            
            canvas.pause(200);
        }

        currentRow += 1;
        setSelected();
    }

    /**
     * Resets all the Grid's constituent UI elements to blank status. This allows the UI to be
     * easily reset after the user finishes a game without needing to instantiate all new elements.
     */
    public void reset() {
        setUnselected();

        currentColumn = 0;
        currentRow = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Rectangle square = squares[i][j];
                GraphicsText label = squareLabels[i][j];

                label.setFillColor(Color.BLACK);
                label.setText("");

                square.setFillColor(Color.WHITE);
                square.setStrokeColor(Color.BLACK);
            }
        }

        
        setSelected();
    }
}
