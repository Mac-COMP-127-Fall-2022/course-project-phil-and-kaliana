package Werdill;

import java.io.IOException;
// import java.util.Random;

import edu.macalester.graphics.CanvasWindow;

public class WerdillGame {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;

    private final WerdillUI ui;
    private final Checker checker;

    // private final Random rand = new Random();

    private final CanvasWindow canvas;

    WerdillGame() throws IOException {

        checker = new Checker();

        canvas = new CanvasWindow("Werdill", CANVAS_WIDTH, CANVAS_HEIGHT);
        ui = new WerdillUI(canvas, checker);
        
        canvas.add(ui);
    }
    
    public static void main(String[] args) throws IOException {
        new WerdillGame();
    }
}
