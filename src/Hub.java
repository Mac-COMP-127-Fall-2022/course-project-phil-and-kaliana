import java.io.IOException;

import SetGame.SetGameMain;
import Werdill.WerdillGame;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;


public class Hub {
    private final CanvasWindow canvas;

    private Button werdillButton;
    private Button setButton;

    public Hub() throws IOException {
        canvas = new CanvasWindow("7509Mythic Arcade", 400, 400);

        Image background = new Image("background.png"); 
        // The library wouldn't load the background from the res directory (see Image line 41; it unnecessarily adds a "/" to the path--please fix that!)
        background.setMaxHeight(400);
        canvas.add(background);

        werdillButton = new Button("Werdill");
        setButton = new Button("Set");

        werdillButton.setPosition(
            canvas.getWidth()/2 - werdillButton.getWidth()/2, 
            canvas.getHeight()/2 - 20
        );
        setButton.setPosition(
            canvas.getWidth()/2 - setButton.getWidth()/2, 
            canvas.getHeight()/2 + 20
        );

        werdillButton.onClick(() -> {
            try {
                new WerdillGame();
            } catch (IOException e) {}
        });
        setButton.onClick(() -> {
            new SetGameMain();
        });

        canvas.add(setButton);
        canvas.add(werdillButton);
    }
    
    public static void main(String[] args) throws Exception {
        new Hub();
    }
}
