package Werdill;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;

public class WerdillGame {
    private static final String WORD_LIST_PATH = "res/wordList/filteredWords.txt";

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;

    private final List<String> words;
    private final WerdillUI ui;
    private final Checker checker;

    private final CanvasWindow canvas;

    WerdillGame() throws IOException {
        words = Files.lines(Path.of(WORD_LIST_PATH)).toList();

        checker = new Checker(chooseSolution());

        canvas = new CanvasWindow("Werdill", CANVAS_HEIGHT, CANVAS_WIDTH);
        ui = new WerdillUI(canvas, checker);
        
        canvas.add(ui);
    }

    private String chooseSolution() {
        return "world";
    }

    public List<String> getWords() {
        return words;
    }
    
    public static void main(String[] args) throws IOException {
        new WerdillGame();
    }
}
