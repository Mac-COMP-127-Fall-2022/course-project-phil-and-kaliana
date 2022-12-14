package Werdill;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.ui.Button;

public class Keyboard {
    public static final double KEY_PADDING = WerdillUI.SQUARE_PADDING / 2;
    public static final double KEY_SIDE_LENGTH = ((WerdillUI.SQUARE_SIDE_LENGTH + WerdillUI.SQUARE_PADDING) * 5.0 - KEY_PADDING * 11) / 10;

    private final HashMap<String, Rectangle> keys = new HashMap<>();
    private final HashMap<String, GraphicsText> keyLabels = new HashMap<>();
    private final HashMap<String, Integer> keyStatus = new HashMap<>();
    
    private final CanvasWindow canvas;
    private final WerdillUI parentUI;
    
    public Keyboard(CanvasWindow canvas, WerdillUI parentUI) {
        this.canvas = canvas;
        this.parentUI = parentUI;
    }

    public Integer getKeyStatus(String ltr) {
        return keyStatus.get(ltr);
    }

    private void setKeyColor(Rectangle square, GraphicsText label, Color color) {
        square.setFillColor(color);
        square.setStrokeColor(color);

        label.setFillColor(Color.WHITE);
    }

    public void refreshKeyboard() {
        for (String key : keyLabels.keySet()) {
            Rectangle square = keys.get(key);
            GraphicsText label = keyLabels.get(key);

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

    private void createKey(double x, double y, String ltr) {
        keyStatus.put(ltr, -1);

        Rectangle newKey = new Rectangle(x, y, KEY_SIDE_LENGTH, KEY_SIDE_LENGTH);
        keys.put(ltr, newKey);

        GraphicsText newKeyLabel = new GraphicsText(ltr, x, y);
        keyLabels.put(ltr, newKeyLabel);

        newKeyLabel.setFontSize(KEY_SIDE_LENGTH/1.5);

        double dx = newKey.getCenter().getX() - newKeyLabel.getCenter().getX();
        double dy = newKey.getCenter().getY() - newKeyLabel.getCenter().getY();
        newKeyLabel.moveBy(dx, dy);

        parentUI.add(newKey);
        parentUI.add(newKeyLabel);
    }

    public void assembleKeyboard() {
        double x = 0 + WerdillUI.SQUARE_PADDING;
        double y = (WerdillUI.SQUARE_PADDING + WerdillUI.SQUARE_SIDE_LENGTH) * 6 + KEY_PADDING + KEY_SIDE_LENGTH;
        createKeyRow(x, y, List.of("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"));
        
        x = 0.25 * KEY_SIDE_LENGTH + WerdillUI.SQUARE_PADDING;
        y += KEY_SIDE_LENGTH + KEY_PADDING;
        createKeyRow(x, y, List.of("A", "S", "D", "F", "G", "H", "J", "K", "L"));

        x = 0.75 * KEY_SIDE_LENGTH + WerdillUI.SQUARE_PADDING;
        y += KEY_SIDE_LENGTH + KEY_PADDING;
        createKeyRow(x, y, List.of("Z", "X", "C", "V", "B", "N", "M"));
        
        Button resetButton = new Button("Reset");
        resetButton.setPosition(keys.get("M").getX() + KEY_PADDING + KEY_SIDE_LENGTH, y);

        resetButton.onClick(() -> {
            parentUI.reset();
            parentUI.getChecker().chooseSolution();
        });

        parentUI.add(resetButton);
        canvas.draw();
    }

    private void createKeyRow(double x, double y, List<String> keys) {
        for (String ltr : keys) {
            createKey(x, y, ltr);
            x += KEY_SIDE_LENGTH + KEY_PADDING;
        }
    }

    public void setKeyStatus(String ltr, Integer status) {
        keyStatus.put(ltr, status);
    }

    public void reset() {
        for (String key : keys.keySet()) {
            keys.get(key).setFillColor(Color.WHITE);
            keys.get(key).setStrokeColor(Color.BLACK);

            keyLabels.get(key).setFillColor(Color.BLACK);

            keyStatus.put(key, -1);
        }
    }
}
