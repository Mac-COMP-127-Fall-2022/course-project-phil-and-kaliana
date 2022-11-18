package Werdill;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WerdillGame {
    private static final String WORD_LIST_PATH = "res/wordList/filteredWords.txt";

    private final List<String> words;
    private final WerdillUI ui;
    private final Checker checker;
    
    WerdillGame() throws IOException {
        words = Files.lines(Path.of(WORD_LIST_PATH)).toList();

        ui = new WerdillUI(this);
        checker = new Checker(chooseSolution());
        
        CanvasWindow canvas = new CanvasWindow();
        canvas.add(ui);

    }

    private String chooseSolution() {
        return null;
    }

    public List<String> getWords() {
        return words;
    }
    
    public static void main(String[] args) {
        
    }
}
