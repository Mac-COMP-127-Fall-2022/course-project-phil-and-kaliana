import java.io.IOException;

import SetGame.SetGameMain;
import Werdill.WerdillGame;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.ui.Button;

public class Hub {
    private final CanvasWindow canvas;

    // private WerdillGame werdill;
    // private SetGameMain set;

    private Button werdillButton;
    private Button setButton;

    public Hub() throws IOException {
        canvas = new CanvasWindow(null, 100, 250);

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
