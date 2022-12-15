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

    public String[] getGuess() {
        String[] ret = new String[5];
        for (int i = 0; i < 5; i++) {
            ret[i] = squareLabels[getCurrentRow()][i].getText();
        }
        return ret;
    }

    public void assemble() {
        currentRow = 0;
        currentColumn = 0;
        
        assembleArrayOfSquares();
        assembleArrayOfGraphicsTexts();

        setSelected();
    }

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
            setNextSquareToLtr(key);
        }
    }

    private void setNextSquareToLtr(Key key) {
        GraphicsText label = squareLabels[getCurrentRow()][currentColumn];
        label.setText(key.toString());
        refreshGraphicsTextPositions();
        shiftColumnRight();
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
        if (getCurrentRow() >= 6) {
            return;
        }
        squares[getCurrentRow()][currentColumn].setStrokeWidth(SQUARE_PADDING/2);
    }
    
    private void setUnselected() {
        if (getCurrentRow() >= 6) {
            return;
        }
        squares[getCurrentRow()][currentColumn].setStrokeWidth(1);
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
        parentUI.add(nextSquare);
        
        squares[row][column] = nextSquare;
    }

    private void newSquareGraphicsText(int x, int y, int row, int column) {
        GraphicsText nextLabel = new GraphicsText("", x, y);
        nextLabel.setFontSize(SQUARE_SIDE_LENGTH/1.5);
        parentUI.add(nextLabel);

        squareLabels[row][column] = nextLabel;
    }

    private void setSquareAndKeyColor(String ltr, Rectangle square, Integer targetKeyStatus) {
        Color color = List.of(WerdillUI.NOT_IN_COLOR, WerdillUI.WRONG_POSITION_COLOR, WerdillUI.RIGHT_POSITION_COLOR).get(targetKeyStatus);

        square.setFillColor(color);
        square.setStrokeColor(color);
    }

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

        currentRow += 1;
        setSelected();
    }

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
