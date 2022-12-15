package Werdill;

import java.io.IOException;

import edu.macalester.graphics.CanvasWindow;

/**
 * Main class for Werdill. Creates the CanvasWindow of appropriate size for the game. Contains
 * a main() method for convenient, standalone use/testing, but it may also be instantiated
 * and accessed by a Hub. 
 * 
 * @author Phil Reitz-Jones
 * @author Kaliana Andriamananjara
 */
public class WerdillGame {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;

    private final WerdillUI ui;
    private final Checker checker;

    private final CanvasWindow canvas;

    public WerdillGame() throws IOException {

        checker = new Checker();

        canvas = new CanvasWindow("Werdill", CANVAS_WIDTH, CANVAS_HEIGHT);
        ui = new WerdillUI(canvas, checker);
        
        canvas.add(ui);
    }
    
    public static void main(String[] args) throws IOException {
        new WerdillGame();
    }
}
