import java.io.IOException;

import SetGame.SetGameMain;
import Werdill.WerdillGame;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;


public class Hub {
    private final CanvasWindow canvas;

    // private WerdillGame werdill;
    // private SetGameMain set;

    private Button werdillButton;
    private Button setButton;

    public Hub() throws IOException {
        canvas = new CanvasWindow("7509Mythic Arcade", 400, 600);

        Image background = new Image("background.png"); 
        // The library wouldn't load the background from the res directory (see Image line 41; it unnecessarily adds a "/" to the path--please fix that!)
        background.setMaxHeight(600);
        canvas.add(background);

        werdillButton = new Button("Werdill");
        setButton = new Button("Set");

        werdillButton.onClick(() -> {
            try {
                new WerdillGame();
            } catch (IOException e) {}
        });
        setButton.onClick(() -> {
            new SetGameMain();
        });

        setButton.setPosition(0, 40);

        canvas.add(setButton);
        canvas.add(werdillButton);
    }
    
    // private void startWerdill() throws IOException {
    //     werdill = new WerdillGame();
    // }
    
    // private void startSet() throws IOException {
    //     set = new SetGameMain();
    // }

    public static void main(String[] args) throws Exception {
        new Hub();
    }
}
